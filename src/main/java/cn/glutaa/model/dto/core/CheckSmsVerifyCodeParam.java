package cn.glutaa.model.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckSmsVerifyCodeParam {

    private String jsCode;
    private String phoneNumber;
    private String verificationCode;
}
