package com.yinghuomars.microblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinghuomars.microblog.dto.OperationLogDTO;
import com.yinghuomars.microblog.entity.OperationLog;
import com.yinghuomars.microblog.vo.ConditionVO;
import com.yinghuomars.microblog.vo.PageResult;

/**
 * 操作日志服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/29
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查询日志列表
     *
     * @param conditionVO 条件
     * @return 日志列表
     */
    PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
