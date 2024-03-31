package com.marcuslull.mbyapisec.model.record;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public record StorageProperties(String location) {
}
