package cn.glutaa.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseInfo {
    private String unionId;
    private String openId;
    private String userName;
}
