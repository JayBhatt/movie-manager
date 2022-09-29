package news.MovieManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import news.MovieManager.persistence.entity.Config;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ConfigDto {

    @NotBlank(message = "Missing required parameter \"Key\".")
    private String key;

    @NotBlank(message = "Missing required parameter \"Value\".")
    private String value;

    public Config toConfig() {
        Config config = new Config();
        config.setKey(this.getKey());
        config.setValue(this.getValue());
        return config;
    }

}
