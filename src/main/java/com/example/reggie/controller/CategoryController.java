package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.common.R;
import com.example.reggie.entity.Category;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category: {}", category);
        categoryService.save(category);
        return R.success("New Category Created Successfully !");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {

        // construct page logic
        Page<Category> pageInfo = new Page<>(page, pageSize);

        // construct condition filter
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // add a sort
        lambdaQueryWrapper.orderByAsc(Category::getSort);

        // execute searching
        categoryService.page(pageInfo, lambdaQueryWrapper);
        // no return require, since the page class will handle them all
        // return to backend/page/member/list.html, line 144-150, this.table = res.data.records; this.count = res.data.total

        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids) { // frontend request is ids
        log.info("deleting Category id: {}", ids);
//        categoryService.removeById(id);
        categoryService.remove(ids);
        return R.success("Category deleted Successfully !");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("Editing Category: {}", category);
        categoryService.updateById(category);
        return R.success("Category edited Successfully !");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // add condition
        lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        // sort
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(lambdaQueryWrapper);

        // 返还 数据库中所有种类

        return R.success(list);
    }
}
