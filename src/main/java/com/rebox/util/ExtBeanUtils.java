package com.rebox.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展的BeanUtils
 *
 */
public class ExtBeanUtils {

    /**
     * 把一个list复制到另一个list中
     *
     * @param sourceList
     * @param clazz
     * @return
     * @param <T>
     * @param <E>
     */
    public static <T, E> List<T> copyPropertiesForList(List<E> sourceList, Class<T> clazz) {
        //需要把 List<TUser> 转换为 List<UserInfoDTO>
        List<T> targetList = new ArrayList<>();

        sourceList.forEach(tUser -> {
            T userInfoDTO = null;
            try {
                userInfoDTO = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            BeanUtils.copyProperties(tUser,  userInfoDTO);

            targetList.add(userInfoDTO);
        });
        return targetList;
    }
}