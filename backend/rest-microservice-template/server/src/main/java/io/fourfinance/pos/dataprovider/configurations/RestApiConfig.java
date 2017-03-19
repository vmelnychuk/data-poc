package io.fourfinance.pos.dataprovider.configurations;

import io.fourfinance.pos.dataprovider.service.ApplicationResourceImpl;
import io.fourfinance.pos.dataprovider.service.PingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class RestApiConfig extends ResourceConfig {

    public RestApiConfig() {
        register(PingResource.class);
        register(ApplicationResourceImpl.class);
    }

}
