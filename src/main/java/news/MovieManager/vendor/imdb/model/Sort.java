package news.MovieManager.vendor.imdb.model;

import java.util.HashMap;
import java.util.Map;

public enum Sort {

    POPULAR_ASC("popularity.asc"),
    POPULAR_DESC("popularity.desc");

    private String sort;

    Sort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return this.sort;
    }

    private static final Map<String, Sort> reverseLookup = new HashMap<>();
    static
    {
        for(Sort sort : Sort.values())
        {
            reverseLookup.put(sort.getSort(), sort);
        }
    }

    public static Sort get(String sort) {
        return reverseLookup.get(sort);
    }

}
