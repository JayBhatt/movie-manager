package news.MovieManager.service;

import news.MovieManager.persistence.entity.UserRole;

public interface UserRoleService extends ServiceAbstract<UserRole> {

    UserRole findDistinctByRoleAndActive(String role, Boolean active);

}
