package com.ocs.restfulweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/messages.properties", encoding="UTF-8")
public class AppConfig {
}
