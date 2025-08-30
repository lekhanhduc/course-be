package vn.fpt.courseservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserCreationRequest {

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 6, message = "Password must greater than 6 character")
    private String password;
}
