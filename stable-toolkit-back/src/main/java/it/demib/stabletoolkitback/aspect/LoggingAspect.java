package it.demib.stabletoolkitback.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Before("execution(* it.demib.stabletoolkitback..*.*(..))")
  public void logBefore(JoinPoint point) {
    Signature signature = point.getSignature();

    log.info("{}::{} - Starting", signature.getDeclaringType().getSimpleName(), signature.getName());
  }

  @After("execution(* it.demib.stabletoolkitback..*.*(..))")
  public void logAfter(JoinPoint point) {
    Signature signature = point.getSignature();

    log.info("{}::{} - Ended", signature.getDeclaringType().getSimpleName(), signature.getName());
  }
}
