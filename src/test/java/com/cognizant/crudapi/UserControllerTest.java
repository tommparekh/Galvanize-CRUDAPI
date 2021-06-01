package com.cognizant.crudapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void getUserTest() throws Exception {

        User user1 = new User();
        user1.setId(1);
        user1.setEmail("john@example.com");
        user1.setPassword("user1pwd");

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("eliza@example.com");
        user2.setPassword("user2pwd");

        List<User> userList = Arrays.asList(user1, user2);
        userRepository.saveAll(userList);


        RequestBuilder requestBuilder = get("/users")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"email\":\"john@example.com\"},{\"id\":2,\"email\":\"eliza@example.com\"}]"));
    }


    @Test
    @Transactional
    @Rollback
    public void postUserTest() throws Exception {


        User user1 = new User();
        user1.setId(1);
        user1.setEmail("john@example.com");
        user1.setPassword("user1pwd");

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("eliza@example.com");
        user2.setPassword("user2pwd");

        List<User> userList = Arrays.asList(user1, user2);
        userRepository.saveAll(userList);

        String requestBody = "{\n" +
                "    \"email\": \"Jimmy@example.com\",\n" +
                "    \"password\": \"something-secret\"\n" +
                "  }";

        RequestBuilder requestBuilder = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":3,\"email\":\"Jimmy@example.com\"}"));
    }
}
