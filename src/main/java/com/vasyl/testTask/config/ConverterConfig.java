package com.vasyl.testTask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    @Primary
    public ConversionServiceFactoryBean getConversionService(Set<Converter> converters) {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();

        bean.setConverters(converters);
        return bean;
    }

}
