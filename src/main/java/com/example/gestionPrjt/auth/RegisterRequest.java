package com.example.gestionPrjt.auth;

import com.example.gestionPrjt.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private  String nom ;
    private  String prenom ;
    private String email;
    private  String password;
    private Role role;
}
