package okhttp.service.solution;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import okhttp.model.User;
import okhttp.service.solution.SolutionGithubService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SolutionGithubServiceTest {

    private static final String FOLLOWERS_1 = "[{ \"login\":\"user1\"},{\"login\":\"user2\"}]";
    private static final String FOLLOWERS_2 = "[{ \"login\":\"user3\"}]";
    private static final String FOLLOWERS_3 = "[{ \"login\":\"user4\"},{\"login\":\"user5\"},{\"login\":\"user6\"}]";

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void test_followers() throws Exception {
        server.enqueue(new MockResponse().setBody(FOLLOWERS_1));
        server.play();

        URL baseUrl = server.getUrl("");

        SolutionGithubService githubService = new SolutionGithubService(baseUrl.toString());
        List<User> followers = githubService.followers("jeremiemartinez");

        assertNotNull(followers);
        assertEquals(2, followers.size());
        assertEquals("user1", followers.get(0).login);
        assertEquals("user2", followers.get(1).login);
    }

    @Test
    public void test_followers_of_followers() throws Exception {
        server.enqueue(new MockResponse().setBody(FOLLOWERS_1));
        server.enqueue(new MockResponse().setBody(FOLLOWERS_2));
        server.enqueue(new MockResponse().setBody(FOLLOWERS_3));
        server.play();

        URL baseUrl = server.getUrl("");

        SolutionGithubService githubService = new SolutionGithubService(baseUrl.toString());
        Map<User, Integer> followersOfFollowers = githubService.followersOfFollowers("jeremiemartinez");

        assertNotNull(followersOfFollowers);
        assertEquals(2, followersOfFollowers.size());
        assertEquals(1, followersOfFollowers.get(new User("user1")).intValue());
        assertEquals(3, followersOfFollowers.get(new User("user2")).intValue());
    }

    @Test
    public void test_format_url() throws Exception {
        server.play();

        SolutionGithubService githubService = new SolutionGithubService("http://test.org");
        String url = githubService.formatUrl("jm");
        assertEquals("http://test.org/users/jm/followers", url);
    }

}