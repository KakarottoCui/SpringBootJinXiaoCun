package com.it.service.impl;

import com.it.entity.Category;
import com.it.mapper.CategoryMapper;
import com.it.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 
 * @since 2023-03-04
 */
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public Set<Integer> findGoodsByCategoryId(int id) throws Exception {
        return categoryMapper.findGoodsByCategoryId(id);
    }
}
