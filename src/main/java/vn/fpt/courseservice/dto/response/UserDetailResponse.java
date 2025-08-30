package vn.fpt.courseservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDetailResponse {
    private Long id;
    private String username;
    private String email;
}
