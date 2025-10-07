package com.example.PayPlatform.service.mapper;

import com.example.PayPlatform.model.User;
import com.example.PayPlatform.model.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromDto (UserDto userDto){
        User user=new User();
        user.setName(userDto.getName());
        user.setIban(userDto.getIban());
        user.setBalance(userDto.getBalance());
        return user;
    }

    public UserDto toDto (User user) {
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setIban(user.getIban());
        userDto.setBalance(user.getBalance());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}
