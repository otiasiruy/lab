package com.example.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.example.config.WebAppConfig;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Typically for middle-tier & data-tier beans (DataSource, security, etc.)
        return new Class<?>[] {};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Classes for Spring MVC configuration
        return new Class<?>[] { WebAppConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        // DispatcherServlet will map requests from "/"
        return new String[] { "/" };
    }
}