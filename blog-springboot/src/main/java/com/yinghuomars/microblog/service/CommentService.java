package com.yinghuomars.microblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinghuomars.microblog.dto.CommentBackDTO;
import com.yinghuomars.microblog.dto.CommentDTO;
import com.yinghuomars.microblog.dto.ReplyDTO;
import com.yinghuomars.microblog.entity.Comment;
import com.yinghuomars.microblog.vo.CommentVO;
import com.yinghuomars.microblog.vo.ConditionVO;
import com.yinghuomars.microblog.vo.PageResult;
import com.yinghuomars.microblog.vo.ReviewVO;

import java.util.List;

/**
 * 评论服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/29
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查看评论
     *
     * @param commentVO 评论信息
     * @return 评论列表
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);

    /**
     * 查看评论下的回复
     *
     * @param commentId 评论id
     * @return 回复列表
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    /**
     * 添加评论
     *
     * @param commentVO 评论对象
     */
    void saveComment(CommentVO commentVO);

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     */
    void saveCommentLike(Integer commentId);

    /**
     * 审核评论
     *
     * @param reviewVO 审核信息
     */
    void updateCommentsReview(ReviewVO reviewVO);

    /**
     * 查询后台评论
     *
     * @param condition 条件
     * @return 评论列表
     */
    PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition);

}
