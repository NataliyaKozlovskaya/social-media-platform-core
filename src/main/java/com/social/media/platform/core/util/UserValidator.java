package com.social.media.platform.core.util;

import com.social.media.platform.core.dto.UserAuthDTO;
import com.social.media.platform.core.models.User;
import com.social.media.platform.core.services.PersonDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final PersonDetailsService personDetailsService;
    public UserValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;
        try{
            personDetailsService.loadUserByUsername(user.getUsername());
        }catch (UsernameNotFoundException ignored){
            return;
        }
        errors.reject("", "Человек с таким именем уже существует");
//        errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}




