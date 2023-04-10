package cn.glutaa.repository.test;

import cn.glutaa.model.entity.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TestUserRepository extends JpaRepository<TestUser, Integer> {


//    根据字段查询：
//          根据单个字段查询，方法名：findAllBy + 字段名
//          根据多个字段查询，方法名：findAllBy + 字段名1 + And + 字段名2
    //根据name查询
    List<TestUser> findAllByName(String name);
    List<TestUser> findAllByIdAndName(int id, String name);



//***************使用JPQL方式查询******************/

//    @Query("select u from User u")
//    List<TestUser> getUser();
//
//    @Query("select u from User u where u.name = 'ls'")
//    List<TestUser> getUser2();
//
//    @Query("select u from User u where u.name = ?1 and u.id = ?2")
//    List<TestUser> getUser21(String name, int id);
//
//    //投影结果
//    @Query("select u.id,u.name from User u where u.name = 'ls'")
//    List<UserDto> getUser3();
//
//    @Modifying //修改数据要加上该注解
//    @Transactional
//    @Query("update User set name = ?1 where id = ?2")
//    List<TestUser> updateUser(String name, int id);
//
//    //使用原生SQL
//    @Query(value = "select * from user where u.name = 'ls'", nativeQuery = true)
//    List<UserDto> getUser4();

}

class UserDto{
    int id;
    String name;
}

/*
*Spring Data JPA关键字：
*        And Or Is Equals IsNull IsNotNull Like NotLike StartWith EndWith
*       Containing OrderBy OrderBy字段Desc IgnoreCase Between
*       In NotIn T
*
*
*
*
*
*
*
*
* */