package com.yinghuomars.microblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinghuomars.microblog.dto.MessageBackDTO;
import com.yinghuomars.microblog.dto.MessageDTO;
import com.yinghuomars.microblog.entity.Message;
import com.yinghuomars.microblog.vo.ConditionVO;
import com.yinghuomars.microblog.vo.MessageVO;
import com.yinghuomars.microblog.vo.PageResult;
import com.yinghuomars.microblog.vo.ReviewVO;

import java.util.List;

/**
 * 留言服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/29
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MessageService extends IService<Message> {

    /**
     * 添加留言弹幕
     *
     * @param messageVO 留言对象
     */
    void saveMessage(MessageVO messageVO);

    /**
     * 查看留言弹幕
     *
     * @return 留言列表
     */
    List<MessageDTO> listMessages();

    /**
     * 审核留言
     *
     * @param reviewVO 审查签证官
     */
    void updateMessagesReview(ReviewVO reviewVO);

    /**
     * 查看后台留言
     *
     * @param condition 条件
     * @return 留言列表
     */
    PageResult<MessageBackDTO> listMessageBackDTO(ConditionVO condition);

}
