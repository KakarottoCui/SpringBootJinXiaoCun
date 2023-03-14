package com.it.service.impl;

import com.it.entity.Logs;
import com.it.mapper.LogsMapper;
import com.it.service.LogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 
 * @since 2023-03-03
 */
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements LogsService {

}
