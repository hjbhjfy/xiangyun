package com.rebox.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rebox.domain.dto.FoodDTO;
import com.rebox.domain.query.FoodQuery;
import com.rebox.result.RestResult;
import com.rebox.service.FoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "食品管理", tags = "食品管理 API")
@RestController
@RequestMapping("/food")
@Tag(name = "食品管理")
public class FoodController {

    @Resource
    private FoodService foodService;

    @ApiOperation(value = "添加食品", notes = "新增一条食品记录")
    @PostMapping("/add")
    public RestResult add(@Valid @RequestBody FoodDTO foodDTO) {
        return foodService.addFood(foodDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "更新食品", notes = "根据ID更新食品内容")
    @PostMapping("/update")
    public RestResult update(@Valid @RequestBody FoodDTO foodDTO) {
        return foodService.updateFood(foodDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "删除食品", notes = "根据ID删除食品")
    @ApiImplicitParam(name = "id", value = "食品ID", required = true, paramType = "query")
    @GetMapping("/delete")
    public RestResult delete(@RequestParam("id") Long id) {
        return foodService.deleteFood(id) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "查询食品详情", notes = "根据ID获取食品内容")
    @ApiImplicitParam(name = "id", value = "食品ID", required = true, paramType = "query")
    @GetMapping("/get")
    public RestResult getById(@RequestParam("id") Long id) {
        return RestResult.SUCCESS(foodService.getFoodById(id));
    }

    @ApiOperation(value = "查询所有食品", notes = "获取所有食品列表")
    @GetMapping("/list/all")
    public RestResult getAll() {
        List<FoodDTO> list = foodService.getAllFoods();
        return RestResult.SUCCESS(list);
    }

    @ApiOperation(value = "分页查询食品", notes = "根据条件进行分页查询食品")
    @PostMapping("/list")
    public RestResult list(@RequestBody FoodQuery query) {
        Page<FoodDTO> page = foodService.getFoodList(query);
        return RestResult.SUCCESS(page);
    }
}