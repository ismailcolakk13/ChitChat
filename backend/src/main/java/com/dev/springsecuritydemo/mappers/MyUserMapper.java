package com.dev.springsecuritydemo.mappers;
import com.dev.springsecuritydemo.entities.MyUser;
import com.dev.springsecuritydemo.dto.MyUserDTO;

import java.util.List;

public class MyUserMapper {
    public static MyUserDTO toDTO(MyUser user) {
        return new MyUserDTO(user.getId(),user.getUsername(), user.getAge(),user.getRole());
    }

    public static List<MyUserDTO> toDTOList(List<MyUser> users) {
        return users.stream().map(MyUserMapper::toDTO).toList();
    }

}
