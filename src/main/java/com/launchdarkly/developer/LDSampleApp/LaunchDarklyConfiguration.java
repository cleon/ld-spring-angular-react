package com.launchdarkly.developer.LDSampleApp;

import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.server.Components;
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import java.time.Duration;

@Configuration
public class LaunchDarklyConfiguration {

        @Value("${ld.sdkkey}")
        private String sdkKey;

        public LaunchDarklyConfiguration() {
        }

        @Bean(destroyMethod = "close")
        public LDClient client() {
                var config = new LDConfig.Builder()
                                .events(Components.sendEvents().capacity(5000).flushInterval(Duration.ofSeconds(2)))
                                .build();
                return new LDClient(sdkKey, config);
        }

        @Bean
        @Scope(scopeName = "request", proxyMode = ScopedProxyMode.INTERFACES)
        public Supplier<LDContext> context(final HttpServletRequest request) {
                return () -> Optional.ofNullable(request.getParameter("userKey"))
                                .map(userKey -> LDContext.builder(userKey)
                                                .kind("user")
                                                .name(userKey)
                                                .set("email", request.getParameter("email"))
                                                .set("plan", request.getParameter("plan"))
                                                .build())
                                .orElseGet(() -> LDContext.builder(UUID.randomUUID().toString())
                                                .kind("user")
                                                .anonymous(true)
                                                .build());
        }
}
