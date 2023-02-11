package it.demib.stabletoolkitback.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MongoDbConfiguration {

  private final MongoClient mongoClient;

  @Value("${spring.data.mongodb.database}")
  private String databaseName;

  @Bean("IMAGE")
  public MongoCollection<Document> getImageCollection() {
    return mongoClient.getDatabase(databaseName).getCollection("image");
  }

  @Bean("TAG")
  public MongoCollection<Document> getTagCollection() {
    return mongoClient.getDatabase(databaseName).getCollection("tag");
  }
}
