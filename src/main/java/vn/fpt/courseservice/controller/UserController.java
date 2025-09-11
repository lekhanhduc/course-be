package vn.fpt.courseservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.courseservice.dto.request.UserCreationRequest;
import vn.fpt.courseservice.dto.request.UserUpdateRequest;
import vn.fpt.courseservice.dto.response.PageResponse;
import vn.fpt.courseservice.dto.response.UserCreationResponse;
import vn.fpt.courseservice.dto.response.UserDetailResponse;
import vn.fpt.courseservice.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    ResponseEntity<UserCreationResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @GetMapping("/users")
    ResponseEntity<PageResponse<UserDetailResponse>> getUsers(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "2") int size) {
            return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @DeleteMapping("/users/{id}")
    void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/users/{id}")
    UserDetailResponse updateUserById(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

}
