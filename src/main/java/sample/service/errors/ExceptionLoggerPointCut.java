package sample.service.errors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sample.service.ws.WSSender;

@Configuration
@Aspect
public class ExceptionLoggerPointCut {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final WSSender wsSender;

    @Autowired
    public ExceptionLoggerPointCut(WSSender wsSender) {
        this.wsSender = wsSender;
    }

    @AfterThrowing(pointcut = "execution(* sample.service.ws.WSHandler.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        logger.error(ex);
        wsSender.sendToCurrent("system error, call the admins");
    }
}