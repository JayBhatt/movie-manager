package news.MovieManager.controller;

import news.MovieManager.dto.ConfigDto;
import news.MovieManager.model.Status;
import news.MovieManager.persistence.entity.Config;
import news.MovieManager.service.ConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<Config>> getAllConfig() {
        return ResponseEntity.ok(this.configService.findAllByActive(Status.ACTIVE.getStatus()));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Config> updateConfig(@PathVariable("id") Long id, @Validated @RequestBody ConfigDto configDto) {
        Config config = configDto.toConfig();
        if(!Objects.equals(id, config.getId())){
            throw new IllegalArgumentException("Tempered request.");
        }
        return ResponseEntity.ok(this.configService.update(config));
    }

}
