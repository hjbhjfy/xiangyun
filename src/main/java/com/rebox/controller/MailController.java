package com.rebox.controller;

import com.rebox.ToEmail;
import com.rebox.commonmail.CommonEmail;
import com.rebox.result.RestResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:MailController
 * Package:com.rebox.controller
 * Descriptionn:
 *
 * @Author wqh
 * @Create 2024/5/27 17:53
 * @Version 1.0
 */

@RestController
@RequestMapping("/mail")
@Tag(name = "邮件发送")
public class MailController {

    @Autowired
    private CommonEmail commonEmail;

    @Operation(summary = "发送邮件的http接口（restFul接口）")
    @PostMapping(value = "/send")
    public RestResult register(@RequestBody ToEmail toEmail) {
        return commonEmail.commonEmail(toEmail);
    }
}
