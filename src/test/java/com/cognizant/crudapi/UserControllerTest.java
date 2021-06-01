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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        RequestBuilder requestBuilder = get("/users")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"email\":\"john@example.com\"},{\"id\":2,\"email\":\"eliza@example.com\"}]"));
    }


    @Test
    @Transactional
    @Rollback
    public void postUserTest() throws Exception {

        String requestBody = "{\n" +
                "    \"email\": \"Jimmy@example.com\",\n" +
                "    \"password\": \"something-secret\"\n" +
                "  }";

        RequestBuilder requestBuilder = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string("{\"id\":3,\"email\":\"Jimmy@example.com\"}"));
    }

    @Test
    @Transactional
    @Rollback
    public void getUserByIdTest() throws Exception {
        RequestBuilder requestBuilder = get("/users/2")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"email\":\"eliza@example.com\"}"));
    }

    @Test
    @Transactional
    @Rollback
    public void patchUserByIdTest() throws Exception {
        String requestBody = "{\n" +
                "  \"email\": \"bob@example.com\"\n" +
                "}";

        RequestBuilder requestBuilder = patch("/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"email\":\"bob@example.com\"}"));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUserByIdTest() throws Exception {
        RequestBuilder requestBuilder = delete("/users/2")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string("{\"count\":1" + "}"));
    }

    @Test
    @Transactional
    @Rollback
    public void postUserAuthenticateTest() throws Exception {

        String requestBody = "{\n" +
                "  \"email\": \"john@example.com\",\n" +
                "  \"password\": \"user1pwd\"\n" +
                "}";

        RequestBuilder requestBuilder = post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        String expectedResponse = "{" +
                "\"authenticated\": true," +
                "\"user\": {" +
                "\"id\": 12," +
                "\"email\": \"angelica@example.com\"" +
                "}" +
                "}";
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    @Transactional
    @Rollback
    public void postUserAuthenticateFailTest() throws Exception {

        String requestBody = "{\n" +
                "  \"email\": \"john@example.com\",\n" +
                "  \"password\": \"user2pwd\"\n" +
                "}";

        RequestBuilder requestBuilder = post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        String expectedResponse = "{" +
                "\"authenticated\":false" + "}";
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

}
