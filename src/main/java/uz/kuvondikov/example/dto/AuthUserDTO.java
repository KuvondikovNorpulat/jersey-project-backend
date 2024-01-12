package uz.kuvondikov.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.kuvondikov.example.enums.Gender;

@Getter
@Setter
@Builder

public class AuthUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String username;
    private String phoneNumber;
    public AuthUserDTO() {
    }

    public AuthUserDTO(String firstName, String lastName, Gender gender, String username, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public AuthUserDTO(Long id, String firstName, String lastName, Gender gender, String username, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }
}
