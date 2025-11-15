package com.example.reqresdemo.controller;
import com.example.reqresdemo.model.User;
import com.example.reqresdemo.model.UserCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Map<Integer, User> userDb = new HashMap<>();
    @GetMapping
    public List<User> listUsers() {
        return new ArrayList<>(userDb.values());
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {
        int id = userDb.size() + 1;
        User user = new User();
        user.setId(id);
        user.setEmail(request.getUsername() + "@example.com");
        user.setName(request.getUsername());
        user.setAvatar("https://example.com/avatar.png");
        userDb.put(id, user);
        return ResponseEntity.ok("User Created"); // Divergence: should return 201
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            int userId = Integer.parseInt(id);
            User user = userDb.get(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok(user);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format"); // Divergence: not defined in spec
        }


    }




}
