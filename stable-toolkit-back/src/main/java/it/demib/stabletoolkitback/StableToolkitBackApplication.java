package it.demib.stabletoolkitback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StableToolkitBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(StableToolkitBackApplication.class, args);
  }

}
