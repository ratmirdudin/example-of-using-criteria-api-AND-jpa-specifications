package com.ratmirdudin.demo.services;

import com.ratmirdudin.demo.dto.UserDto;
import com.ratmirdudin.demo.entities.Role;
import com.ratmirdudin.demo.entities.User;
import com.ratmirdudin.demo.exceptions.UserNotFoundException;
import com.ratmirdudin.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private static Specification<User> hasRole(String role) {
        return (user, cq, cb) -> cb.equal(user.get("role"), role);
    }

    private static Specification<User> firstNameContains(String firstName) {
        return (user, cq, cb) -> cb.like(cb.lower(user.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    private static Specification<User> lastNameContains(String lastName) {
        return (user, cq, cb) -> cb.like(cb.lower(user.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public List<UserDto> searchUsers(String query) {
        log.info("Searching users by query: {}", query.isBlank() ? "__BLANK__" : query);
        return userRepository.findAll(where(firstNameContains(query)).or(lastNameContains(query)))
                .stream()
                .map(this::mapUserModelToDto)
                .collect(Collectors.toList());
    }

    public void updateInfoAboutUserById(Long id, UserDto userDto) {
        log.info("Updating info by id: {}", id);
        if (userRepository.updateInfoAboutUser(id, userDto.getFirstName(), userDto.getLastName()) == 0) {
            throw new UserNotFoundException("User with id: " + id + " not found during updating info.");
        }
    }

    private UserDto mapUserModelToDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getId())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }

}
