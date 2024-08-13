package com.ec.tt;

import com.ec.tt.config.BaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Import({ BaseConfiguration.class })
@SpringBootApplication(scanBasePackages = { "com.ec.tt" })
public class BaseApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }
}
