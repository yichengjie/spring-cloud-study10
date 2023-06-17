package com.yicj.database.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author: yicj
 * @date: 2023/6/17 11:53
 */
@Slf4j
@SpringBootTest
public class HelloTest {

    @Autowired
    private JdbcTemplate jdbcTemplate ;

    @Test
    public void initTable(){
        jdbcTemplate.execute("create table user(id int primary key, name varchar(50))");
        jdbcTemplate.execute("insert into user values(1, 'Alice')");
        jdbcTemplate.execute("insert into user values(2, 'Bob')");
    }

    @Test
    public void listAll(){
        this.initTable();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user ");
        maps.forEach(item -> log.info("====> {}", item));
    }

}
