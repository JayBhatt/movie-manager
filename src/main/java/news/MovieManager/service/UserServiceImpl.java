package news.MovieManager.service;

import news.MovieManager.exception.EntityNotFoundException;
import news.MovieManager.persistence.entity.MovieUser;
import news.MovieManager.persistence.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<MovieUser> findDistinctByEmailAndActive(String email, Boolean active) {
        return this.userDao.findDistinctByEmailAndActive(email, active);
    }

    public MovieUser findDistinctByIdAndActive(Long id, Boolean active) {
        MovieUser movieUser = this.userDao.findDistinctByIdAndActive(id, active);
        if (movieUser == null) {
            throw new EntityNotFoundException(String.format("No role found for id %s", id));
        }
        return movieUser;
    }

    @Override
    public MovieUser findByIdAndActive(long id, Boolean active) {
        return this.userDao.findByIdAndActive(id, active);
    }

    @Override
    public List<MovieUser> findAllByActive(Boolean active) {
        return this.userDao.findAllByActive(active);
    }

    @Override
    public MovieUser save(MovieUser movieUser) {
        return this.userDao.save(movieUser);
    }

}
