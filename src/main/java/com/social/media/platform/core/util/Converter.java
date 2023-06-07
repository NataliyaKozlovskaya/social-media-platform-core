package com.social.media.platform.core.util;

import com.social.media.platform.core.dto.UserDTO;
import com.social.media.platform.core.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    private final ModelMapper modelMapper;

    @Autowired
    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

}
