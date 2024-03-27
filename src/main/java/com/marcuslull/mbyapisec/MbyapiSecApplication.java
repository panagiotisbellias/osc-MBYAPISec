package com.marcuslull.mbyapisec;

import com.marcuslull.mbyapisec.model.record.RsaKeyProperties;
import com.marcuslull.mbyapisec.model.record.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyProperties.class, StorageProperties.class})
public class MbyapiSecApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbyapiSecApplication.class, args);
    }

}
