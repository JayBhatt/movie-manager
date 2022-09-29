package news.MovieManager.services;

import news.MovieManager.persistence.entity.UserRole;
import news.MovieManager.persistence.repository.IUserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final IUserRoleDao userRoleDao;

    @Autowired
    public UserRoleServiceImpl(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public Optional<UserRole> findByIdAndActive(long id, Boolean active) {
        return this.userRoleDao.findByIdAndActive(id, active);
    }

    @Override
    public List<UserRole> findAllByActive(Boolean active) {
        return this.userRoleDao.findAllByActive(active);
    }

    @Override
    public Optional<UserRole> findDistinctByRoleAndActive(String role, Boolean active) {
        return this.userRoleDao.findDistinctByRoleAndActive(role, active);
    }

    @Override
    public UserRole save(UserRole userRole) {
        return this.userRoleDao.save(userRole);
    }
}
