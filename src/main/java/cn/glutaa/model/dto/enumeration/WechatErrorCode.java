package cn.glutaa.model.dto.enumeration;

public enum WechatErrorCode {

    SUCCESS(0, "认证成功！"),
    FAIL(40029, "无效的js_code！"),
    BUSY(45011, "用户认证繁忙，请重试！"),
    BUSY2(-1, "用户认证繁忙，请重试！"),
    REJUCT(40226, "登录已被拦截！");


    //状态码
    int code;
    //提示信息
    String message;

    WechatErrorCode(int i, String s) {
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessageByCode(int code) {
        for (WechatErrorCode c : values()) {
            if (c.code == code) {
                return c.message;
            }
        }
        return "用户认证失败！";
    }
}
