package news.MovieManager.persistence.repository;

import news.MovieManager.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends RepositoryAbstract<UserRole, Long> {
    @Query("SELECT DISTINCT ur FROM UserRole ur WHERE ur.role = :role AND ur.active = :active")
    UserRole findDistinctByRoleAndActive(@Param("role") String role, @Param("active") Boolean active);

}
