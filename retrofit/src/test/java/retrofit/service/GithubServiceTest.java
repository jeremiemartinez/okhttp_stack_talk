package retrofit.service;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit.model.User;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GithubServiceTest {

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

        GithubService githubService = new GithubService(baseUrl.toString());
        List<User> followers = githubService.followers("jeremiemartinez");

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

        GithubService githubService = new GithubService(baseUrl.toString());
        Map<User, Integer> followersOfFollowers = githubService.followersOfFollowers("jeremiemartinez");

        assertEquals(2, followersOfFollowers.size());
        assertEquals(1, followersOfFollowers.get(new User("user1")).intValue());
        assertEquals(3, followersOfFollowers.get(new User("user2")).intValue());
    }

}