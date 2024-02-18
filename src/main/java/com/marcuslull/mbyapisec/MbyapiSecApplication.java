package com.marcuslull.mbyapisec;

import com.marcuslull.mbyapisec.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class MbyapiSecApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbyapiSecApplication.class, args);
    }

}
