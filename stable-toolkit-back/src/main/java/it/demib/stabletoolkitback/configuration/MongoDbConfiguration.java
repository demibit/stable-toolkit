package it.demib.stabletoolkitback.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor(onConstructor_ = @Autowired)
public class MongoDbConfiguration {

  private final MongoClient mongoClient;

  @Bean("IMAGE")
  public MongoCollection<Document> getImageCollection() {
    return mongoClient.getDatabase("stable-toolkit").getCollection("image");
  }

  @Bean("TAG")
  public MongoCollection<Document> getTagCollection() {
    return mongoClient.getDatabase("stable-toolkit").getCollection("tag");
  }
}
