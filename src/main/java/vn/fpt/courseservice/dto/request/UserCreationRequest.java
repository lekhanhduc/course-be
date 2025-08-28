package vn.fpt.courseservice.dto.request;

import lombok.Getter;

@Getter
public class UserCreationRequest {
    private String email;
    private String password;
}
