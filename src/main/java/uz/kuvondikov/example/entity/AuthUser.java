package uz.kuvondikov.example.entity;

import lombok.Getter;
import lombok.Setter;
import uz.kuvondikov.example.enums.Gender;

@Getter
@Setter
public class AuthUser {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String username;
    private String phoneNumber;
    private String password;

    public AuthUser() {
    }

    public AuthUser(String firstName, String lastName, Gender gender, String username, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public AuthUser(Long id, String firstName, String lastName, Gender gender, String username, String phoneNumber, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
