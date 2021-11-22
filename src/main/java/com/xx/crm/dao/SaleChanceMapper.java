package com.xx.crm.dao;

import com.xx.crm.base.BaseMapper;
import com.xx.crm.vo.SaleChance;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {

    /**
     * 多条件查询的接口不需要单独定义
     * 由于多个模块涉及到多条件查询，所以将对应的多条件查询功能定义在父接口BaseMapper
     */



}