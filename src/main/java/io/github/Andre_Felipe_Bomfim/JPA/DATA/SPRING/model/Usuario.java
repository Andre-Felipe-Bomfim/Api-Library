package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model;


import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String login;
    @Column
    private String senha;
    @Type(ListArrayType.class)//faz a tradução de List para Array
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;//no banco de dados vira um array
}
