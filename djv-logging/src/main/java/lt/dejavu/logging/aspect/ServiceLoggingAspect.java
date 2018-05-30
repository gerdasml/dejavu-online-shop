package lt.dejavu.logging.aspect;

import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Order(value=2)
public class ServiceLoggingAspect {

    @Value("${logging.level.common:OFF}")
    private LogLevel LOG_LEVEL;

    @Around("execution(* lt.dejavu..*.service..*.*(..))")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        final Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
        Object retVal = null;

        try {
            StringBuilder startMessageStringBuffer = new StringBuilder();

            startMessageStringBuffer.append("Start method ");
            startMessageStringBuffer.append(joinPoint.getSignature().getName());
            startMessageStringBuffer.append("(");

            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                startMessageStringBuffer.append(arg).append(",");
            }
            if (args.length > 0) {
                startMessageStringBuffer.deleteCharAt(startMessageStringBuffer.length() - 1);
            }

            startMessageStringBuffer.append(")");

            if (LOG_LEVEL.compareTo(LogLevel.DEBUG) <= 0) {
                logger.debug(startMessageStringBuffer.toString());
            }

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            retVal = joinPoint.proceed();

            stopWatch.stop();

            String endMessageStringBuffer = "Finish method " +
                                            joinPoint.getSignature().getName() +
                                            "(..); execution time: " +
                                            stopWatch.getTotalTimeMillis() +
                                            " ms; return value: " +
                                            retVal;
            if (LOG_LEVEL.compareTo(LogLevel.DEBUG) <= 0) {
                logger.debug(endMessageStringBuffer);
            }
        } catch (Throwable ex) {
            if (LOG_LEVEL.compareTo(LogLevel.ERROR) <= 0) {
                logger.error("", ex);
            }
            throw ex;
        }

        return retVal;
    }
}
