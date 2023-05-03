package com.example.admin.actuator.endpoint;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Endpoint(id = "myservice")
public class DockerEndpoint {

    @ReadOperation
    public Map getDocketInfo(){
        //端点的读操作  http://localhost:8080/actuator/myservice
        return Collections.singletonMap("docketInfo","docket started......");
    }

    @WriteOperation
    public void stopDocket(){
        System.out.println("docker stopped.....");
    }

}
