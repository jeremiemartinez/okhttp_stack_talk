package okhttp.service;

import okhttp.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GithubService {

    private final String urlServer;

    public GithubService(String urlServer) {
        this.urlServer = urlServer;
        // TODO

    }

    public Map<User, Integer> followersOfFollowers(String username) throws IOException {
        return null; // TODO
    }

    List<User> followers(String username) throws IOException {
        return null; // TODO
    }

    String formatUrl(String username) {
        return new StringBuilder(urlServer)
                .append("/users/")
                .append(username)
                .append("/followers")
                .toString();
    }
}
