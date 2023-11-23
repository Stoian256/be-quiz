package com.example.bequiz.configuration;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;


@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    String clientOriginUrl;

    @ConstructorBinding
    public ApplicationProperties(final String clientOriginUrl) {
        this.clientOriginUrl = clientOriginUrl;
    }


    public String getClientOriginUrl() {
        return clientOriginUrl;
    }
}
