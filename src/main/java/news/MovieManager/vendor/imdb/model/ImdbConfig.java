package news.MovieManager.vendor.imdb.model;

import lombok.Data;

@Data
public class ImdbConfig {

    private String baseUrl;

    private String key;

    private String maxRetryCount;

    private String maxRetryDelay;

    private String region;

    public void setProperty(String property, String value) {
        switch (property.toLowerCase()) {
            case "baseurl":
                this.setBaseUrl(value);
                break;
            case "key":
                this.setKey(value);
                break;
            case "maxretrycount":
                this.setMaxRetryCount(value);
                break;
            case "maxretrydelay":
                this.setMaxRetryDelay(value);
                break;
            case "region":
                this.setRegion(value);
                break;
            default:
                break;
        }
    }

}
