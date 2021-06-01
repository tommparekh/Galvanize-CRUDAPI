package com.cognizant.crudapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
        for (User user : userList) {
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


    @PostMapping("/users/authenticate")
    public UserAuthenticationResponse postUsersAuthentication(@RequestBody User user) {
        populateData();
        User userSaved = userRepository.findByEmail(user.getEmail());
        UserAuthenticationResponse userAuthenticationResponse = new UserAuthenticationResponse();
        userAuthenticationResponse.setAuthenticated(false);
        if (userSaved == null) {
            return userAuthenticationResponse;
        } else {
            if (userSaved.getPassword().equals(user.getPassword())) {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(userSaved.getId());
                userResponse.setEmail(user.getEmail());
                userAuthenticationResponse.setUserResponse(userResponse);
                userAuthenticationResponse.setAuthenticated(true);
            }
            return userAuthenticationResponse;
        }
    }


    @GetMapping("/users/{id}")
    public UserResponse getUsersById(@PathVariable int id) {
        populateData();
        User userSaved = userRepository.findById(id);
        return convertToUserResponse(userSaved);
    }

    @PatchMapping("/users/{id}")
    public UserResponse patchUserById(@PathVariable int id, @RequestBody User user) {
        populateData();
        User userSaved = userRepository.findById(id);
        userSaved.setEmail(user.getEmail() == null ? userSaved.getEmail() : user.getEmail());
        userSaved.setPassword(user.getPassword() == null ? userSaved.getPassword() : user.getPassword());
        return convertToUserResponse(userSaved);
    }

    @DeleteMapping("/users/{id}")
    public UserDeleteResponse deleteUserById(@PathVariable int id) {
        populateData();
        userRepository.deleteById(id);

        Collection<User> userCollection = (Collection<User>) userRepository.findAll();

        UserDeleteResponse userDeleteResponse = new UserDeleteResponse();
        userDeleteResponse.setCount(userCollection.size());
        return userDeleteResponse;
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
