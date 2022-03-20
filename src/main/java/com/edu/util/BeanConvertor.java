package com.edu.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

public class BeanConvertor {

    public BeanConvertor() {
    }

    public static <OUT> OUT convert(Object in, Class<OUT> clazz) {
        return in == null ? null : JSON.parseObject(JSON.toJSONString(in), clazz);
    }

    public static <OUT> List<OUT> convertCollection(Collection<? extends Object> in, Class<OUT> clazz) {
        return in == null ? null : JSON.parseArray(JSON.toJSONString(in), clazz);
    }

    public static <OUT> IPage<OUT> convertPage(IPage<? extends Object> in, Class<OUT> clazz) {
        if (in == null) {
            return null;
        } else {
            Page<OUT> page = new Page();
            page.setCurrent(in.getCurrent());
            page.setPages(in.getPages());
            page.setSize(in.getSize());
            page.setTotal(in.getTotal());
            List<? extends Object> records = in.getRecords();
            List<OUT> converted = convertCollection(records, clazz);
            page.setRecords(converted);
            return page;
        }
    }
}
