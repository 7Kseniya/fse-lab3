package se.ifmo.lab4.resource;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import se.ifmo.lab4.model.Response;
import se.ifmo.lab4.model.User;
import se.ifmo.lab4.service.JwtService;
import se.ifmo.lab4.service.implementation.UserServiceImplementation;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserServiceImplementation userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody User user) throws IOException {
        if(userService.register(user)){
            return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user", true))
                        .message("user registred")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .jwt(jwtService.generateToken(user))
                        .build()

                );
        } else {
            return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user", false))
                        .message("username taken")
                        .status(HttpStatus.CONFLICT)
                        .statusCode(HttpStatus.CONFLICT.value())
                        .build()

                );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody User user){
        if(userService.authenticate(user)){
            return ResponseEntity.ok(
            Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .data(Map.of("logged in", true))
                    .message("user logged in")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .jwt(jwtService.generateToken(user))
                    .build()
            );
        } else {
            return ResponseEntity.ok(
            Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .data(Map.of("logged in", false))
                    .message("incorrect password or login")
                    .status(HttpStatus.UNAUTHORIZED)
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build()
        );
        }

    }
}
