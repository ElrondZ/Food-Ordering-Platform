package com.omtou.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.omtou.ruiji.common.R;
import com.omtou.ruiji.entity.Category;
import com.omtou.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * add category
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("add category successfully");
    }

    /**
     * searching by pages
     * @param page
     * @param pageSize
     * @return
     */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        //pagination constructor
        Page<Category> pageInfo = new Page<>(page,pageSize);
        //condition constructor
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //add ordering conditions, order by sorting
        queryWrapper.orderByAsc(Category::getSort);
        //searching by pagination
        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);


    }

    @DeleteMapping
    public R<String> delete (Long ids) {
        log.info("delete category, id:{}",ids);
        //categoryService.removeById(id);
        categoryService.remove(ids);


        return R.success("successfully delete category info");
    }

    /**
     * modify category info by ID
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("modify classified info:{}", category);

        categoryService.updateById(category);

        return  R.success("successfully modify classified info");
    }

}
