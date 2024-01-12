package uz.kuvondikov.example.dto;

import lombok.Getter;
import lombok.Setter;
import uz.kuvondikov.example.enums.Gender;

@Setter
@Getter
public class CreateDTO {

    private String firstName;
    private String lastName;
    private Gender gender;
    private String username;
    private String phoneNumber;
    private String password;

    public CreateDTO() {
    }

    public CreateDTO(String firstName, String lastName, Gender gender, String username, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}