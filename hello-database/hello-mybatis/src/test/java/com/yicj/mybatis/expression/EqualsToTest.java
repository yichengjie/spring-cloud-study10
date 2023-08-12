package com.yicj.mybatis.expression;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import org.junit.jupiter.api.Test;

/**
 * @author: yicj
 * @date: 2023/8/12 11:40
 */
@Slf4j
public class EqualsToTest {

    @Test
    public void test1(){
        TenantLineHandler tenantLineHandler = new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                return !"t_student".equalsIgnoreCase(tableName);
            }
        };
        String tenantIdColumn = tenantLineHandler.getTenantIdColumn() ;
        Expression tenantIdExpression = tenantLineHandler.getTenantId();
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new StringValue(tenantIdColumn));
        equalsTo.setRightExpression(tenantIdExpression);
        log.info("equalsTo : {}", equalsTo);
        log.info("equalsTo : {}", equalsTo.getASTNode());
    }
}
