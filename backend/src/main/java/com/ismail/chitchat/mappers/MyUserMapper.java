package com.ismail.chitchat.mappers;

import java.util.List;

import com.ismail.chitchat.dto.MyUserDTO;
import com.ismail.chitchat.entities.MyUser;

public class MyUserMapper {
    public static MyUserDTO toDTO(MyUser user) {
        return new MyUserDTO(user.getUsername(), user.getPhone(), user.getAge(), user.getRole());
    }

    public static List<MyUserDTO> toDTOList(List<MyUser> users) {
        return users.stream().map(MyUserMapper::toDTO).toList();
    }

}
