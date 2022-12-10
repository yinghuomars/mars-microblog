package com.yinghuomars.microblog.controller;

import com.yinghuomars.microblog.dto.OperationLogDTO;
import com.yinghuomars.microblog.service.OperationLogService;
import com.yinghuomars.microblog.vo.ConditionVO;
import com.yinghuomars.microblog.vo.PageResult;
import com.yinghuomars.microblog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志控制器
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/27
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Api(tags = "日志模块")
@RestController
public class LogController {
    private final OperationLogService operationLogService;

    /**
     * 查看操作日志
     *
     * @param conditionVO 条件
     * @return {@link Result<OperationLogDTO>} 日志列表
     */
    @ApiOperation(value = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public Result<PageResult<OperationLogDTO>> listOperationLogs(ConditionVO conditionVO) {
        return Result.ok(operationLogService.listOperationLogs(conditionVO));
    }

    /**
     * 删除操作日志
     *
     * @param logIdList 日志id列表
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除操作日志")
    @DeleteMapping("/admin/operation/logs")
    public Result<?> deleteOperationLogs(@RequestBody List<Integer> logIdList) {
        operationLogService.removeByIds(logIdList);
        return Result.ok();
    }

}
