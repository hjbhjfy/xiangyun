package com.rebox.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    /**
     * 主键，自动增长，用户ID
     */
    private Integer id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 登录token
     */
    private String token;

}
