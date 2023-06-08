package com.example.springbootazuresqlcrudpagination.controller;

import com.example.springbootazuresqlcrudpagination.exception.ResourceNotFoundException;
import com.example.springbootazuresqlcrudpagination.exception.ServerError;
import com.example.springbootazuresqlcrudpagination.model.User;
import com.example.springbootazuresqlcrudpagination.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public Map<String, Object> getAllUsers(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "4") int size){
        try{
            List<User> users = new ArrayList<User>();
            Pageable pagination = PageRequest.of(page, size);
            Page<User> userPage;
            if(country == null){
                userPage = userRepository.findAll(pagination);
            }else{
                userPage = userRepository.findByCountryContaining(country, pagination);
            }
            users = userPage.getContent();
            Map<String, Object> respons = new HashMap<String, Object>();
            respons.put("users", users);
            respons.put("totalPages", userPage.getTotalPages());
            return respons;
        }catch(Exception e){
            throw new ServerError(e.getMessage());
        }
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable("id") Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        return user;
    }

    @PutMapping("user/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user){
        User userDetails = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userDetails.setName(user.getName());
        userDetails.setCountry(user.getCountry());
        userDetails.setEmail(user.getEmail());

        return userRepository.save(userDetails);
    }

    @DeleteMapping("users/{id}")
    public Boolean deleteUser(@PathVariable("id") Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userRepository.delete(user);
        return true;
    }


}
