package com.offcn.order.service.impl;

import com.offcn.enums.OrderStatusEnume;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.po.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.order.vo.resp.TReturn;
import com.offcn.response.AppResponse;
import com.offcn.util.AppDateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Override
    public TOrder saveOrder(OrderInfoSubmitVo vo) {
        TOrder order = new TOrder();
        String accessToken = vo.getAccessToken();
        String memberId = redisTemplate.opsForValue().get(accessToken);  //得到会员ID
        order.setMemberid(Integer.parseInt(memberId));
        //2.复制对象  orderInfoSubmitVo---》order
        BeanUtils.copyProperties(vo, order);
        String orderNum = UUID.randomUUID().toString().replace("-", "");  //产生订单编号
        order.setOrdernum(orderNum);
        order.setStatus(OrderStatusEnume.UNPAY.getCode() + "");          //支付状态  未支付
        order.setInvoice(vo.getInvoice().toString());    //发票状态
        order.setCreatedate(AppDateUtils.getFormatTime());              //创建时间
        //3.服务远程调用  查询回报增量列表
        AppResponse<List<TReturn>> response = projectServiceFeign.getReturnList(vo.getProjectid());
        List<TReturn> returnList = response.getData();
        //默认取得第一个回报增量信息
        TReturn tReturn = returnList.get(0);
        //支持金额  回报数量*回报支持金额+运费
        Integer money = order.getRtncount() * tReturn.getSupportmoney() + tReturn.getFreight();
        order.setMoney(money);
        //4.执行保存操作
        orderMapper.insertSelective(order);
        return order;
    }
}
