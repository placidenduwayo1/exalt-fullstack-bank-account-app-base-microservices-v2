package fr.exalt.businessmicroservicespringsecurity.entities.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long userId;
    @Column(unique = true)
    private String username;
    private String pwd;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(name = "created_date")
    private String createdAt;
    @ManyToMany @JoinTable(
            name = "users_roles_association_table",
            joinColumns = {@JoinColumn(name = "user_Id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();
}
