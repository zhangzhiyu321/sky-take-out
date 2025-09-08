package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional
    public void addDish(DishDTO dto) {
        // 1. 构造菜品基本信息数据, 将其存入dish表中
        log.info("新增菜品");
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dishMapper.addDish(dish);

        // 2. 构造菜品口味列表数据, 将其存入dish_flavor表中
        log.info("新增菜品口味");
        List<DishFlavor> flavors = dto.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            // 2.1 关联菜品id
            flavors.forEach(flavor -> {
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.addDishFlavor(flavors);
        }
    }

    @Override
    public PageResult page(DishPageQueryDTO dto) {
        log.info("分页查询菜品");
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<Dish> list = dishMapper.list(dto);
        Page<Dish> page = (Page<Dish>) list;
        return new PageResult(page.getTotal(), page.getResult());
    }
}
