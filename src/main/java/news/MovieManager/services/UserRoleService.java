package news.MovieManager.services;

import news.MovieManager.persistence.entity.UserRole;
import java.util.Optional;

public interface UserRoleService extends ServiceAbstract<UserRole> {

    Optional<UserRole> findDistinctByRoleAndActive(String role, Boolean active);

}
