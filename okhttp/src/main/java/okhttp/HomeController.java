package okhttp;

import okhttp.service.GithubService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


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
