package com.summary.biz.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.summary.common.core.json.LocalDateOfEpochDayDeserializer;
import com.summary.common.core.json.LocalDateTimeOfEpochMilliDeserializer;
import com.summary.common.core.json.LocalDateTimeToEpochMilliSerializer;
import com.summary.common.core.json.LocalDateToEpochDaySerializer;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jie.luo
 * @since 2024/6/22
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchProperties elasticsearchProperties) {

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));

        HttpHost[] httpHosts = new HttpHost[elasticsearchProperties.getHosts().size()];
        int i = 0;
        for (String host : elasticsearchProperties.getHosts()) {
            httpHosts[i] = HttpHost.create(host);
            i++;
        }

        RestClient restClient = RestClient
                .builder(httpHosts)
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    return httpClientBuilder;
                })
                .build();

        JacksonJsonpMapper jacksonJsonpMapper = new JacksonJsonpMapper();
        // 使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeToEpochMilliSerializer());
        timeModule.addSerializer(LocalDate.class, new LocalDateToEpochDaySerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeOfEpochMilliDeserializer());
        timeModule.addDeserializer(LocalDate.class, new LocalDateOfEpochDayDeserializer());
        jacksonJsonpMapper.objectMapper().registerModule(timeModule);

        ElasticsearchTransport transport = new RestClientTransport(restClient, jacksonJsonpMapper);

        return new ElasticsearchClient(transport);
    }

}
