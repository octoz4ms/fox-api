package com.octo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.octo.entity.ExcelUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserExcelListener extends AnalysisEventListener<ExcelUser> {

    private final List<ExcelUser> dataList = new ArrayList<>();

    @Override
    public void invoke(ExcelUser user, AnalysisContext analysisContext) {
        dataList.add(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

}
