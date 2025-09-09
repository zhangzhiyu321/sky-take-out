package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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
    @Autowired
    private SetmealMapper setmealMapper;

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
        List<DishVO> list = dishMapper.list(dto);
        Page<DishVO> page = (Page<DishVO>) list;
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delByIds(List<Integer> ids) {
        // 1. 起售中的菜品不能删除
        ids.forEach(id -> {
            Dish dish = dishMapper.selectById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        // 2. 被套餐关联的菜品不能删除
        Integer count = setmealMapper.countByDishId(ids);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        // 3. 删除菜单
        dishMapper.delByIds(ids);

        // 4. 删除口味
        dishFlavorMapper.delByIds(ids);
    }

    @Override
    public DishVO getById(Integer id) {
        DishVO dishVO = new DishVO();
        Dish dish = dishMapper.selectById(id);
        BeanUtils.copyProperties(dish,dishVO);
        List<DishFlavor> flavors = dishFlavorMapper.getById(id);
        dishVO.setFlavors( flavors);
        return dishVO;
    }

    @Override
    @Transactional
    public void update(DishDTO dto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        // 1. 修改菜品的基本信息, dish_flavor表
        dishMapper.update(dish);

        // 2. 修改口味列表信息, dish_flavor表
        dishFlavorMapper.deleteByDishId(dto.getId());
        List<DishFlavor> list = dto.getFlavors();
        if(list != null && !list.isEmpty()){
            list.forEach(flavor -> {
                flavor.setDishId(dto.getId());
            });
            dishFlavorMapper.addDishFlavor(list);
        }
    }

    @Override
    public void startOrstop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }
}
