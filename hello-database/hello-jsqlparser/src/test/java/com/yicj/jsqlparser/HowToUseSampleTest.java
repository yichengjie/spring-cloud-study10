package com.yicj.jsqlparser;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.deparser.StatementDeParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/12 15:01
 */
@Slf4j
public class HowToUseSampleTest {
    //@formatter:off
    /*
     SQL Text
      └─Statements: net.sf.jsqlparser.statement.select.Select
          ├─selectItems -> Collection<SelectItem>
          │  └─LongValue: 1
          ├─Table: dual
          └─where: net.sf.jsqlparser.expression.operators.relational.EqualsTo
             ├─Column: a
             └─Column: b
     */
    //@formatter:on

    @Test
    void writeSQL() throws JSQLParserException {
        String sqlStr = "select 1 from dual where a=b";
        Select select = (Select) CCJSqlParserUtil.parse(sqlStr);
        PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
        // selectItem
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        SelectExpressionItem selectItem = (SelectExpressionItem)selectItems.get(0);
        Assertions.assertEquals(new LongValue(1), selectItem.getExpression());
        // tableName
        Table table = (Table) plainSelect.getFromItem();
        Assertions.assertEquals("dual", table.getName());
        // where
        EqualsTo equalsTo = (EqualsTo) plainSelect.getWhere();
        Column a = (Column) equalsTo.getLeftExpression();
        Column b = (Column) equalsTo.getRightExpression();
        Assertions.assertEquals("a", a.getColumnName());
        Assertions.assertEquals("b", b.getColumnName());
    }



    @Test
    void visitor() throws JSQLParserException {
        ExpressionVisitorAdapter expressionVisitorAdapter = new ExpressionVisitorAdapter(){
            @Override
            public void visit(EqualsTo equalsTo) {
                equalsTo.getLeftExpression().accept(this);
                equalsTo.getRightExpression().accept(this);
            }

            @Override
            public void visit(Column column) {
                log.info("Found a Column ： {}", column.getColumnName());
            }
        } ;

        SelectVisitorAdapter  selectVisitor = new SelectVisitorAdapter(){
            @Override
            public void visit(PlainSelect plainSelect) {
                plainSelect.getWhere().accept(expressionVisitorAdapter);
            }
        } ;

        StatementVisitorAdapter statementVisitor = new StatementVisitorAdapter(){
            @Override
            public void visit(Select select) {
                select.getSelectBody().accept(selectVisitor);
            }
        } ;

        //
        String sqlStr = "select 1 from dual where a=b";
        Statement stmt = CCJSqlParserUtil.parse(sqlStr);
        stmt.accept(statementVisitor);
    }

    @Test
    void buildSqlStatement(){

        String expectedSQLStr = "SELECT 1 FROM dual t WHERE a = b";
        //step 1: generate the java object hierarchy for
        Table table = new Table().withName("dual").withAlias(new Alias("t", false)) ;

        Column columnA = new Column().withColumnName("a") ;
        Column columnB = new Column().withColumnName("b") ;
        Expression whereExpression =
                new EqualsTo().withLeftExpression(columnA).withRightExpression(columnB) ;

        PlainSelect select = new PlainSelect().addSelectItems(new SelectExpressionItem(new LongValue(1)))
                .withFromItem(table).withWhere(whereExpression);

        //step 2a: Print init a Sql Statement
        Assertions.assertEquals(expectedSQLStr, select.toString());
        //step 2b: De-Parse into a Sql statement
        StringBuilder builder = new StringBuilder() ;
        StatementDeParser deParser = new StatementDeParser(builder) ;
//        deParser.visit(select);
//        Assertions.assertEquals(expectedSQLStr, builder.toString());

    }



    @Test
    void writeSQL2() throws JSQLParserException {
        String sqlStr = "select 1 from t_user, t_student b where a.id = b.user_id and a.name = '张三' and (c = 1 or d = 2) and e in (1,2,3)";
        Select select = (Select) CCJSqlParserUtil.parse(sqlStr);
        PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
        // selectItem
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        SelectExpressionItem selectItem = (SelectExpressionItem)selectItems.get(0);
        Assertions.assertEquals(new LongValue(1), selectItem.getExpression());
        // tableName
        Table table = (Table) plainSelect.getFromItem();
        //Assertions.assertEquals("dual", table.getName());
        // where
        EqualsTo equalsTo = (EqualsTo) plainSelect.getWhere();
        Column a = (Column) equalsTo.getLeftExpression();
        Column b = (Column) equalsTo.getRightExpression();
        Assertions.assertEquals("a", a.getColumnName());
        Assertions.assertEquals("b", b.getColumnName());
    }
}
