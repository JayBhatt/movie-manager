package news.MovieManager.model;

import java.util.HashMap;
import java.util.Map;

public enum Status {

    ACTIVE(true),
    INACTIVE(false);

    private boolean status;

    Status(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

    private static final Map<Boolean, Status> reverseLookup = new HashMap<>();
    static
    {
        for(Status status : Status.values())
        {
            reverseLookup.put(status.getStatus(), status);
        }
    }

    public static Status get(boolean status) {
        return reverseLookup.get(status);
    }

}
