package uz.kuvondikov.example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String password;

    public UpdateDTO() {

    }

    public UpdateDTO(String firstName, String lastName, String username, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
