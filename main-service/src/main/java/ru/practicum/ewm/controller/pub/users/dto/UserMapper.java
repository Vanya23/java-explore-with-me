package ru.practicum.ewm.controller.pub.users.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.controller.pub.users.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserDto fromUserToUserDto(User user) {

        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public UserShortDto fromUserToUserShortDto(User user) {

        return new UserShortDto(user.getId(), user.getName());
    }

    public List<UserDto> fromListUserToListUserDto(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user :
                users) {
            userDtoList.add(fromUserToUserDto(user));
        }
        return userDtoList;
    }

    public User fromUserDtoToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }


}
