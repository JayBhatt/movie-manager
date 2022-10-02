package news.MovieManager.vendor.imdb.config;

import news.MovieManager.vendor.imdb.model.ImdbConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImdbConfiguration {

    @Bean
    protected ImdbConfig imdbConfig() {
        return new ImdbConfig();
    }

}
