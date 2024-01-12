package com.poc.yourcaryourwarback.models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;

@Entity
@Table(name = "USERS")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Size(max = 40)
    @Column(name = "username")
    private String username;

    @NonNull
    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @NonNull
    @Size(max = 255)
    private String password;
}
