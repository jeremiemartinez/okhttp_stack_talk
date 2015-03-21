package retrofit.service.solution;

import retrofit.RestAdapter;
import retrofit.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionGithubService {

    private final SolutionGithubApi githubApi;

    public SolutionGithubService(String urlServer) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(urlServer)
                .build();

        githubApi = restAdapter.create(SolutionGithubApi.class);
    }

    public Map<User, Integer> followersOfFollowers(String username) {
        List<User> users = followers(username);

        Map<User, Integer> followersOfFollowers = new HashMap<>();
        for (User user : users) {
            followersOfFollowers.put(user, followers(user.login).size());
        }
        return followersOfFollowers;
    }

    List<User> followers(String username)  {
        return githubApi.followers(username);
    }
}
