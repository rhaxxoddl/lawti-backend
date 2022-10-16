package com.pivot.hp.hometownpolitician.annotation;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.pivot.hp.hometownpolitician.strategy.P6SpyFormatter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@Import(P6SpyFormatter.class)
@SpringBootTest(properties = {"logging.level.org.springframework.test.context=ERROR"})
public @interface EnableQueryLog {

    @PropertyMapping("spring.jpa.show-sql") boolean showSql() default false;

    @PropertyMapping("decorator.datasource.p6spy.enable-logging") boolean enableLogging() default true;

}