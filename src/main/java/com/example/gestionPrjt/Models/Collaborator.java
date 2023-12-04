package com.example.gestionPrjt.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "Collaborator")
@PrimaryKeyJoinColumn(name = "id")
public class Collaborator extends User{

    @OneToMany(mappedBy = "collaborator", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
}

