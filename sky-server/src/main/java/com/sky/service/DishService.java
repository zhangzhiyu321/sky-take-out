package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    public PageResult page(DishPageQueryDTO dto);

    void addDish(DishDTO dto);
}
