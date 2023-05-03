package com.example.boot05webadminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class Boot05WebAdminserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot05WebAdminserverApplication.class, args);
    }

}
