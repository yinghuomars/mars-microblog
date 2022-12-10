package com.yinghuomars.microblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinghuomars.microblog.dao.ChatRecordDao;
import com.yinghuomars.microblog.entity.ChatRecord;
import com.yinghuomars.microblog.service.ChatRecordService;
import org.springframework.stereotype.Service;

/**
 * 聊天记录服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/28
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordDao, ChatRecord> implements ChatRecordService {

}
