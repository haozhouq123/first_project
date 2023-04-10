package cn.glutaa.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    private String user_id;
    private String nick_name;
    private String session_key;
    private String unionid;
}
