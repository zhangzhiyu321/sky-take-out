package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    public PageResult page(DishPageQueryDTO dto);

    void addDish(DishDTO dto);

    void delByIds(List<Integer> ids);

    DishVO getById(Integer id);

    void update(DishDTO dto);

    void startOrstop(Integer status, Long id);
}
