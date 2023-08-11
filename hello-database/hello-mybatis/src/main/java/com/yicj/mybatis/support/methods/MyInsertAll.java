package com.yicj.mybatis.support.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: yicj
 * @date: 2023/8/11 21:24
 */
public class MyInsertAll extends AbstractMethod {

    public MyInsertAll() {
        super("insertAll");
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "insert into %s %s values %s" ;
        StringBuilder fieldSql = new StringBuilder() ;
        fieldSql.append(tableInfo.getKeyColumn()).append(",") ;
        //
        StringBuilder valueSql = new StringBuilder() ;
        valueSql.append("#{").append(tableInfo.getKeyProperty()).append("},") ;
        tableInfo.getFieldList().forEach(field -> {
            fieldSql.append(field.getColumn()).append(",") ;
            valueSql.append("#{").append(field.getProperty()).append("},") ;
        });
        fieldSql.delete(fieldSql.length()-1, fieldSql.length()) ;
        fieldSql.insert(0, "(") ;
        fieldSql.append(")") ;
        valueSql.insert(0, "(") ;
        valueSql.delete(valueSql.length()-1, valueSql.length()) ;
        valueSql.append(")") ;
        String formatSql = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, formatSql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, methodName, sqlSource, new NoKeyGenerator(), null, null);
    }
}
