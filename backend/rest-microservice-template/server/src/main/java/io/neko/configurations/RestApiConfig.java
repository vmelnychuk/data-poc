package io.fourfinance.pos.dataprovider.configurations;


import io.neko.service.EchoResorce;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class RestApiConfig extends ResourceConfig {

    public RestApiConfig() {
        register(Echo.class);
    }

}
