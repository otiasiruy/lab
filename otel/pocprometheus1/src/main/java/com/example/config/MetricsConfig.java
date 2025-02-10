package com.example.config;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

    @Bean
    public ClassLoaderMetrics classLoaderMetrics(PrometheusMeterRegistry registry) {
        ClassLoaderMetrics binder = new ClassLoaderMetrics();
        binder.bindTo(registry);
        return binder;
    }

    @Bean
    public JvmMemoryMetrics jvmMemoryMetrics(PrometheusMeterRegistry registry) {
        JvmMemoryMetrics binder = new JvmMemoryMetrics();
        binder.bindTo(registry);
        return binder;
    }

    @Bean
    public JvmGcMetrics jvmGcMetrics(PrometheusMeterRegistry registry) {
        JvmGcMetrics binder = new JvmGcMetrics();
        binder.bindTo(registry);
        return binder;
    }

    @Bean
    public ProcessorMetrics processorMetrics(PrometheusMeterRegistry registry) {
        ProcessorMetrics binder = new ProcessorMetrics();
        binder.bindTo(registry);
        return binder;
    }
}