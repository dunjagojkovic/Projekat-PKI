package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String type; //rola


    public String getRole() {
        return type.toString();
    }

}