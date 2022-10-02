package news.MovieManager.model;

import lombok.Data;
import java.util.Date;

@Data
public class Token {

    private Date expiration;

    private String subject;

}
