package vn.fpt.courseservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.dto.request.UserCreationRequest;
import vn.fpt.courseservice.dto.request.UserUpdateRequest;
import vn.fpt.courseservice.dto.response.PageResponse;
import vn.fpt.courseservice.dto.response.UserCreationResponse;
import vn.fpt.courseservice.dto.response.UserDetailResponse;
import vn.fpt.courseservice.model.User;
import vn.fpt.courseservice.repository.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserCreationResponse createUser(UserCreationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return UserCreationResponse.builder()
                .email(request.getEmail())
                .build();
    }

    public PageResponse<UserDetailResponse> getUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<User> users = userRepository.findAll(pageable);

        List<User> userList = users.getContent();

        return PageResponse.<UserDetailResponse>builder()
                .currentPages(page)
                .sizes(pageable.getPageSize())
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .data(userList.stream().map(user -> UserDetailResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()).toList())
                .build();
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.deleteById(user.getId());
    }

    public UserDetailResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(request.getUsername() != null) {
            user.setUsername(request.getUsername());
            userRepository.save(user);
        }

        return UserDetailResponse.builder()
                .id(id)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

}
