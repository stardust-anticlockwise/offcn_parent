package com.offcn.webui.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.offcn.response.AppResponse;
import com.offcn.webui.config.AlipayConfig;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.resp.OrderFormInfoSubmitVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import com.offcn.webui.vo.resp.TOrder;
import com.offcn.webui.vo.resp.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private OrderServiceFeign orderServiceFeign;

    @RequestMapping("/order/pay")
    public String OrderPay(OrderFormInfoSubmitVo vo, HttpSession session){

        UserRespVo userRespVo = (UserRespVo) session.getAttribute("sessionMember");
        if (userRespVo == null){
            return "redirect:/login";
        }
        String accessToken =userRespVo.getAccessToken();
        vo.setAccessToken(accessToken);
        ReturnPayConfirmVo confirmVo = (ReturnPayConfirmVo) session.getAttribute("returnConfirmSession");
        if (confirmVo == null){
            return "redirect:/login";
        }
        vo.setProjectid(confirmVo.getProjectId());
        vo.setReturnid(confirmVo.getId());
        vo.setRtncount(confirmVo.getNum());

        AppResponse<TOrder> order =orderServiceFeign.createOrder(vo);
        TOrder data =order.getData();
        String orderName =confirmVo.getProjectName();
        System.out.println("orderNum:"+data.getOrdernum());
        System.out.println("money:"+data.getMoney());
        System.out.println("orderName:"+orderName);
        System.out.println("Remark:"+vo.getRemark());
        String result =payOrder(data.getOrdernum(),data.getMoney(),orderName,vo.getRemark());
        return "/member/minecrowdfunding";
    }

    /**
     * 支付
     * 商户订单号，商户网站订单系统中唯一订单号，必填
     * 付款金额，必填
     * 订单名称，必填
     * 商品描述，可空
     * */
    private String payOrder(String orderNum,Integer money,String orderName,String remark){
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        alipayRequest.setBizContent("{\"out_trade_no\":\""+orderNum+"\","
                + "\"total_amount\":\""+money+"\","
                + "\"subject\":\""+orderName+"\","
                + "\"body\":\""+remark+"\","
                +"\"project_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println(result);
            return  result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
