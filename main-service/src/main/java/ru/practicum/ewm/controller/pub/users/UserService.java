package ru.practicum.ewm.controller.pub.users;


import ru.practicum.ewm.controller.pub.users.dto.UserDto;

import java.util.List;

public interface UserService {


    UserDto addUser(UserDto userDto);

    void deleteUser(long userId);


    List<UserDto> getUsers(List<Long> ids, int from, int size);
}
