package news.MovieManager.model;

import java.util.HashMap;
import java.util.Map;

public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    private static final Map<String, Role> reverseLookup = new HashMap<>();
    static
    {
        for(Role role : Role.values())
        {
            reverseLookup.put(role.getRole(), role);
        }
    }

    public static Role get(String role) {
        return reverseLookup.get(role);
    }

}
