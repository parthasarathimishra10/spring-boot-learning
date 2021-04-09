package com.partha.springboot.dao;

import com.partha.springboot.bean.User;
import com.partha.springboot.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDao {
    private static List<User> userList = new ArrayList<>();
    static {
        userList.add(new User(1,"Partha", new Date()));
        userList.add(new User(2,"Sarathi", new Date()));
        userList.add(new User(3,"Mishra", new Date()));
    }

    private static int count = 3;

    public List<User> getUsers(){
        return userList;
    }

    public User getUserById(Integer id){
        return userList.stream().filter(user ->
            user.getId().equals(id)
        ).findAny().orElse(null);

    }

    public User addUser(User user){
        if(user.getId() == null){
            count++;
            user.setId(count);
        }

        userList.add(user);
        return user;
    }

    public void removeUser(Integer id){
        userList.removeIf(user -> user.getId().equals(id));
    }
}
