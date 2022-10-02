package news.MovieManager.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Config extends EntityAbstract {

    @Column(name = "config_key", unique = true, nullable = false, length = 200)
    private String key;

    @Column(name = "config_value")
    @Type(type = "text")
    private String value;

}
