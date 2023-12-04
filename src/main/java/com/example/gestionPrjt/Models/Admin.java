package com.example.gestionPrjt.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends  User{
}
