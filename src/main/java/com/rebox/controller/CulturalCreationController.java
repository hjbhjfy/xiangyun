package com.rebox.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rebox.domain.dto.CulturalCreationDTO;
import com.rebox.domain.query.CulturalCreationQuery;
import com.rebox.result.RestResult;
import com.rebox.service.CulturalCreationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "文创管理", tags = "文创管理 API")
@RestController
@RequestMapping("/culturalCreation")
@Tag(name = "文创管理")
public class CulturalCreationController {

    @Resource
    private CulturalCreationService culturalCreationService;

    @ApiOperation(value = "添加文创", notes = "新增一条文创记录")
    @PostMapping("/add")
    public RestResult add(@Valid @RequestBody CulturalCreationDTO culturalCreationDTO) {
        return culturalCreationService.addCulturalCreation(culturalCreationDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "更新文创", notes = "根据ID更新文创内容")
    @PostMapping("/update")
    public RestResult update(@Valid @RequestBody CulturalCreationDTO culturalCreationDTO) {
        return culturalCreationService.updateCulturalCreation(culturalCreationDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "删除文创", notes = "根据ID删除文创")
    @ApiImplicitParam(name = "id", value = "文创ID", required = true, paramType = "query")
    @GetMapping("/delete")
    public RestResult delete(@RequestParam("id") Long id) {
        return culturalCreationService.deleteCulturalCreation(id) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "查询文创详情", notes = "根据ID获取文创内容")
    @ApiImplicitParam(name = "id", value = "文创ID", required = true, paramType = "query")
    @GetMapping("/get")
    public RestResult getById(@RequestParam("id") Long id) {
        return RestResult.SUCCESS(culturalCreationService.getCulturalCreationById(id));
    }

    @ApiOperation(value = "查询所有文创", notes = "获取所有文创列表")
    @GetMapping("/list/all")
    public RestResult getAll() {
        List<CulturalCreationDTO> list = culturalCreationService.getAllCulturalCreations();
        return RestResult.SUCCESS(list);
    }

    @ApiOperation(value = "分页查询文创", notes = "根据条件进行分页查询文创")
    @PostMapping("/list")
    public RestResult list(@RequestBody CulturalCreationQuery query) {
        Page<CulturalCreationDTO> page = culturalCreationService.getCulturalCreationList(query);
        return RestResult.SUCCESS(page);
    }
}