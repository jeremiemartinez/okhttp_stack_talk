package okhttp.service;

import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        server.play();
        // TODO
    }

    @Test
    public void test_followers_of_followers() throws Exception {
        server.play();
        // TODO
    }

    @Test
    public void test_format_url() throws Exception {
        server.play();
        GithubService githubService = new GithubService("http://test.org");
        String url = githubService.formatUrl("jm");
        assertEquals("http://test.org/users/jm/followers", url);
    }
}