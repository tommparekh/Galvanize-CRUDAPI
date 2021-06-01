package com.cognizant.crudapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserResponse> getUsers() {

        populateData();

        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);

        List<UserResponse> userRespList = new ArrayList<>();
        for(User user : userList) {
            userRespList.add(convertToUserResponse(user));
        }
        return userRespList;
    }

    @PostMapping("/users")
    public UserResponse postUsers(@RequestBody User user) {
        populateData();
        User userSaved = userRepository.save(user);
        return convertToUserResponse(userSaved);
    }

    private UserResponse convertToUserResponse(User userSaved) {
        UserResponse response = new UserResponse();
        response.setId(userSaved.getId());
        response.setEmail(userSaved.getEmail());

        return response;

    }

    private void populateData() {
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
    }


}
