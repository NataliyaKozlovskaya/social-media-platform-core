package com.social.media.platform.core.controllers;

import com.social.media.platform.core.dto.UserAuthDTO;
import com.social.media.platform.core.dto.UserDTO;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.security.JWTUtil;
import com.social.media.platform.core.services.FriendshipService;
import com.social.media.platform.core.services.UserService;
import com.social.media.platform.core.util.Converter;
import com.social.media.platform.core.util.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Tag(name = "User controller", description = "Allows to register , login users, make a request for correspondence")
@RestController
@RequestMapping("/user")
public class UserController {
    private final FriendshipService friendshipService;
    private final UserService userService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final Converter converter;

    @Autowired
    public UserController(FriendshipService friendshipService, UserService userService,
                          UserValidator userValidator, JWTUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          Converter converter) {
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.converter = converter;
    }

    @Operation(summary = "User registration", description = "User creation.")
    @ApiResponses(value={@ApiResponse(responseCode = "200", description = "User successfully created")})
    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                                   BindingResult bindingResult){
        User user = converter.convertToUser(userDTO);
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors())
            return Map.of("message", "Error!");
        userService.register(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt_token", token);
    }

    @Operation(summary = "User authentication and authorization.",
            description = "This can only be done by the logged in user.")
    @ApiResponse(responseCode = "200", description = "You have successfully logged in")
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


    @Operation(summary = "Request to chat with another user",
            description = "User can send a request to chat to another user, if they are not friends.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Send a request"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping("/correspondence/userFrom/{fromUserId}/userTo/{toUserId}")
    public void askCorrespondence(@PathVariable("fromUserId") Integer fromUserId,
                                  @PathVariable("toUserId") Integer toUserId) {
        friendshipService.askCorrespondence(fromUserId, toUserId);
    }

    @Operation(summary = "A response to a request for correspondence",
            description = "If the answer is positive, you can start chatting.")
    @PatchMapping("/correspondence/{correspondenceId1}/{correspondenceId2}/{approval}")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "OK!"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public void ansCorrespondence(@PathVariable("correspondenceId1") Integer correspondenceId1,
                                  @PathVariable("correspondenceId2") Integer correspondenceId2,
                                  @PathVariable("approval") Boolean approval) {
        friendshipService.ansCorrespondence(correspondenceId1, correspondenceId2, approval);
    }

}





