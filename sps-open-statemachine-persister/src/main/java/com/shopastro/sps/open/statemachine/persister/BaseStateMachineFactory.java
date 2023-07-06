package com.shopastro.sps.open.statemachine.persister;

import com.google.common.collect.ImmutableMap;
import com.shopastro.sps.open.share.utils.ExceptionUtils;
import com.shopastro.sps.open.statemachine.Action;
import com.shopastro.sps.open.statemachine.Condition;
import com.shopastro.sps.open.statemachine.StateMachine;
import com.shopastro.sps.open.statemachine.builder.AlertFailCallback;
import com.shopastro.sps.open.statemachine.builder.StateMachineBuilder;
import com.shopastro.sps.open.statemachine.builder.StateMachineBuilderFactory;
import com.shopastro.sps.open.statemachine.persister.context.Context;
import com.shopastro.sps.open.statemachine.persister.dal.entiry.StateMachineTraceDO;
import com.shopastro.sps.open.statemachine.persister.dal.mapper.StateMachineTraceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
public abstract class BaseStateMachineFactory<S, E, C extends Context> implements ApplicationContextAware {
    ApplicationContext applicationContext;
    Map<String, Condition> conditionMap;
    Map<String, Action> actionMap;
    StateMachineTraceMapper stateMachineTraceMapper;

    protected abstract String getMachineId();

    /**
     * @return [[sourceStateEnum, eventEnum, targetStateEnum, conditionBeanName, actionBeanName], ...]
     */
    protected abstract Object[][] getStateMetric();

    protected abstract S[] getInitStates();

    protected abstract S[] getTerminateStates();

    protected String[] getExceptionFilters() {
        return new String[]{"com.shopastro."};
    }


    protected StateMachine<S, E, C> build() {
        StateMachineBuilder<S, E, C> builder = StateMachineBuilderFactory.create();
        for (Object[] row : getStateMetric()) {
            S from = (S) row[0];
            E on = (E) row[1];
            S to = (S) row[2];
            Condition<C> condition = conditionMap.get((String) row[3]);
            Action<S, E, C> action = actionMap.get((String) row[4]);

            Objects.requireNonNull(condition, "condition " + row[3] + " not found");
            Objects.requireNonNull(action, "action " + row[4] + " not found");
            boolean auto = row.length > 5 && (boolean) row[5];

            if (auto) {
                builder.addAutoTransition(
                        from, to, on, condition, action
                );
            } else {
                builder.addTransition(
                        from, to, on, condition, action
                );
            }
        }

        builder.initStates(Arrays.stream(getInitStates()).collect(Collectors.toSet()));
        builder.terminalStates(Arrays.stream(getTerminateStates()).collect(Collectors.toSet()));
        builder.setFailCallback(new AlertFailCallback<>());
        builder.setAfterFireEventCallback((sourceStateId, targetStateId, event, beforeCtx, ctx, throwable) -> {
            try {
                List<String> ex = ExceptionUtils.getRootCauseStackTrace(throwable, getExceptionFilters());
                stateMachineTraceMapper.insert(
                        StateMachineTraceDO.builder()
                                .statemachineId(getMachineId())
                                .tenantId(ctx.getTenantId())
                                .bizType(ctx.getBizType())
                                .bizId(ctx.getBizId())
                                .beforeState(sourceStateId)
                                .afterState(targetStateId)
                                .error(throwable != null)
                                .exception(ex.isEmpty() ? null : ex)
                                .context(
                                        ImmutableMap.of("before", beforeCtx, "after", ctx)
                                )
                                .build()
                );
            } catch (Throwable e) {
                log.error("save statemachine trace error", e);
            }
        });

        return builder.build(getMachineId());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.conditionMap = applicationContext.getBeansOfType(Condition.class);
        this.actionMap = applicationContext.getBeansOfType(Action.class);
        this.stateMachineTraceMapper = (StateMachineTraceMapper) applicationContext.getBean("stateMachineTraceMapper");
    }
}
