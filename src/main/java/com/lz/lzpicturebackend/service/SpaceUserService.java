package com.lz.lzpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.lzpicturebackend.model.dto.space.SpaceAddRequest;
import com.lz.lzpicturebackend.model.dto.space.SpaceQueryRequest;
import com.lz.lzpicturebackend.model.dto.spaceuser.SpaceUserAddRequest;
import com.lz.lzpicturebackend.model.dto.spaceuser.SpaceUserQueryRequest;
import com.lz.lzpicturebackend.model.enity.Space;
import com.lz.lzpicturebackend.model.enity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.lzpicturebackend.model.enity.User;
import com.lz.lzpicturebackend.model.vo.SpaceUserVO;
import com.lz.lzpicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author lz
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2026-03-01 19:16:01
*/
public interface SpaceUserService extends IService<SpaceUser> {

    /**
     * 创建空间成员
     * @param spaceUserAddRequest
     * @return
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 校验空间成员
     * @param spaceUser
     * @param add
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 获取空间成员包装类（单条）
     * @param spaceUser
     * @param request
     * @return
     */
    SpaceUserVO getSpaceVOUser(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 获取空间成员包装类（列表）
     * @param spaceUserList,
     * @return
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);

    /**
     * 获取查询对象
     * @param spaceUserQueryRequest 空间请求类
     * @return 可用来查询的 queryWrapper
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);





}
