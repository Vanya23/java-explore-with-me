package ru.practicum.ewm.controller.pub.users.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.users.UserService;
import ru.practicum.ewm.controller.pub.users.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminUserController {

    UserService service;


    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(required = false, defaultValue = "0") int from,
                                  @RequestParam(required = false, defaultValue = "10") int size) {

        return service.getUsers(ids, from, size);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public UserDto addUser(@Validated({Create.class}) @RequestBody UserDto userDto) {
        return service.addUser(userDto);
    }


    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteUser(@PathVariable long userId) {
        service.deleteUser(userId);

    }


}
