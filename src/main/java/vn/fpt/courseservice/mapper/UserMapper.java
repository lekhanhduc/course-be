package vn.fpt.courseservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.fpt.courseservice.dto.request.UserCreationRequest;
import vn.fpt.courseservice.dto.response.UserCreationResponse;
import vn.fpt.courseservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toUser(UserCreationRequest request);

    UserCreationResponse toUserResponse(User user);
}
