package vn.fpt.courseservice.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class VerifyTokenRequest {
    private String accessToken;
}
