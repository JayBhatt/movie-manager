package news.MovieManager.service;

import news.MovieManager.exception.EntityNotFoundException;
import news.MovieManager.persistence.entity.UserRole;
import news.MovieManager.persistence.repository.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleDao userRoleDao;

    @Autowired
    public UserRoleServiceImpl(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserRole findByIdAndActive(long id, Boolean active) {
        UserRole role = this.userRoleDao.findByIdAndActive(id, active);
        if (role == null) {
            throw new EntityNotFoundException(String.format("No role found for id %s", id));
        }
        return role;
    }

    @Override
    public List<UserRole> findAllByActive(Boolean active) {
        return this.userRoleDao.findAllByActive(active);
    }

    @Override
    public UserRole findDistinctByRoleAndActive(String role, Boolean active) {
        UserRole userRole = this.userRoleDao.findDistinctByRoleAndActive(role, active);
        if (userRole == null) {
            throw new EntityNotFoundException(String.format("No role found for role %s", role));
        }
        return userRole;
    }

    @Override
    public UserRole save(UserRole userRole) {
        return this.userRoleDao.save(userRole);
    }
}
