package news.MovieManager.vendor.imdb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("title")
    private String title;

}
