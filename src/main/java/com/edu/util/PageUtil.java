package com.edu.util;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class PageUtil {

    /**
     * 通用pageInfo转换
     *
     * @param sourcePageInfo 源数据
     * @param targetClass    目标类型
     * @param mapper         list转换方法
     * @param <T>            目标类型
     * @param <S>            源类型
     * @return
     */
    public static <T, S> PageInfo<T> pageInfoCopy(PageInfo<S> sourcePageInfo, Class<T> targetClass) {
        log.info("分页转换至{}", targetClass.getSimpleName());
        PageInfo<T> respPageInfo = new PageInfo<>();
        respPageInfo.setPageNum(sourcePageInfo.getPageNum());
        respPageInfo.setPageSize(sourcePageInfo.getPageSize());
        respPageInfo.setSize(sourcePageInfo.getSize());
        respPageInfo.setStartRow(sourcePageInfo.getStartRow());
        respPageInfo.setEndRow(sourcePageInfo.getEndRow());
        respPageInfo.setPages(sourcePageInfo.getPages());
        respPageInfo.setPrePage(sourcePageInfo.getPrePage());
        respPageInfo.setNextPage(sourcePageInfo.getNextPage());
        respPageInfo.setIsFirstPage(sourcePageInfo.isIsFirstPage());
        respPageInfo.setIsLastPage(sourcePageInfo.isIsLastPage());
        respPageInfo.setHasPreviousPage(sourcePageInfo.isHasPreviousPage());
        respPageInfo.setHasNextPage(sourcePageInfo.isHasNextPage());
        respPageInfo.setNavigatePages(sourcePageInfo.getNavigatePages());
        respPageInfo.setNavigatepageNums(sourcePageInfo.getNavigatepageNums());
        respPageInfo.setNavigateFirstPage(sourcePageInfo.getNavigateFirstPage());
        respPageInfo.setNavigateLastPage(sourcePageInfo.getNavigateLastPage());
        respPageInfo.setTotal(sourcePageInfo.getTotal());
        List<T> pageList = sourcePageInfo.getList()
                .stream().map(r -> BeanConvertor.convert(r, targetClass))
                .collect(Collectors.toList());
        respPageInfo.setList(pageList);
        return respPageInfo;
    }
}

