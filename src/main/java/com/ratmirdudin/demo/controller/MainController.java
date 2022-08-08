package com.ratmirdudin.demo.controller;

import com.ratmirdudin.demo.dto.UserDto;
import com.ratmirdudin.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam(defaultValue = "") String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInfoAboutUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateInfoAboutUserById(id, userDto);
        return ResponseEntity.ok("User with id: " + id + " successfully updated.");
    }
}
