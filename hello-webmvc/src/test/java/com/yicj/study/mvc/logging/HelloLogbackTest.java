package com.yicj.study.mvc.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.Marker;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author yicj
 * @date 2023/10/21 11:16
 */
@Slf4j
@SpringBootTest
public class HelloLogbackTest {

    @Autowired
    private Environment environment ;

    private static final ConfigurationPropertyName LOGGING_LEVEL = ConfigurationPropertyName.of("logging.level");

    private static final ConfigurationPropertyName LOGGING_GROUP = ConfigurationPropertyName.of("logging.group");
    private static final Bindable<Map<String, LogLevel>> STRING_LOGLEVEL_MAP = Bindable.mapOf(String.class, LogLevel.class);

    private static final Bindable<Map<String, List<String>>> STRING_STRINGS_MAP = Bindable
            .of(ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, String.class).asMap());


    @Test
    public void getLoggerContext(){
        ILoggerFactory factory = StaticLoggerBinder.getSingleton().getLoggerFactory();
        LoggerContext loggerContext = (LoggerContext) factory;
        log.info("log context : {}", loggerContext);

        SLF4JBridgeHandler.install();
        loggerContext.getTurboFilterList().add(FILTER);
        Binder binder = Binder.get(environment);
//        binder.bind(LOGGING_GROUP, STRING_STRINGS_MAP).ifBound(this.loggerGroups::putAll);
//        Binder binder = Binder.get(environment);
//        Map<String, LogLevel> levels = binder.bind(LOGGING_LEVEL, STRING_LOGLEVEL_MAP).orElseGet(Collections::emptyMap);
    }


    private static final TurboFilter FILTER = new TurboFilter() {

        @Override
        public FilterReply decide(Marker marker, ch.qos.logback.classic.Logger logger, Level level, String format,
                                  Object[] params, Throwable t) {
            return FilterReply.DENY;
        }

    };
}
