package com.example.PayPlatform.service.impl;

import com.example.PayPlatform.model.User;
import com.example.PayPlatform.model.dto.UserDto;
import com.example.PayPlatform.repository.UserRepository;
import com.example.PayPlatform.service.UserService;
import com.example.PayPlatform.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.fromDto(userDto);
        User userSaved= userRepository.save(user);
        return userMapper.toDto(userSaved);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> usersList= userRepository.findAll();
        List<UserDto> usersDtoList =usersList.stream()
                .map(user -> userMapper.toDto(user))
                .toList();
        return usersDtoList;
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> user= userRepository.findById(id);
        Optional<UserDto> userDto= user.map(userMapper::toDto);
        return userDto;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
