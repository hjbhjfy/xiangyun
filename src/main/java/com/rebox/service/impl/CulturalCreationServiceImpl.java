package com.rebox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.domain.dto.CulturalCreationDTO;
import com.rebox.domain.po.CulturalCreationPO;
import com.rebox.domain.query.CulturalCreationQuery;
import com.rebox.mapper.CulturalCreationMapper;
import com.rebox.service.CulturalCreationService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CulturalCreationServiceImpl extends ServiceImpl<CulturalCreationMapper, CulturalCreationPO> implements CulturalCreationService {

    @Resource
    private CulturalCreationMapper culturalCreationMapper;

    @Override
    public boolean addCulturalCreation(CulturalCreationDTO dto) {
        CulturalCreationPO po = new CulturalCreationPO();
        BeanUtils.copyProperties(dto, po);
        return save(po);
    }

    @Override
    public boolean updateCulturalCreation(CulturalCreationDTO dto) {
        CulturalCreationPO po = new CulturalCreationPO();
        BeanUtils.copyProperties(dto, po);
        return updateById(po);
    }

    @Override
    public boolean deleteCulturalCreation(Long id) {
        return removeById(id);
    }

    @Override
    public CulturalCreationDTO getCulturalCreationById(Long id) {
        CulturalCreationPO po = getById(id);
        CulturalCreationDTO dto = new CulturalCreationDTO();
        BeanUtils.copyProperties(po, dto);
        return dto;
    }

    @Override
    public Page<CulturalCreationDTO> getCulturalCreationList(CulturalCreationQuery query) {
        LambdaQueryWrapper<CulturalCreationPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getName() != null, CulturalCreationPO::getName, query.getName());

        Page<CulturalCreationPO> poPage = culturalCreationMapper.selectPage(new Page<>(query.getPage(), query.getRows()), wrapper);

        Page<CulturalCreationDTO> dtoPage = new Page<>();
        dtoPage.setCurrent(poPage.getCurrent());
        dtoPage.setSize(poPage.getSize());
        dtoPage.setTotal(poPage.getTotal());
        dtoPage.setRecords(
                poPage.getRecords().stream().map(po -> {
                    CulturalCreationDTO dto = new CulturalCreationDTO();
                    BeanUtils.copyProperties(po, dto);
                    return dto;
                }).collect(Collectors.toList())
        );

        return dtoPage;
    }

    @Override
    public List<CulturalCreationDTO> getAllCulturalCreations() {
        return list().stream().map(po -> {
            CulturalCreationDTO dto = new CulturalCreationDTO();
            BeanUtils.copyProperties(po, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}