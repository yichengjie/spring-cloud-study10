package com.yicj.mybatis.support.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: yicj
 * @date: 2023/8/11 21:36
 */
public class MysqlInsertAllBatch extends AbstractMethod {


    public MysqlInsertAllBatch() {
        super("insertAllBatch");
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>insert into %s %s values %s</script>" ;
        final String fieldSql = prepareFieldSql(tableInfo) ;
        final String valueSql = prepareValuedSql(tableInfo) ;
        final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql) ;
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, methodName, sqlSource, new NoKeyGenerator(), null, null);
    }

    private String prepareFieldSql(TableInfo tableInfo) {
        StringBuilder fieldSql = new StringBuilder() ;
        fieldSql.append(tableInfo.getKeyColumn()).append(",") ;
        tableInfo.getFieldList().forEach(x -> {
            fieldSql.append(x.getColumn()).append(",") ;
        });
        fieldSql.delete(fieldSql.length() -1, fieldSql.length()) ;
        fieldSql.insert(0, "(") ;
        fieldSql.append(")") ;
        return fieldSql.toString() ;
    }

    private String prepareValuedSql(TableInfo tableInfo) {
        final StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection = \"list\" index = \"index\" open = \"(\"  separator = \"),(\"  close = \")\">") ;
        valueSql.append("#{item.").append(tableInfo.getKeyProperty()).append("},") ;
        tableInfo.getFieldList().forEach(x -> valueSql.append("#{item.").append(x.getProperty()).append("},"));
        valueSql.delete(valueSql.length()-1, valueSql.length()) ;
        valueSql.append("</foreach>") ;
        return null ;
    }

}
