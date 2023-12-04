package com.example.gestionPrjt.Services;

import com.example.gestionPrjt.Dto.UserDto;
import com.example.gestionPrjt.Exception.ErrorCode;
import com.example.gestionPrjt.Models.User;
import com.example.gestionPrjt.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper; // to convert class User to UserDto

    public UserDto addUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    //converting an optional<User> to optional<UserDto> is not directly supported by ModelMapper.
    public Optional<UserDto> findUserById(Long id) {
        if (id==0 ){log.error("l'id est null");}
        Optional<User> user = userRepo.findById(id);
        return Optional.ofNullable(user.map(u -> modelMapper.map(u, UserDto.class)).orElseThrow(() -> new EntityNotFoundException("user " + id + "not found"+ErrorCode.USER_NOT_FOUND)));
    }

    public List<UserDto> findAllUser(){
        List<User> users =userRepo.findAll();
        return users.stream()
                .map(u-> modelMapper.map(u,UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto UpdateUser(UserDto userDto){
        User user =modelMapper.map(userDto,User.class);
        user.setNom(user.getNom());
        user.setPrenom(user.getPrenom());
        user.setEmail(user.getEmail());
        user.setRole(user.getRole());
        User updatedUser= userRepo.save(user);
        return modelMapper.map(updatedUser,UserDto.class);
    }

    public void DeleteById(Long id){
        if (id.equals(0) )log.error("UserId is null");
        else
        userRepo.deleteById(id);
    }

}

