package com.omtou.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.omtou.ruiji.common.R;
import com.omtou.ruiji.entity.Category;
import com.omtou.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-13 15:09
 **/
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category: {}", category);
        categoryService.save(category);
        return R.success("Add Category Successfully.");
    }

    /**
     * Category Pages
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * Delete category via ID
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("Delete category, id is: {}", id);
        categoryService.remove(id);
        return R.success("Successfully Deleted.");
    }

    /**
     * Update category via ID
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("Successfully Updated.");
    }

    /**
     * Find category via criteria
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());

        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(lambdaQueryWrapper);

        return R.success(list);
    }

}
