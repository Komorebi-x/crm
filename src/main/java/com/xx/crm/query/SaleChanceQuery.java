package com.xx.crm.query;

import com.xx.crm.base.BaseQuery;

/**
 * @ClassName SaleChanceQuery
 * @Description  营销机会管理多条件查询条件
 * @Author xu
 * @Date 2021/10/25 13:47
 * @Version 1.0
 */
public class SaleChanceQuery extends BaseQuery {

    //分页参数

    //条件参数
    private String customerName; //客户名
    private String createMan; //创建人
    private Integer state; // 分配状态 0：未分配   1：已分配

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
