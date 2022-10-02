package news.MovieManager.persistence.repository;

import news.MovieManager.persistence.entity.MovieUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserDao extends RepositoryAbstract<MovieUser, Long> {
    @Query("SELECT DISTINCT mu FROM MovieUser mu WHERE mu.email = :email AND mu.active = :active")
    Optional<MovieUser> findDistinctByEmailAndActive(@Param("email") String email, @Param("active") Boolean active);

    @Query("SELECT DISTINCT mu FROM MovieUser mu WHERE mu.id = :id AND mu.active = :active")
    MovieUser findDistinctByIdAndActive(@Param("id") Long id, @Param("active") Boolean active);

}
