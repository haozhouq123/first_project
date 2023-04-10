package cn.glutaa.model.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity //实体类标识
@Table(name = "user")//表名
public class User {

    /*** 用户标识 ***/
    @Id
    @Column(name = "open_id", nullable = false, length = 30)
    private String openId;
    @Column(name = "union_id", nullable = false, length = 30)
    private String unionId;
    /*** 个人基本信息 ***/
    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;
    @Column(name = "gender", nullable = false)
    private Integer gender;//后续会改枚举
    @Column(name = "real_name", nullable = false, length = 200)//加密 长度*10
    private String realName;
    @Column(name = "phone_number", nullable = false, length = 150)//加密 长度*10
    private String phoneNumber;
    @Column(name = "id_number", nullable = false, length = 200)//加密 长度*10
    private String idNumber;
    @Column(name = "email", length = 300)
    private String email;
    @Column(name = "birth_day", length = 100)
    private Date birthDay;
    @Column(name = "introduction", length = 300)
    private String introduction;
    @Column(name = "interest", length = 300)
    private String interest;
    @Column(name = "address", length = 300)
    private String address;

    /*** 个人院校信息 ***/
    @Column(name = "student_number", length = 300)//加密 长度*10
    private String studentNumber;//学号
    @Column(name = "college_id")
    private Integer collegeId;//学院（关联学院信息表）
    @Column(name = "major_id")
    private Integer majorId;//专业（关联专业信息表）
    @Column(name = "grade_id")
    private Integer gradeId;//年级班级id（关联班级信息表）

    /*** 组织活动信息 ***/
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "organization_id")
    private Integer organizationId;

//    /*** 个人职业信息 ***/
//    @Column(name = "enterprise", length = 100)
//    private String enterprise;//企业
//    @Column(name = "trade", length = 50)
//    private String trade;//从事行业 例如：计算机/互联网
//    @Column(name = "occupation", length = 50)
//    private String occupation;//从事职业 例如：软件开发
//    @Column(name = "job_title", length = 50)
//    private String jobTitle;//职称 例如：高级程序员
//    @Column(name = "job_introduction", length = 200)
//    private String jobIntroduction;//职业简介

    /*** 账户信息 ***/
    @Column(name = "salt")
    private String salt;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "status", nullable = false)
    private Integer status; //后续会改枚举
}
