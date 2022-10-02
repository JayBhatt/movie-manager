package news.MovieManager.service;

import news.MovieManager.persistence.entity.MovieUser;
import java.util.Optional;

public interface UserService extends ServiceAbstract<MovieUser> {

    Optional<MovieUser> findDistinctByEmailAndActive(String email, Boolean active);

    MovieUser findDistinctByIdAndActive(Long id, Boolean active);

}
