package com.octo.dto.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long count;
    private List<T> list;

    public PageResult(Page<T> page) {
        count = page.getTotal();
        list = page.getRecords();
    }
}
