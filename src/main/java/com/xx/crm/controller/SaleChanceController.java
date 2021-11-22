package com.xx.crm.controller;

import com.xx.crm.base.BaseController;
import com.xx.crm.base.ResultInfo;
import com.xx.crm.query.SaleChanceQuery;
import com.xx.crm.service.SaleChanceService;
import com.xx.crm.utils.CookieUtil;
import com.xx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName SaleChanceController
 * @Description TODO
 * @Author xu
 * @Date 2021/10/25 13:31
 * @VersRest1.0
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    /**
     * 营销机会数据查询（分页多条件查询）
     * @param saleChanceQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request){
        //从Cookie中获取当前登录的用户名
        String userName = CookieUtil.getCookieValue(request, "userName");
        //设置用户名到营销机会对象
        saleChance.setCreateMan(userName);
        //调用Service层的添加方法
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会数据添加成功！");
    }

    /**
     * 进入添加/修改营销机会数据界面
     * @return
     */
    @RequestMapping("addOrUpdateDialog")
    public String addOrUpdateDialog(){

        return "saleChance/add_update";
    }
}
