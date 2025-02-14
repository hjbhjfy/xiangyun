package com.rebox.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rebox.domain.dto.CulturalCreationDTO;
import com.rebox.domain.po.CulturalCreationPO;
import com.rebox.domain.query.CulturalCreationQuery;

import java.util.List;

public interface CulturalCreationService extends IService<CulturalCreationPO> {
    boolean addCulturalCreation(CulturalCreationDTO dto);

    boolean updateCulturalCreation(CulturalCreationDTO dto);

    boolean deleteCulturalCreation(Long id);

    CulturalCreationDTO getCulturalCreationById(Long id);

    Page<CulturalCreationDTO> getCulturalCreationList(CulturalCreationQuery query);

    List<CulturalCreationDTO> getAllCulturalCreations();
}