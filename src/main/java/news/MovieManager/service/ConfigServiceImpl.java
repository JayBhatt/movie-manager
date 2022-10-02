package news.MovieManager.service;

import news.MovieManager.exception.EntityNotFoundException;
import news.MovieManager.persistence.entity.Config;
import news.MovieManager.persistence.repository.ConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigDao configDao;

    @Autowired
    public ConfigServiceImpl(ConfigDao configDao) {
        this.configDao = configDao;
    }

    @Override
    public Config findDistinctByKeyAndActive(String key, Boolean active) {
        Config config = this.configDao.findDistinctByKeyAndActive(key, active);
        if (config == null) {
            throw new EntityNotFoundException(String.format("No config found for key %s", key));
        }
        return config;
    }

    @Override
    public Config findByIdAndActive(long id, Boolean active) {
        Config config = this.configDao.findByIdAndActive(id, active);
        if (config == null) {
            throw new EntityNotFoundException(String.format("No config found for id %s", id));
        }
        return config;
    }

    @Override
    public List<Config> findAllByActive(Boolean active) {
        return this.configDao.findAllByActive(active);
    }

    @Override
    public Config save(Config config) {
        return this.configDao.save(config);
    }

    public Config update(Config config) {
        return this.configDao.save(config);
    }
}
