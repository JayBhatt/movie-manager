package news.MovieManager.controller;

import news.MovieManager.dto.ConfigDto;
import news.MovieManager.model.Status;
import news.MovieManager.persistence.entity.Config;
import news.MovieManager.services.ConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigServiceImpl configService;

    @Autowired
    public ConfigController(ConfigServiceImpl configService) {
        this.configService = configService;
    }

    @GetMapping
    @ResponseBody
    public List<Config> getAllConfig() {
        return this.configService.findAllByActive(Status.ACTIVE.getStatus());
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateConfig(@PathVariable("id") Long id, @Validated @RequestBody ConfigDto configDto) {
        Config config = configDto.toConfig();
        if(!Objects.equals(id, config.getId())){
            throw new IllegalArgumentException("Tempered request.");
        }
        this.configService.update(config);
    }

}
