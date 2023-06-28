package com.shopastro.sps.open.framework.validate;

import com.shopastro.sps.open.share.rpc.RpcResult;
import com.shopastro.sps.open.share.rpc.SysErrorMsgEnum;
import com.shopastro.sps.open.share.rpc.ValidateResultEntry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.HibernateValidator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ye.ly@shopastro-inc.com
 * @see org.springframework.validation.beanvalidation.MethodValidationInterceptor
 */
@Slf4j
@Component
@Aspect
public class RpcValidateAspect {
    public static final String ANN_CLZ = "com.shopastro.sps.open.framework.validate.EnableRpcValidate";
    private Validator validator;

    @PostConstruct
    public void init() {
        this.validator = Validation.byProvider(HibernateValidator.class).configure()
                .failFast(false)
                .buildValidatorFactory().getValidator();
    }



    @Around(value = "@within(" + ANN_CLZ + ") || @annotation(" + ANN_CLZ + ")")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object target = joinPoint.getTarget();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        Class<?>[] groups = determineValidationGroups(method, target);
        ExecutableValidator execVal = this.validator.forExecutables();
        Set<ConstraintViolation<Object>> validateResult = execVal.validateParameters(target, method, args, groups);
        if (!validateResult.isEmpty()) {
            if (RpcResult.class.isAssignableFrom(method.getReturnType())) {
                RpcResult rpcResult = RpcResult.error(SysErrorMsgEnum.invalid_argument_error);
                List<ValidateResultEntry> reason = validateResult.stream().map(
                        it -> ValidateResultEntry.builder()
                                .path(it.getPropertyPath().toString())
                                .message(it.getMessage())
                                .invalidValue(it.getInvalidValue())
                                .build()
                ).collect(Collectors.toList());
                rpcResult.setValidateResult(reason);
                log.info("rpc validate not pass,{},{}, {}", target, method, rpcResult);
                return rpcResult;
            } else {
                throw new ConstraintViolationException(validateResult);
            }
        }
        return joinPoint.proceed();
    }


    protected Class<?>[] determineValidationGroups(Method method, Object target) {
        EnableRpcValidate validatedAnn = AnnotationUtils.findAnnotation(method, EnableRpcValidate.class);
        if (validatedAnn == null) {
            Assert.state(target != null, "Target must not be null");
            validatedAnn = AnnotationUtils.findAnnotation(target.getClass(), EnableRpcValidate.class);
        }
        return (validatedAnn != null ? validatedAnn.value() : new Class<?>[0]);
    }
}
