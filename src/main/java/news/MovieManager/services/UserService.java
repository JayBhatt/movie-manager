package news.MovieManager.services;

import news.MovieManager.persistence.entity.User;

public interface UserService extends ServiceAbstract<User> {

    User findDistinctByEmailAndActive(String email, Boolean active);

}
