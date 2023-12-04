package com.example.gestionPrjt.Models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@DiscriminatorValue("MANAGER")

@Table(name = "Manager")
public class Manager extends User implements Serializable {
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Project> projects= new ArrayList<>();
}
