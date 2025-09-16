package vn.fpt.courseservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class VerifyTokenResponse {
    private boolean isValid;
    private List<String> roles;
}
