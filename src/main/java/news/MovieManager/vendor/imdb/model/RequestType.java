package news.MovieManager.vendor.imdb.model;

import java.util.HashMap;
import java.util.Map;

public enum RequestType {

    NOW_PLAYING("/movie/now_playing"),
    MOVIE_DETAIL("/movie/{id}"),
    POPULAR("discover/movie");

    private String type;

    RequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    private static final Map<String, RequestType> reverseLookup = new HashMap<>();
    static
    {
        for(RequestType type : RequestType.values())
        {
            reverseLookup.put(type.getType(), type);
        }
    }

    public static RequestType get(String type) {
        return reverseLookup.get(type);
    }

}
