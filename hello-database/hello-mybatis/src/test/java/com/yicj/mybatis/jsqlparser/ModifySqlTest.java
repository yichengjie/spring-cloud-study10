package com.yicj.mybatis.jsqlparser;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterSession;
import net.sf.jsqlparser.statement.alter.AlterSystemStatement;
import net.sf.jsqlparser.statement.alter.RenameTableStatement;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.analyze.Analyze;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.grant.Grant;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.show.ShowIndexStatement;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/12 11:52
 */
@Slf4j
public class ModifySqlTest {

    @Test
    public void hello() throws JSQLParserException {
        // 1. 获取原始sql输入
        String sql = "select max(age) from table1" ;
        log.info("org sql : {}", sql);
        // 2. 创建解析器
        CCJSqlParserManager mgr = new CCJSqlParserManager() ;
        // 3. 使用解析器解析sql生成具有层次结构对的java类
        Statement stmt = mgr.parse(new StringReader(sql));
        // 4. 将自定义访问者传入解析后的sql对象
        stmt.accept(new MyJSqlVisitor()) ;
        // 5. 打印转换后的sql语句
        log.info("new sql : [{}]", stmt.toString());
    }

    class MyJSqlVisitor implements StatementVisitor, SelectVisitor ,SelectItemVisitor, ExpressionVisitor,FromItemVisitor {

        @Override
        public void visit(Analyze analyze) {

        }

        @Override
        public void visit(SavepointStatement savepointStatement) {

        }

        @Override
        public void visit(RollbackStatement rollbackStatement) {

        }

        @Override
        public void visit(Comment comment) {

        }

        @Override
        public void visit(Commit commit) {

        }

        @Override
        public void visit(Delete delete) {

        }

        @Override
        public void visit(Update update) {

        }

        @Override
        public void visit(Insert insert) {

        }

        @Override
        public void visit(Replace replace) {

        }

        @Override
        public void visit(Drop drop) {

        }

        @Override
        public void visit(Truncate truncate) {

        }

        @Override
        public void visit(CreateIndex createIndex) {

        }

        @Override
        public void visit(CreateSchema aThis) {

        }

        @Override
        public void visit(CreateTable createTable) {

        }

        @Override
        public void visit(CreateView createView) {

        }

        @Override
        public void visit(AlterView alterView) {

        }

        @Override
        public void visit(Alter alter) {

        }

        @Override
        public void visit(Statements stmts) {

        }

        @Override
        public void visit(Execute execute) {

        }

        @Override
        public void visit(SetStatement set) {

        }

        @Override
        public void visit(ResetStatement reset) {

        }

        @Override
        public void visit(ShowColumnsStatement set) {

        }

        @Override
        public void visit(ShowIndexStatement showIndex) {

        }

        @Override
        public void visit(ShowTablesStatement showTables) {

        }

        @Override
        public void visit(Merge merge) {

        }

        @Override
        public void visit(Select select) {
            SelectBody selectBody = select.getSelectBody();
            if (selectBody != null) {
                selectBody.accept(this);
            }
        }

        @Override
        public void visit(Upsert upsert) {

        }

        @Override
        public void visit(UseStatement use) {

        }

        @Override
        public void visit(Block block) {

        }

        @Override
        public void visit(ValuesStatement values) {

        }

        @Override
        public void visit(DescribeStatement describe) {

        }

        @Override
        public void visit(ExplainStatement aThis) {

        }

        @Override
        public void visit(ShowStatement aThis) {

        }

        @Override
        public void visit(DeclareStatement aThis) {

        }

        @Override
        public void visit(Grant grant) {

        }

        @Override
        public void visit(CreateSequence createSequence) {

        }

        @Override
        public void visit(AlterSequence alterSequence) {

        }

        @Override
        public void visit(CreateFunctionalStatement createFunctionalStatement) {

        }

        @Override
        public void visit(CreateSynonym createSynonym) {

        }

        @Override
        public void visit(AlterSession alterSession) {

        }

        @Override
        public void visit(IfElseStatement aThis) {

        }

        @Override
        public void visit(RenameTableStatement renameTableStatement) {

        }

        @Override
        public void visit(PurgeStatement purgeStatement) {

        }

        @Override
        public void visit(AlterSystemStatement alterSystemStatement) {

        }

        @Override
        public void visit(UnsupportedStatement unsupportedStatement) {

        }

        @Override
        public void visit(PlainSelect plainSelect) {
            /** 处理select字段 */
            List<SelectItem> selectItems = plainSelect.getSelectItems();
            if (selectItems != null && selectItems.size() > 0) {
                selectItems.forEach(selectItem -> {
                    selectItem.accept(this);
                });
            }

            /** 处理表名或子查询 */
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem != null) {
                fromItem.accept(this);
            }
        }

        @Override
        public void visit(SetOperationList setOpList) {

        }

        @Override
        public void visit(WithItem withItem) {

        }

        @Override
        public void visit(AllColumns allColumns) {

        }

        @Override
        public void visit(AllTableColumns allTableColumns) {

        }

        // 这个方法我们并没有考虑完全，比如select项目中可能有子查询还有可能有case表达式，这些我们都没考虑，这里只是先展示了一种思路。
        @Override
        public void visit(SelectExpressionItem selectExpressionItem) {
            if (Function.class.isInstance(selectExpressionItem.getExpression())) {
                Function function = (Function) selectExpressionItem.getExpression();
                function.accept(this);
            }
        }

        @Override
        public void visit(BitwiseRightShift aThis) {

        }

        @Override
        public void visit(BitwiseLeftShift aThis) {

        }

        @Override
        public void visit(NullValue nullValue) {

        }

        // 实现将max函数转为min函数
        @Override
        public void visit(Function function) {
            if (function.getName().equalsIgnoreCase("max")) {
                function.setName("min");
            }
        }

        @Override
        public void visit(SignedExpression signedExpression) {

        }

        @Override
        public void visit(JdbcParameter jdbcParameter) {

        }

        @Override
        public void visit(JdbcNamedParameter jdbcNamedParameter) {

        }

        @Override
        public void visit(DoubleValue doubleValue) {

        }

        @Override
        public void visit(LongValue longValue) {

        }

        @Override
        public void visit(HexValue hexValue) {

        }

        @Override
        public void visit(DateValue dateValue) {

        }

        @Override
        public void visit(TimeValue timeValue) {

        }

        @Override
        public void visit(TimestampValue timestampValue) {

        }

        @Override
        public void visit(Parenthesis parenthesis) {

        }

        @Override
        public void visit(StringValue stringValue) {

        }

        @Override
        public void visit(Addition addition) {

        }

        @Override
        public void visit(Division division) {

        }

        @Override
        public void visit(IntegerDivision division) {

        }

        @Override
        public void visit(Multiplication multiplication) {

        }

        @Override
        public void visit(Subtraction subtraction) {

        }

        @Override
        public void visit(AndExpression andExpression) {

        }

        @Override
        public void visit(OrExpression orExpression) {

        }

        @Override
        public void visit(XorExpression orExpression) {

        }

        @Override
        public void visit(Between between) {

        }

        @Override
        public void visit(OverlapsCondition overlapsCondition) {

        }

        @Override
        public void visit(EqualsTo equalsTo) {

        }

        @Override
        public void visit(GreaterThan greaterThan) {

        }

        @Override
        public void visit(GreaterThanEquals greaterThanEquals) {

        }

        @Override
        public void visit(InExpression inExpression) {

        }

        @Override
        public void visit(FullTextSearch fullTextSearch) {

        }

        @Override
        public void visit(IsNullExpression isNullExpression) {

        }

        @Override
        public void visit(IsBooleanExpression isBooleanExpression) {

        }

        @Override
        public void visit(LikeExpression likeExpression) {

        }

        @Override
        public void visit(MinorThan minorThan) {

        }

        @Override
        public void visit(MinorThanEquals minorThanEquals) {

        }

        @Override
        public void visit(NotEqualsTo notEqualsTo) {

        }

        @Override
        public void visit(Column tableColumn) {

        }

        @Override
        public void visit(SubSelect subSelect) {

        }

        @Override
        public void visit(SubJoin subjoin) {

        }

        @Override
        public void visit(LateralSubSelect lateralSubSelect) {

        }

        @Override
        public void visit(ValuesList valuesList) {

        }

        @Override
        public void visit(TableFunction tableFunction) {

        }

        @Override
        public void visit(ParenthesisFromItem aThis) {

        }

        @Override
        public void visit(CaseExpression caseExpression) {

        }

        @Override
        public void visit(WhenClause whenClause) {

        }

        @Override
        public void visit(ExistsExpression existsExpression) {

        }

        @Override
        public void visit(AnyComparisonExpression anyComparisonExpression) {

        }

        @Override
        public void visit(Concat concat) {

        }

        @Override
        public void visit(Matches matches) {

        }

        @Override
        public void visit(BitwiseAnd bitwiseAnd) {

        }

        @Override
        public void visit(BitwiseOr bitwiseOr) {

        }

        @Override
        public void visit(BitwiseXor bitwiseXor) {

        }

        @Override
        public void visit(CastExpression cast) {

        }

        @Override
        public void visit(TryCastExpression cast) {

        }

        @Override
        public void visit(SafeCastExpression cast) {

        }

        @Override
        public void visit(Modulo modulo) {

        }

        @Override
        public void visit(AnalyticExpression aexpr) {

        }

        @Override
        public void visit(ExtractExpression eexpr) {

        }

        @Override
        public void visit(IntervalExpression iexpr) {

        }

        @Override
        public void visit(OracleHierarchicalExpression oexpr) {

        }

        @Override
        public void visit(RegExpMatchOperator rexpr) {

        }

        @Override
        public void visit(JsonExpression jsonExpr) {

        }

        @Override
        public void visit(JsonOperator jsonExpr) {

        }

        @Override
        public void visit(RegExpMySQLOperator regExpMySQLOperator) {

        }

        @Override
        public void visit(UserVariable var) {

        }

        @Override
        public void visit(NumericBind bind) {

        }

        @Override
        public void visit(KeepExpression aexpr) {

        }

        @Override
        public void visit(MySQLGroupConcat groupConcat) {

        }

        @Override
        public void visit(ValueListExpression valueList) {

        }

        @Override
        public void visit(RowConstructor rowConstructor) {

        }

        @Override
        public void visit(RowGetExpression rowGetExpression) {

        }

        @Override
        public void visit(OracleHint hint) {

        }

        @Override
        public void visit(TimeKeyExpression timeKeyExpression) {

        }

        @Override
        public void visit(DateTimeLiteralExpression literal) {

        }

        @Override
        public void visit(NotExpression aThis) {

        }

        @Override
        public void visit(NextValExpression aThis) {

        }

        @Override
        public void visit(CollateExpression aThis) {

        }

        @Override
        public void visit(SimilarToExpression aThis) {

        }

        @Override
        public void visit(ArrayExpression aThis) {

        }

        @Override
        public void visit(ArrayConstructor aThis) {

        }

        @Override
        public void visit(VariableAssignment aThis) {

        }

        @Override
        public void visit(XMLSerializeExpr aThis) {

        }

        @Override
        public void visit(TimezoneExpression aThis) {

        }

        @Override
        public void visit(JsonAggregateFunction aThis) {

        }

        @Override
        public void visit(JsonFunction aThis) {

        }

        @Override
        public void visit(ConnectByRootOperator aThis) {

        }

        @Override
        public void visit(OracleNamedFunctionParameter aThis) {

        }

        @Override
        public void visit(AllValue allValue) {

        }

        @Override
        public void visit(IsDistinctExpression isDistinctExpression) {

        }

        @Override
        public void visit(GeometryDistance geometryDistance) {

        }

        //实现表名称的更换
        @Override
        public void visit(Table table) {
            if (table.getName().equalsIgnoreCase("table1")) {
                table.setName("table2");
            }
        }
    }

}
