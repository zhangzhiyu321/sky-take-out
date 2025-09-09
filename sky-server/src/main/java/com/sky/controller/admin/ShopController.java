package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态为:{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result getStatus(Integer status){
        log.info("获取店铺的营业状态");
        Object shopStatus = redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(shopStatus);
    }
}
