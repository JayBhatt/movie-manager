package news.MovieManager.vendor.imdb.helper;

import news.MovieManager.model.Status;
import news.MovieManager.persistence.entity.Config;
import news.MovieManager.service.ConfigServiceImpl;
import news.MovieManager.vendor.imdb.model.ImdbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ConfigHelper {

    private final ConfigServiceImpl configService;

    private static final String CONFIG_PREFIX = "imdb";


    @Autowired
    public ConfigHelper(ConfigServiceImpl configService) {
        this.configService = configService;
    }

    public ImdbConfig getConfig() {
        List<Config> configList = this.configService.findAllByActive(Status.ACTIVE.getStatus());
        ImdbConfig imdbConfig = new ImdbConfig();
        for(Config config : configList) {
            imdbConfig.setProperty(config.getKey().replaceFirst(ConfigHelper.CONFIG_PREFIX, ""), config.getValue());
        }
        return imdbConfig;
    }

}
