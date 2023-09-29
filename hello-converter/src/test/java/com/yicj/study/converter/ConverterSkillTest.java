package com.yicj.study.converter;

import com.yicj.study.converter.config.ConverterSkillConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author: yicj
 * @date: 2023/9/29 15:42
 */
@Slf4j
@SpringJUnitConfig(ConverterSkillConfig.class)
public class ConverterSkillTest {

    @Autowired
    private ConversionService conversionService ;

    @Test
    public void convert(){
        Long value = conversionService.convert("123", Long.class);
        log.info("value : {}", value);
    }
}
