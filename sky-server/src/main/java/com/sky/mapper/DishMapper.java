package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    List<DishVO> list(DishPageQueryDTO dto);

    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @Insert("insert into dish (name,category_id,price,status,create_time,update_time,create_user,update_user, image, description) values (#{name},#{categoryId},#{price},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser},#{image},#{description})")
    void addDish(Dish dto);

    void delByIds(List<Integer> ids);

    @Select("select * from dish where id = #{id}")
    Dish selectById(Integer id);

    @AutoFill(OperationType.UPDATE)
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void update(Dish dish);

}
