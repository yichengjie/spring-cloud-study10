package com.yicj.mybatis.plugin;

import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import org.junit.jupiter.api.Test;

/**
 * @author: yicj
 * @date: 2023/8/11 15:12
 */
public class IllegalSQLInnerInterceptorTest {
    private final IllegalSQLInnerInterceptor interceptor = new IllegalSQLInnerInterceptor();

    @Test
    void test() {
        interceptor.parserSingle("SELECT COUNT(*) AS total FROM t_user WHERE (client_id = ?)", null);
    }

}
