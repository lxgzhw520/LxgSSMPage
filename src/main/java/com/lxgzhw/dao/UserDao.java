package com.lxgzhw.dao;

import com.lxgzhw.bean.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    /**
     * 查询所有的用户数据
     *
     * @return 用户数据集合
     */
    @Select("select *from user")
    List<User> findAll();

    /**
     * 使用分页查询所有的用户数据
     *
     * @param currentPage 当前页
     * @param rows        每页数量
     * @return 返回值
     */
    @Select("select *from user limit #{currentPage},#{rows}")
    List<User> findByLimit(@Param("currentPage") int currentPage, @Param("rows") int rows);

    /**
     * 查询数据库中的数据总量
     *
     * @return 数据总量
     */
    @Select("select count(*) from user")
    long findCount();
}
