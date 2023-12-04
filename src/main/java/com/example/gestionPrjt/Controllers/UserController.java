package com.example.gestionPrjt.Controllers;

import com.example.gestionPrjt.Dto.UserDto;
import com.example.gestionPrjt.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/User")
public class UserController {

    private final UserService userService;

    @GetMapping("/allUsers")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<UserDto> FindUser(@PathVariable("id") Long id) {
        Optional<UserDto> userDto = userService.findUserById(id);
        if (userDto.isPresent()) {
            return new ResponseEntity<>(userDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//ADMIN token : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUb3VtaUBnbWFpbC5jb20iLCJpYXQiOjE3MDEzOTAwOTAsImV4cCI6MTcwMTM5MTUzMH0.CIY3scC9Sb09QM2aBsrQ8RGqwbvYJdPJcyn8sk2-QDA
    @PostMapping("/addUser")
    //@PreAuthorize("hasRole('ADMIN')")

    //User doit être désérialisé (Conversion d'un objet en Json ou xml en objet Java )
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto, HttpServletResponse response) {
        UserDto newUser = userService.addUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<UserDto> UpdateUser(@RequestBody UserDto userDto, HttpServletResponse response) {
        UserDto updateUser = userService.UpdateUser(userDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id) {
        if (!userService.findUserById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            userService.DeleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
