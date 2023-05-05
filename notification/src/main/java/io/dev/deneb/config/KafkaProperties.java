package io.dev.deneb.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "deneb.kafka")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaProperties{
  private String topic;
}
