package com.rebox.domain.query;

import lombok.Data;

@Data
public class LoginQuery {

    private String loginAct;

    private String loginPwd;

    private Boolean isRememberMe;
}
