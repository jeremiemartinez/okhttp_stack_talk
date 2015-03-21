package retrofit.service.solution;

import org.junit.Before;
import org.junit.Test;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.model.User;
import retrofit.service.solution.SolutionGithubApi;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SolutionGithubApiTest {

    private SolutionGithubApi githubApi;

    @Before
    public void setUp() throws Exception {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("localhost")
                .build();

        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
        mockRestAdapter.setDelay(1000);
        mockRestAdapter.setErrorPercentage(0);

        githubApi = mockRestAdapter.create(SolutionGithubApi.class, new MockSolutionGithubApi());
    }

    @Test
    public void test_followers() throws Exception {
        List<User> followers = githubApi.followers("username");

        assertEquals(3, followers.size());
        assertEquals("user1", followers.get(0).login);
        assertEquals("user2", followers.get(1).login);
        assertEquals("user3", followers.get(2).login);
    }

    private static class MockSolutionGithubApi implements SolutionGithubApi {

        @Override
        public List<User> followers(String username) {
            return Arrays.asList(new User("user1"), new User("user2"), new User("user3"));
        }

    }
}