package com.social.media.platform.core.util;

import com.social.media.platform.core.dto.UserAuthDTO;
import com.social.media.platform.core.dto.UserDTO;
import com.social.media.platform.core.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    private final ModelMapper modelMapper;

    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(UserAuthDTO userAuthDTO){
        return modelMapper.map(userAuthDTO, User.class);
    }
    public UserAuthDTO convertToUserAuthDTO(User user){
        return modelMapper.map(user, UserAuthDTO.class);
    }

    public User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
    public UserDTO convertToUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }


//    public User convertToUser(@Valid UserAuthDTO userDTO){
//        return this.modelMapper.map(userDTO, User.class);
//    }
}
