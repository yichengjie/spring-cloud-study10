#### 初始化构建重要接口及类
1. SqlSessionFactoryBuilder: 构建出SqlSessionFactory
2. SqlSessionFactory: SqlSessionFactory有六个方法创建SqlSession实例
3. XMLConfigBuilder: 构建出Configuration对象
4. Configuration: MyBatis所有的配置信息都维护在Configuration对象之中
5. SqlSession: 作为MyBatis工作的主要顶层API，表示和数据库交互的会话。完成必要数据库增删改查功能
#### 执行重要接口及类
1. Executor: MyBatis执行器，是MyBatis调度的核心类，负责SQL语句的生成和查询缓存的维护
2. StatementHandler: 封装JDBC Statement操作、负责对JDBC statement的操作，如设置参数、将Statement结果集转换成List集合
3. ParameterHandler: 负责对用户传入的参数转换成JDBC Statement所需要的参数
4. TypeHandler: 负责Java数据类型和JDBC数据类型之间的影射和转换
5. MappedStatement: MappedStatement维护了一条<select|update|delete|insert>节点的封装
6. SqlSource: 负责根据用户传递的parameterObject，动态地生成SQL语句，将信息封装到BoundSql对象种，并返回
7. BoundSql: 表示动态生成的SQL语句以及相应的参数信息
8. ResultSetHandler: 负责将JDBC返回的ResultSet结果集对象转换成List类型的集合