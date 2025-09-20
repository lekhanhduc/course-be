package vn.fpt.courseservice.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

}
