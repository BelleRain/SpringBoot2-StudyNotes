package com.example.admin.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class MyHealthIndicator extends AbstractHealthIndicator{

    /**
     * 真实的检查方法
     * @param builder
     * @throws Exception
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        //mongodb 获取连接进行测试
        Map<String, Object> map = new HashMap<>();
        //检查完成
        if (1==1){
            //builder.up();
            builder.status(Status.UP);
            map.put("count",1);
            map.put("ms",100);
        }else {
            //builder.down();
            builder.status(Status.OUT_OF_SERVICE);
            map.put("msg","time out");
            map.put("ms",3000);
        }
        builder.withDetail("code",100).withDetails(map);
    }
}



//@Component
//public class MyHealthIndicator implements HealthIndicator {
//    @Override
//    public Health health() {
//        int errorCode = 500; // perform some specific health check
//        if (errorCode != 0) {
//            Health build = Health.down()
//                    .withDetail("msg", "error service")
//                    .withDetail("code", "500")
//                    .withException(new RuntimeException())
//                    .build();
//            return build;
//            //return Health.down().withDetail("Error Code", errorCode).build();
//        }
//        return Health.up().withDetail("msg","成功").build();
//    }
//
//}
