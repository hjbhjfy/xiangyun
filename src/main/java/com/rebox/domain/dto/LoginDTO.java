package com.rebox.domain.dto;

import com.rebox.enums.CodeEnum;
import com.rebox.enums.CodeEnum;
import lombok.Data;

@Data
public class LoginDTO {

    private CodeEnum codeEnum;

    private Object data;

    public LoginDTO() {
    }

    public LoginDTO(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public LoginDTO(CodeEnum codeEnum, Object data) {
        this.codeEnum = codeEnum;
        this.data = data;
    }
}