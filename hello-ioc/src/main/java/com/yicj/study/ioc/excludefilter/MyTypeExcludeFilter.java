package com.yicj.study.ioc.excludefilter;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 * @author yicj
 * @date 2023/11/5 11:44
 */
public class MyTypeExcludeFilter extends TypeExcludeFilter {
    //metadataReader：读取到的当前正在扫描的类的信息
    //metadataReaderFactory：可以获取到其他任何类的信息的（工厂）
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 过滤掉 MyFilterController 的加载
        return metadataReader.getAnnotationMetadata().getClassName().equals(MyFilterController.class.getName()) ;
    }
}
