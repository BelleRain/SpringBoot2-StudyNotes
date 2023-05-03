package com.example.paymentdemo;

import com.example.paymentdemo.config.WxpayConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.security.PrivateKey;
import java.util.Random;

@SpringBootTest
class PaymentDemoApplicationTests {

    @Autowired
    private WxpayConfig wxpayConfig;


 /*   @Test
    void testgetPrivateKey() throws FileNotFoundException {

        String privateKeyPath = wxpayConfig.getPrivateKeyPath();
        PrivateKey privateKey = wxpayConfig.getPrivateKey(privateKeyPath);
        System.out.println("=================");
        System.out.println(privateKey);
    }*/

    @Test
    public void testNextint(){
        Random random = new Random();
        System.out.println("=====================");
        System.out.println(random.nextInt(20));
    }

}
