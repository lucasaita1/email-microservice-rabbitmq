package dev.lucas.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "TB_USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID userId;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

}
