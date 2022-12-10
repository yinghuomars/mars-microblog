package com.yinghuomars.microblog.controller;

import com.yinghuomars.microblog.annotation.OptLog;
import com.yinghuomars.microblog.service.PageService;
import com.yinghuomars.microblog.vo.PageVO;
import com.yinghuomars.microblog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.yinghuomars.microblog.constant.OptTypeConst.REMOVE;
import static com.yinghuomars.microblog.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * 页面控制器
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/09
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Api(tags = "页面模块")
@RestController
public class PageController {
    private final PageService pageService;

    /**
     * 删除页面
     *
     * @param pageId 页面id
     * @return {@link Result <>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除页面")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, dataType = "Integer")
    @DeleteMapping("/admin/pages/{pageId}")
    public Result<?> deletePage(@PathVariable("pageId") Integer pageId) {
        pageService.deletePage(pageId);
        return Result.ok();
    }

    /**
     * 保存或更新页面
     *
     * @param pageVO 页面信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新页面")
    @PostMapping("/admin/pages")
    public Result<?> saveOrUpdatePage(@Valid @RequestBody PageVO pageVO) {
        pageService.saveOrUpdatePage(pageVO);
        return Result.ok();
    }

    /**
     * 获取页面列表
     *
     * @return {@link Result<PageVO>}
     */
    @ApiOperation(value = "获取页面列表")
    @GetMapping("/admin/pages")
    public Result<List<PageVO>> listPages() {
        return Result.ok(pageService.listPages());
    }

}
