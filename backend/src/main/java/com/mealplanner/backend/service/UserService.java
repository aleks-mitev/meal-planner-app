package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.CreateUserDTO;
import com.mealplanner.backend.dto.UpdateUserDTO;
import com.mealplanner.backend.dto.UserResponseDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.UserMapper;
import com.mealplanner.backend.model.User;
import com.mealplanner.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO create(CreateUserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User user = userMapper.toEntity(dto);
        user.setRegistrationDate(LocalDate.now());
        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found for this id: " + id));
    }

    public UserResponseDTO getDTOById(String id) {
        return userMapper.toResponseDTO(getById(id));
    }

    public UserResponseDTO update(String id, UpdateUserDTO updatedUserData) {
        User existingUser = getById(id);

        if (!existingUser.getEmail().equals(updatedUserData.getEmail()) &&
            userRepository.existsByEmail(updatedUserData.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        userMapper.updateEntity(existingUser, updatedUserData);
        User saved = userRepository.save(existingUser);
        return userMapper.toResponseDTO(saved);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}

