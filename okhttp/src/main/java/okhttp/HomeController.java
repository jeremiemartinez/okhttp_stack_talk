package okhttp;

import okhttp.model.User;
import okhttp.service.GithubService;
import okhttp.service.solution.SolutionGithubService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
@EnableAutoConfiguration
public class HomeController {

    private static final String URL_SERVER = "https://api.github.com";

    private final GithubService githubService;

    public HomeController() {
        githubService = new GithubService(URL_SERVER);
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "XKE OkHttp";
    }

    @RequestMapping("/followers/{username}")
    @ResponseBody
    String followers(@PathVariable("username") String username) throws IOException {
        return githubService.followersOfFollowers(username).toString();
    }
}
