package com.rebox.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rebox.domain.dto.PlaceDTO;
import com.rebox.domain.dto.SubPlaceDTO;
import com.rebox.domain.po.PlacePO;
import com.rebox.domain.query.PlaceQuery;

import java.util.List;

public interface PlaceService extends IService<PlacePO> {
    boolean addPlace(PlaceDTO dto);

    boolean updatePlace(PlaceDTO dto);

    boolean deletePlace(Long id);

    PlaceDTO getPlaceById(Long id);

    Page<PlaceDTO> getPlaceList(PlaceQuery query);

    List<PlaceDTO> getAllPlaces();

    List<SubPlaceDTO> getSubPlacesByPlaceId(Long placeId); // 额外方法
}