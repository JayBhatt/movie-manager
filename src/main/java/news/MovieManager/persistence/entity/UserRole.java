package news.MovieManager.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends EntityAbstract {

    @Column(unique = true, nullable = false, length = 20)
    private String role;

    @Column(nullable = false, length = 200)
    private String description;

}