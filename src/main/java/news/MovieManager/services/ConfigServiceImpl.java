package news.MovieManager.services;

import news.MovieManager.persistence.entity.Config;
import news.MovieManager.persistence.repository.IConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final IConfigDao configDao;

    @Autowired
    public ConfigServiceImpl(IConfigDao configDao) {
        this.configDao = configDao;
    }

    @Override
    public Optional<Config> findDistinctByKeyAndActive(String key, Boolean active) {
        return this.configDao.findDistinctByKeyAndActive(key, active);
    }

    @Override
    public Optional<Config> findByIdAndActive(long id, Boolean active) {
        return this.configDao.findByIdAndActive(id, active);
    }

    @Override
    public List<Config> findAllByActive(Boolean active) {
        return this.configDao.findAllByActive(active);
    }

    @Override
    public Config save(Config config) {
        return this.configDao.save(config);
    }

    public void update(Config config) {
        this.configDao.save(config);
    }
}
