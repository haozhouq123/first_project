package cn.glutaa.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class TestUser {

    @Id
    private int id;
    @Column(nullable = false,length = 25)
    private String name;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date regDate;

    //外键
    //@JoinColumn(name = "user_detail_id")
    //一对一关系
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private UserDetail userDetail;

}
