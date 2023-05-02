package ru.practicum.ewm.controller.pub.users;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.controller.pub.GeneratePageableObj;
import ru.practicum.ewm.controller.pub.error.exception.BadRequestException;
import ru.practicum.ewm.controller.pub.error.exception.ConflictException;
import ru.practicum.ewm.controller.pub.users.dto.UserDto;
import ru.practicum.ewm.controller.pub.users.dto.UserMapper;
import ru.practicum.ewm.controller.pub.users.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository repository;
    UserMapper userMapper;
    GeneratePageableObj myServicePage;

    Sort sortIdAsc = Sort.by(Sort.Direction.ASC, "id"); // по возрастанию


    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        // по условию задачи проверка уникальности почты выполняется в БД
        if (Strings.isBlank(userDto.getEmail()) || Strings.isBlank(userDto.getName()))
            throw new BadRequestException("");
        try {
            User user = repository.save(userMapper.fromUserDtoToUser(userDto));
            return userMapper.fromUserToUserDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("" + e.getClass());
        }


    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        repository.deleteById(userId);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {

        Pageable pageable = myServicePage.checkAndCreatePageable(from, size, sortIdAsc);

        Page<User> page;
        if (ids == null || ids.size() == 0) page = repository.findAll(pageable);
        else page = repository.findByIdIn(ids, pageable);


        List<User> users = page.getContent();

        return userMapper.fromListUserToListUserDto(users);
    }


}
