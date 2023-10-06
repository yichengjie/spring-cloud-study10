package com.yicj.study.mybatis.model.vo;

import org.apache.ibatis.session.RowBounds;

/**
 * @author yicj
 * @date 2023/10/6 20:35
 */
public class PageRowBounds extends RowBounds {
    private Long total;

    public PageRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    public PageRowBounds() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
