package com.rebox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.domain.dto.PlaceDTO;
import com.rebox.domain.dto.SubPlaceDTO;
import com.rebox.domain.po.PlacePO;
import com.rebox.domain.po.SubPlacePO;
import com.rebox.domain.query.PlaceQuery;
import com.rebox.mapper.PlaceMapper;
import com.rebox.mapper.SubPlaceMapper;
import com.rebox.service.PlaceService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, PlacePO> implements PlaceService {

    @Resource
    private PlaceMapper placeMapper;

    @Resource
    private SubPlaceMapper subPlaceMapper;

    @Override
    public boolean addPlace(PlaceDTO dto) {
        PlacePO po = new PlacePO();
        BeanUtils.copyProperties(dto, po);
        return save(po);
    }

    @Override
    public boolean updatePlace(PlaceDTO dto) {
        PlacePO po = new PlacePO();
        BeanUtils.copyProperties(dto, po);
        return updateById(po);
    }

    @Override
    public boolean deletePlace(Long id) {
        return removeById(id);
    }

    @Override
    public PlaceDTO getPlaceById(Long id) {
        PlacePO po = getById(id);
        PlaceDTO dto = new PlaceDTO();
        BeanUtils.copyProperties(po, dto);
        return dto;
    }

    @Override
    public Page<PlaceDTO> getPlaceList(PlaceQuery query) {
        LambdaQueryWrapper<PlacePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getName() != null, PlacePO::getName, query.getName());

        Page<PlacePO> poPage = placeMapper.selectPage(new Page<>(query.getPage(), query.getRows()), wrapper);

        Page<PlaceDTO> dtoPage = new Page<>();
        dtoPage.setCurrent(poPage.getCurrent());
        dtoPage.setSize(poPage.getSize());
        dtoPage.setTotal(poPage.getTotal());
        dtoPage.setRecords(
                poPage.getRecords().stream().map(po -> {
                    PlaceDTO dto = new PlaceDTO();
                    BeanUtils.copyProperties(po, dto);
                    return dto;
                }).collect(Collectors.toList())
        );

        return dtoPage;
    }

    @Override
    public List<PlaceDTO> getAllPlaces() {
        List<PlacePO> poList = list();  // 查询所有景点
        return poList.stream().map(po -> {
            PlaceDTO dto = new PlaceDTO();
            BeanUtils.copyProperties(po, dto);
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<SubPlaceDTO> getSubPlacesByPlaceId(Long placeId) {
        List<SubPlacePO> subPlacePOs = subPlaceMapper.selectList(
                new LambdaQueryWrapper<SubPlacePO>().eq(SubPlacePO::getParentPlaceId, placeId)
        );

        return subPlacePOs.stream().map(po -> {
            SubPlaceDTO dto = new SubPlaceDTO();
            BeanUtils.copyProperties(po, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}