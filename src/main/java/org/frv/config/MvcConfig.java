package org.frv.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.frv"})
public class MvcConfig implements WebMvcConfigurer {
}
