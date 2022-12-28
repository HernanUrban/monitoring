package com.urban.observability.user.logging

import io.opentracing.Tracer
import io.opentracing.noop.NoopTracerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(value = ["opentracing.jaeger.enabled"], havingValue = "false", matchIfMissing = false)
class TracerConfig {
    @Bean("noopTracer")
    fun jaegerTracer(): Tracer = NoopTracerFactory.create()
}
