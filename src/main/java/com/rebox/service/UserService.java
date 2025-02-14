package com.rebox.service;

import com.rebox.domain.dto.LoginDTO;
import com.rebox.domain.dto.UserInfoDTO;
import com.rebox.domain.dto.PageDTO;
import com.rebox.domain.query.LoginQuery;
import com.rebox.domain.query.RegisterQuery;
import com.rebox.domain.query.UserQuery;

import java.util.List;

public interface UserService {

    LoginDTO register(RegisterQuery registerQuery);

    LoginDTO login(LoginQuery loginQuery);

    Boolean logout(String token);

    PageDTO<UserInfoDTO> getUserByPage(Integer current);

    int saveUser(UserQuery userQuery);

    UserInfoDTO getUserById(Integer id);

    int updateUser(UserQuery userQuery);

    int delUserById(Integer id);

    int batchDelUserById(List<String> idList);

    List<UserInfoDTO> getUserByAll();


}