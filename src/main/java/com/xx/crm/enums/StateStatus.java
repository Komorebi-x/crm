package com.xx.crm.enums;

/**
 * 分配状态枚举类
 *
 * 枚举的特征：
 *  1.枚举类都是继承了枚举类型：Java.lang.Enum
 *  2.枚举都是最终类，不可被继承
 *  3.枚举器的构造器都是私有的，枚举对外不能创建对象
 *  4.枚举类的第一行默认都是罗列枚举对象的名称的
 *  5.枚举类相当于是多例模式，（固定几个）
 */
public enum  StateStatus {
    // 未分配
    UNSTATE(0),
    // 已分配
    STATED(1);

    private Integer type;

    StateStatus(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
