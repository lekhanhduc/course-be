package vn.fpt.courseservice.exception;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String message;
    private String path;
}
