package news.MovieManager.service;

import news.MovieManager.persistence.entity.Config;

public interface ConfigService extends ServiceAbstract<Config> {

    Config findDistinctByKeyAndActive(String key, Boolean active);



}
