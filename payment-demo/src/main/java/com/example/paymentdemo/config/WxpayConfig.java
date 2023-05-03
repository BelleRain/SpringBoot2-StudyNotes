package com.example.paymentdemo.config;


import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

@Configuration
@PropertySource("classpath:wxpay.properties")  //读取配置文件
@ConfigurationProperties(prefix = "wxpay")  //读取wxpay节点
@Data
public class WxpayConfig {

    // 商户号
    private String mchId;

    // 商户API证书序列号
    private String mchSerialNo;

    // 商户私钥文件
    private String privateKeyPath;

    // APIv3密钥
    private String apiV3Key;

    // APPID
    private String appid;

    // 微信服务器地址
    private String domain;

    // 接收结果通知地址
    private String notifyDomain;

    /**
     * 如何加载商户私钥
     * 商户申请商户API证书时，会生成商户私钥，并保存在本地证书文件夹的文件apiclient_key.pem中。商户开发者可以使用方法PemUtil.loadPrivateKey()加载证书。
     *
     * # 示例：私钥存储在文件
     * PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
     *         new FileInputStream("/path/to/apiclient_key.pem"));
     *
     * # 示例：私钥为String字符串
     * PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
     *         new ByteArrayInputStream(privateKey.getBytes("utf-8")));
     */

    //获取商户的私钥文件 测试时改为public类型
    private PrivateKey getPrivateKey(String filename) throws FileNotFoundException {

        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                    new FileInputStream(filename));
        return merchantPrivateKey;
    }


    /**String requestId
     * 获取签名验证器
     * @return
     * @throws IOException
     * @throws HttpCodeException
     * @throws GeneralSecurityException
     */
    @Bean
    public Verifier getVerify() throws IOException, HttpCodeException, GeneralSecurityException, NotFoundException {

        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(privateKeyPath);

        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();

        //获取私钥签名
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(mchSerialNo, privateKey);

        //身份认证对象
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchId, privateKeySigner);

        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(mchId,
                wechatPay2Credentials,
                apiV3Key.getBytes(StandardCharsets.UTF_8));
        // ... 若有多个商户号，可继续调用putMerchant添加商户信息

        // 从证书管理器中获取verifier
        Verifier verifier = certificatesManager.getVerifier(mchId);

        return verifier;
    }


    /**
     * 构造支付请求的httpClient
     * @return
     */
    @Bean
    public CloseableHttpClient getWxpayClient(Verifier verifier) throws FileNotFoundException {

        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(privateKeyPath);

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator(new WechatPay2Validator(verifier));
        // ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();

        return httpClient;
    }

}
