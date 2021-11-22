package com.xx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xx.crm.base.BaseService;
import com.xx.crm.dao.SaleChanceMapper;
import com.xx.crm.enums.DevResult;
import com.xx.crm.enums.StateStatus;
import com.xx.crm.query.SaleChanceQuery;
import com.xx.crm.utils.AssertUtil;
import com.xx.crm.utils.PhoneUtil;
import com.xx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SaleChanceService
 * @Description TODO
 * @Author xu
 * @Date 2021/10/25 13:30
 * @Version 1.0
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 多条件分页查询营销机会（返回的数据格式必须满足LayUi中数据表格要求的格式）
     * @param saleChanceQuery
     * @return
     */
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){

        Map<String,Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getLimit());
        //得到对应分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));

        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance){
        /*1.参数校验*/
        //营销机会ID  非空，数据库中对应的记录存在
        AssertUtil.isTrue(null == saleChance.getId(),"待更新记录不存在！");
        //通过主键查询对象
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        //判断数据库中对应的记录存在
        AssertUtil.isTrue(temp == null,"待更新记录不存在！");
        //参数校验
        checkSaleChanceParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());

//       /*2.设置相关参数的默认值*/
        //updateDate更新时间  设置为系统当前时间
    }

    /**
     *
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
        /*1.参数校验*/
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());

        /*2.设置相关字段的默认值*/
        //IsValid是否有效（0=无效，1=有效）
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        //判断是否分配了
        if (StringUtils.isBlank(saleChance.getAssignMan())){
            //如果为空，表示未设置指派人
            //state分配状态，（0：未分配，1：已分配）
            saleChance.setState(StateStatus.UNSTATE.getType());
            //AssignTime指派时间，设置为null
            saleChance.setAssignTime(null);
            //DevResult开发状态（0：未开发，1：开发中，2：开发成功，3：开发失败）
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }else {
            //如果不为空，表示设置了指派人
            saleChance.setState(StateStatus.STATED.getType());
            //AssignTime指派时间，系统当前时间
            saleChance.setAssignTime(new Date());
            //DevResult开发状态（0：未开发，1：开发中，2：开发成功，3：开发失败）
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }

        //3.执行添加操作，判断受影响的行数
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance) < 1,"添加营销机会失败！");


    }

    /**
     *  参数校验
     *  customerName:非空
     *  linkMan:非空
     *  linkPhone:非空 11位手机号
     *
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {

        //customerName客户名称   非空
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "请输入客户名！");
        //linkMan联系人  非空
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "请输入联系人！");
        //linkPhone联系号码  非空
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "请输入手机号！");
        //linkPhone联系号码  非空，手机号格式正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"手机号格式不正确！");

    }

}
