package com.example.PayPlatform.service;

import com.example.PayPlatform.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public UserDto save (UserDto userDto);

    public List<UserDto> findAll ();

    public Optional<UserDto> findById (Long id);

    public void delete (Long id);
}
