package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.UpdateUserDTO;
import com.mealplanner.backend.dto.UserResponseDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.UserMapper;
import com.mealplanner.backend.model.User;
import com.mealplanner.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User create(User user) {return userRepository.insert(user);}

    public List<User> getAll() {return userRepository.findAll();}

    public User getById(String id) {return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found for this id: " + id));}

    public UserResponseDTO update(String id, UpdateUserDTO dto) {
        User user = getById(id);
        userMapper.updateEntity(user, dto);
        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    public void delete(String id) {userRepository.deleteById(id);}


}

