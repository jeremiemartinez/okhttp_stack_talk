package okhttp.service.solution;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import okhttp.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionGithubService {

    private final String urlServer;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public SolutionGithubService(String urlServer) {
        this.urlServer = urlServer;
        client = new OkHttpClient();
        mapper = new ObjectMapper();
    }

    public Map<User, Integer> followersOfFollowers(String username) throws IOException {
        List<User> users = followers(username);

        Map<User, Integer> followersOfFollowers = new HashMap<>();
        for (User user : users) {
            followersOfFollowers.put(user, followers(user.login).size());
        }
        return followersOfFollowers;
    }

    List<User> followers(String username) throws IOException {
        Request request = new Request.Builder()
                .url(formatUrl(username))
                .get()
                .build();

        Response response = client.newCall(request).execute(); // enqueue for asynchrone
        if (response.isSuccessful()) {
            return mapper.readValue(response.body().string(), new TypeReference<List<User>>() {});
        } else {
            throw new IllegalArgumentException("User " + username + " cannot be found");
        }
    }

    String formatUrl(String username) {
        return new StringBuilder(urlServer)
                .append("/users/")
                .append(username)
                .append("/followers")
                .toString();
    }
}
