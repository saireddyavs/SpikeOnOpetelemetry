package com.tw.Microservice;


import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class MicroserviceApplication {

    private Tracer tracer;

    @PostConstruct
    public void init() {
        tracer = (Tracer) opentelemetry.initOpenTelemetry("localhost", 14250).getTracer("io.opentelemetry.Microservivefour");
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApplication.class, args);
    }

    @Bean
    public Tracer tracer() {

        return tracer;
    }

}

@RestController
class Microservice4Controller {

    private final Tracer tracer;

    @Autowired
    public Microservice4Controller(Tracer tracer) {
        this.tracer = tracer;
    }

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @GetMapping(value = "/microservice4")
    public String method4() {
        Span span = this.tracer.spanBuilder("Start MicroService four").startSpan();
        LOG.info("Inside method4--begin");
        span.addEvent("Dummy evnt");
        LOG.info("Inside method4--end");

        span.end();
        return "Hello world from java microservice-4";

    }
}