package com.offcn.webui.config;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
@Component
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016103000779951";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCM8ytj0VQUgAMuf36IIDVQTBQ6YxcwX60cvhtO6YKgGHFpwe2TQNu1gV3ddNEcgvpCqMXv26leJWDPbJOUdpfRuRHM8kG2cci0psYWUe8uW73UsTJXzHQi/hARTjggz6kS7ZqQcar0XSC4+FytzauoLESqJD9FfhsECTp3Lbr0nZpDwcBRfa3mGVYMEv+6vGXzg6dDJsu1E8Dxfp6PKuAoGZRicCGuNhwBxlRnToPdTmFNevQ2ztBiRHzKI8chMD1wPWgYuUFQx1hfbz1/t5w+e/LstR2+LIPx5xLwTd8lPWincsABWYU3mpeFWaeCgjftwR7sIhZ/9v7mDA8AicOhAgMBAAECggEABiElB+g4gdO3T7RXqk/MQbutPYTFo2LlwlQ6F8pr1c56UCEQ1dCCPXhsvo6v8U941qFESlQBH4j8lsif+lrV61bjAUiftvFnywNdpebWQevXa4JxMARCIKtvw3DlW/fr4FjY2eM3jfzmyWRzk1NYXa3fJo8A+WKshhZopnokoTKQfhML8W7JSbOt6SCsJkHwJBozG1/MrruR07vbBIhwgJl4uLWyxOgMlQGJ2Xxgk1LBQiUP2O0DX1Kf8Xve2nPAZWkb6H4jXxfPgZf9fFMQ4XIvqWqJhjSBZQ/282/zC08GKaIl0LmFpRrAtJFIvcuxamxsc8y+stXg4+ug3+ehgQKBgQDpM1/eKp5RPCVNg3sE5uCZl1a8d0Zhy9IPcDSUG8FFXNhncSQ9un9yYLEIbr6dYuxb+XrTScM4fcRvkOSvQfx/5BiWIykStey+IT8kaCnSQ1NycGt2lb1ObRyH/0GOcxdQqKaaW1kVbWJYSXbOlIIsCtbPQQzAhVjRWrR56dwZqQKBgQCauukoWgHKNLwyf70NbXNi5wN9P6s7g410fKWYKLosk2LUt/NexxZIKiUHbsA4bMoqQUnJFCGp08sIAQhzGsqRyc7UVJfrTjebX9PXT+oFOCShN6TUiuEkfamqbxS3KZv9tR0J53QY9o6s4YYH5ph1sHLED5RNkrqWTD0aQCHFOQKBgCXA7XpYR/060Yc1DAuFHUOAXshwPvLCPqXFMp3vyGBkYZyNkqJzuQJZaQ/GbhGo3D8fqqTeQj65Db5KJNvCwRAra1eTJakjzsoRM9m/C1ZBFBdo39z47QbvXzkdOA3J0DJ8u0wuEcQIS22jpxpWrKRYtOIk092vOOvb1XAPTlTRAoGAdKqwORu8s8GVkaIdYy1+zjkPuXWzX9luZEHD/nne/tNWtrvIxlLo6xrT9sm8KA9nU/sMbpA+XbXzJaLZwyiVSt3WlOT/841zgjT8HyIDe1H81I6HOzdIUWJq9rZw8TZvhxnzvk8Fi/kEVThq/jQSCtMzhb+Ve/RBuifb/poSnIkCgYAVp7pZ6jRPg+WQ5wc55309N6lU54dZgM//K2NqHsQ2AqEM8N3gY0z9uef6wlsx9DAlfH1XG5VjBg4rtkTuAGvHtlHi1Yns//r6TYo7+PTS7pMFWyWhkPk+nUpgotcgr8cB8CtGUP2+vG4U80+r8D+wL2trHZUAzQ6OAbzrGyEQxw==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAktoBN0TWBQoLq6wZAM/GMPbfmzyKE7iMJJKLasfKpYHGXtPntSlEfIpBqJPV/JZJPaBN9XSHDeiXuPl5pESfwVSnZmGTzGgdROtGrYX7Xh7yRnh42s0HukTwo9NWbgCtCcsaWLaZlUzwsucDPsjieR0F83j1/SM098ipk4spAYHWTPOYZyYpAMg2NAWYe5fNDZfkIl54Yoi1dUDd0qM/0A9pcFpZzazkFi6HZMGDQzP1a8HQz0M0Rx7lyNGnRLGLAuvBVAp2F7+B5jQD99MdFywa9tQJ3DnuSNveBSQLXcXRjNpIUh49yfd1Pq072R7J+8Kqji2YFpPNrsE/7Z7kdQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://7syyiti0q7.52http.com/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://7syyiti0q7.52http.com/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
