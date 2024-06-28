package com.knf.dev.demo.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.knf.dev.demo.model.User;

@Mapper
public interface UserRepository {

    @Select("select * from users")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "emailId", column = "email_id")
    })
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "emailId", column = "email_id")
    })
    Optional<User> findById(Integer id);

    @Select("<script>SELECT * FROM users WHERE id in <foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach></script>")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "emailId", column = "email_id")
    })
    List<User> findByIds(List<Integer> ids);

    @Select("SELECT * FROM users WHERE first_name like '%' || #{name} || '%' " +
                "or last_name like '%' || #{name} ||'%' ")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "emailId", column = "email_id")
    })
    Optional<List<User>> findByName(String name);


    @Select({
        "<script>",
        "SELECT * FROM users",
            "WHERE first_name IN ",
            "<foreach item='name' collection='names' open='(' separator=',' close=')'>",
                // "( #{names} )",
                "#{name}",
            "</foreach>",
        "</script>"
        }
    )
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "emailId", column = "email_id")
    })
    List<User> findByNames(List<String> names);


    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Integer id);

    @Insert("INSERT INTO users(first_name, last_name, email_id) " +
            " VALUES (#{firstName}, #{lastName}, #{emailId})")
    int insert(User user);

    @Update("Update users set first_name=#{firstName}, " +
            " last_name=#{lastName}, email_id=#{emailId} where id=#{id}")
    int update(User user);
}
