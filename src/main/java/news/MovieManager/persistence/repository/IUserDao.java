package news.MovieManager.persistence.repository;

import news.MovieManager.persistence.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends IRepositoryAbstract<User, Long> {
    @Query("SELECT DISTINCT u FROM User u WHERE u.email = :email AND u.active = :active")
    User findDistinctByEmailAndActive(@Param("email") String email, @Param("active") Boolean active);

}
