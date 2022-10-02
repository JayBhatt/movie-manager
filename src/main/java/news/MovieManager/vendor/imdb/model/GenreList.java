package news.MovieManager.vendor.imdb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreList {

    @JsonProperty("genres")
    private List<Genre> genreList;

}
