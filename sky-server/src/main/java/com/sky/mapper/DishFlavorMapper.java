package com.sky.mapper;

import com.sky.anno.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface DishFlavorMapper {

    void addDishFlavor(List<DishFlavor> flavors);
}
