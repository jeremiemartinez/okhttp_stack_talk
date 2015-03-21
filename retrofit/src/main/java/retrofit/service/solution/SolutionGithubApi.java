package retrofit.service.solution;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.model.User;

import java.util.List;

public interface SolutionGithubApi {

    @GET("/users/{username}/followers")
    List<User> followers(@Path("username") String username);

}
