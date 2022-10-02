package news.MovieManager.vendor.imdb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

}
