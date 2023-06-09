package io.dev.deneb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class BookApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(BookApiApplication.class, args);
  }
}
