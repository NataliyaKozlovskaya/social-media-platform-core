package com.social.media.platform.core.controllers;

import com.social.media.platform.core.dto.UserAuthDTO;
import com.social.media.platform.core.dto.UserDTO;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.services.UserService;
import com.social.media.platform.core.util.Converter;
import com.social.media.platform.core.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.social.media.platform.core.security.JWTUtil;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final Converter converter;//todo
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserValidator userValidator, UserService userService,
                          JWTUtil jwtUtil, AuthenticationManager authenticationManager,
                          Converter converter) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.converter = converter;
    }

    // регистрация, валидация и генерирование jwt-токена
    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                                   BindingResult bindingResult){
        User user = converter.convertToUser(userDTO);
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors())
            return Map.of("message", "Ошибка!");
        userService.register(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt_token", token);
    }

    // принимаем логин и пароль и выдаем новый jwt-токен с новым сроком годности
    @PostMapping("/login")
    public Map<String, String> performLogin (@RequestBody UserAuthDTO userAuthDTO){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(
                        userAuthDTO.getUsername(), userAuthDTO.getPassword());
        try{
            authenticationManager.authenticate(authInputToken);
        }catch (BadCredentialsException e){
            return Map.of("message", "Incorrect credentials");
        }
        String token = jwtUtil.generateToken(userAuthDTO.getUsername());
        return Map.of("jwt_token", token);
    }


//    @PostMapping("/registration")
//    public ResponseEntity<?> create(@RequestBody @Valid UserAuthDTO userAuthDTO,
//                                    BindingResult bindingResult){
//        userValidator.validate(userAuthDTO, bindingResult);
//        if(bindingResult.hasErrors()){
//            return new ResponseEntity<>(bindingResult.toString(),
//                    HttpStatus.CONFLICT);
//        }
//        User user = userService.register(converter.convertToUser(userAuthDTO));
//        UserAuthDTO registeredUser = converter.convertToUserAuthDTO(user);
//        return ResponseEntity.ok(registeredUser);
//    }
}

