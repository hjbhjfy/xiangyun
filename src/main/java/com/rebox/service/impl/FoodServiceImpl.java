package com.rebox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.domain.dto.FoodDTO;
import com.rebox.domain.po.FoodPO;
import com.rebox.domain.query.FoodQuery;
import com.rebox.mapper.FoodMapper;
import com.rebox.service.FoodService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, FoodPO> implements FoodService {

    @Resource
    private FoodMapper foodMapper;

    @Override
    public boolean addFood(FoodDTO dto) {
        FoodPO po = new FoodPO();
        BeanUtils.copyProperties(dto, po);
        return save(po);
    }

    @Override
    public boolean updateFood(FoodDTO dto) {
        FoodPO po = new FoodPO();
        BeanUtils.copyProperties(dto, po);
        return updateById(po);
    }

    @Override
    public boolean deleteFood(Long id) {
        return removeById(id);
    }

    @Override
    public FoodDTO getFoodById(Long id) {
        FoodPO po = getById(id);
        FoodDTO dto = new FoodDTO();
        BeanUtils.copyProperties(po, dto);
        return dto;
    }

    @Override
    public Page<FoodDTO> getFoodList(FoodQuery query) {
        LambdaQueryWrapper<FoodPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getName() != null, FoodPO::getName, query.getName());

        Page<FoodPO> poPage = foodMapper.selectPage(new Page<>(query.getPage(), query.getRows()), wrapper);

        Page<FoodDTO> dtoPage = new Page<>();
        dtoPage.setCurrent(poPage.getCurrent());
        dtoPage.setSize(poPage.getSize());
        dtoPage.setTotal(poPage.getTotal());
        dtoPage.setRecords(
                poPage.getRecords().stream().map(po -> {
                    FoodDTO dto = new FoodDTO();
                    BeanUtils.copyProperties(po, dto);
                    return dto;
                }).collect(Collectors.toList())
        );

        return dtoPage;
    }

    @Override
    public List<FoodDTO> getAllFoods() {
        return list().stream().map(po -> {
            FoodDTO dto = new FoodDTO();
            BeanUtils.copyProperties(po, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}