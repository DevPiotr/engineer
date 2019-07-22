package com.codepolishing.engineer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "material_type")
@Data
public class MaterialType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material_type")
    private int id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(name = "id_material_type")
    private List<Material> materialList;
}
