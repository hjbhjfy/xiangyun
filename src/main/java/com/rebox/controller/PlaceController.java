package com.rebox.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rebox.domain.dto.PlaceDTO;
import com.rebox.domain.dto.SubPlaceDTO;
import com.rebox.domain.query.PlaceQuery;
import com.rebox.result.RestResult;
import com.rebox.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "景点管理", tags = "景点管理 API")
@RestController
@RequestMapping("/place")
@Tag(name = "景点管理")
public class PlaceController {

    @Resource
    private PlaceService placeService;

    @ApiOperation(value = "添加景点", notes = "新增一条景点记录")
    @PostMapping("/add")
    public RestResult add(@Valid @RequestBody PlaceDTO placeDTO) {
        return placeService.addPlace(placeDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "更新景点", notes = "根据ID更新景点内容")
    @PostMapping("/update")
    public RestResult update(@Valid @RequestBody PlaceDTO placeDTO) {
        return placeService.updatePlace(placeDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "删除景点", notes = "根据ID删除景点")
    @ApiImplicitParam(name = "id", value = "景点ID", required = true, paramType = "query")
    @GetMapping("/delete")
    public RestResult delete(@RequestParam("id") Long id) {
        return placeService.deletePlace(id) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "查询景点详情", notes = "根据ID获取景点内容")
    @ApiImplicitParam(name = "id", value = "景点ID", required = true, paramType = "query")
    @GetMapping("/get")
    public RestResult getById(@RequestParam("id") Long id) {
        return RestResult.SUCCESS(placeService.getPlaceById(id));
    }

    @ApiOperation(value = "查询所有景点", notes = "获取所有景点列表")
    @GetMapping("/list/all")
    public RestResult getAll() {
        List<PlaceDTO> list = placeService.getAllPlaces();
        return RestResult.SUCCESS(list);
    }

    @ApiOperation(value = "分页查询景点", notes = "根据条件进行分页查询景点")
    @PostMapping("/list")
    public RestResult list(@RequestBody PlaceQuery query) {
        Page<PlaceDTO> page = placeService.getPlaceList(query);
        return RestResult.SUCCESS(page);
    }

    @ApiOperation(value = "查询某景点下的所有次级景点", notes = "根据景点ID查询所有次级景点")
    @ApiImplicitParam(name = "placeId", value = "景点ID", required = true, paramType = "query")
    @GetMapping("/list/subplaces")
    public RestResult getSubPlaces(@RequestParam("placeId") Long placeId) {
        List<SubPlaceDTO> subPlaces = placeService.getSubPlacesByPlaceId(placeId);
        return RestResult.SUCCESS(subPlaces);
    }
}