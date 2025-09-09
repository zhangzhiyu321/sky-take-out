package com.sky.mapper;

import com.sky.anno.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface DishFlavorMapper {

    void addDishFlavor(List<DishFlavor> flavors);

    void delByIds(List<Integer> ids);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(DishFlavor dishFlavor);

    @Delete("delete from dish_flavor where id = #{id}")
    void deleteByDishId(Long id);
}
