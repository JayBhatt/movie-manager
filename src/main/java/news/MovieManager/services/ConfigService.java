package news.MovieManager.services;

import news.MovieManager.persistence.entity.Config;

import java.util.Optional;

public interface ConfigService extends ServiceAbstract<Config> {

    Optional<Config> findDistinctByKeyAndActive(String key, Boolean active);

}
