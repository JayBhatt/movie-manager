package news.MovieManager.services;

import news.MovieManager.persistence.entity.User;
import news.MovieManager.persistence.repository.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final IUserDao userDao;

    @Autowired
    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findDistinctByEmailAndActive(String email, Boolean active) {
        return this.userDao.findDistinctByEmailAndActive(email, active);
    }

    @Override
    public Optional<User> findByIdAndActive(long id, Boolean active) {
        return this.userDao.findByIdAndActive(id, active);
    }

    @Override
    public List<User> findAllByActive(Boolean active) {
        return this.userDao.findAllByActive(active);
    }

    @Override
    public User save(User user) {
        return this.userDao.save(user);
    }

}
