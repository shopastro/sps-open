package com.shopastro.sps.open.id.generator.aop;

import com.shopastro.sps.open.id.generator.impl.CachedIDGenerator;
import com.shopastro.sps.open.share.InjectIdSupport;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Slf4j
@Aspect
@Component
public class InjectIdAspect {
    final CachedIDGenerator cachedIDGenerator;

    public InjectIdAspect(CachedIDGenerator cachedIDGenerator) {
        this.cachedIDGenerator = cachedIDGenerator;
    }

    @Around("@annotation(com.shopastro.sps.open.id.generator.aop.InjectId)")
    public Object inject(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        InjectId injectId = method.getAnnotation(InjectId.class);
        boolean replace = "true".equals(injectId.replace());

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length < 1) {
            log.debug("ignore inject id,because args is empty.");
        } else {
            for (Object arg : args) {
                if (arg instanceof InjectIdSupport) {
                    InjectIdSupport sis = ((InjectIdSupport) arg);

                    Long id = sis.getId();
                    if (id == null || replace) {
                        id = cachedIDGenerator.getUID();
                        sis.setId(id);
                        log.debug("set id for object {},{}: ", id, arg);
                    } else {
                        log.debug("ignore inject id,because id is not empty.{}，{}", id, arg);
                    }
                }
            }
        }

        return joinPoint.proceed();
    }
}
