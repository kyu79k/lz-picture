package com.lz.lzpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.lzpicturebackend.model.dto.space.SpaceAddRequest;
import com.lz.lzpicturebackend.model.dto.space.SpaceQueryRequest;
import com.lz.lzpicturebackend.model.enity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.lzpicturebackend.model.enity.User;
import com.lz.lzpicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author lz
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2026-02-27 20:21:28
*/
public interface SpaceService extends IService<Space> {

    /**
     * 创建空间
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 校验空间
     * @param space
     * @param add
     */
    void validSpace(Space space,boolean add);

    /**
     * 获取空间包装类（单条）
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取空间包装类（分页）
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 获取查询的 queryWrapper
     * @param spaceQueryRequest 空间请求类
     * @return 可用来查询的 queryWrapper
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 根据空间级别填充空间对象
     * @param space
     */
     void fillSpaceBySpaceLevel(Space space);




}
