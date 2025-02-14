package com.rebox.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rebox.domain.dto.FoodDTO;
import com.rebox.domain.po.FoodPO;
import com.rebox.domain.query.FoodQuery;

import java.util.List;

public interface FoodService extends IService<FoodPO> {
    boolean addFood(FoodDTO dto);

    boolean updateFood(FoodDTO dto);

    boolean deleteFood(Long id);

    FoodDTO getFoodById(Long id);

    Page<FoodDTO> getFoodList(FoodQuery query);

    List<FoodDTO> getAllFoods();
}