package com.marcuslull.mbyapisec.config;

import com.marcuslull.mbyapisec.model.Animal;
import com.marcuslull.mbyapisec.model.Plant;
import com.marcuslull.mbyapisec.model.User;
import com.marcuslull.mbyapisec.model.Yard;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(User.class, Yard.class, Plant.class, Animal.class);
    }
}
