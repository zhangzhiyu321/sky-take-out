package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishMapper dishMapper;

    @PostMapping
    public Result save(@RequestBody DishDTO dto){
        dishService.addDish(dto);
        return Result.success();
    }

    @GetMapping("/page")
    public Result page(DishPageQueryDTO dto) {
        log.info("分页查询菜品");
        PageResult pageResult = dishService.page(dto);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result delByIds(@RequestParam List<Integer> ids){
        log.info("批量删除菜品");
        dishService.delByIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("菜品回显");
        DishVO dto = dishService.getById(id);
        return Result.success(dto);
    }

    @PutMapping
    public Result update(@RequestBody DishDTO dto){
        log.info("菜品修改");
        dishService.update(dto);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrstop(@PathVariable Integer status, Long id){
        log.info("菜品状态修改");
        dishService.startOrstop(status, id);
        return Result.success();
    }

}
