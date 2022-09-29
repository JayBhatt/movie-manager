package news.MovieManager.persistence.repository;

import news.MovieManager.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserRoleDao extends IRepositoryAbstract<UserRole, Long> {
    @Query("SELECT DISTINCT ur FROM UserRole ur WHERE ur.role = :role AND ur.active = :active")
    Optional<UserRole> findDistinctByRoleAndActive(@Param("role") String role, @Param("active") Boolean active);

}
