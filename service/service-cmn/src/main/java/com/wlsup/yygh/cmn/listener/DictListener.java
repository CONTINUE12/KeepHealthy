package com.wlsup.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wlsup.yygh.cmn.mapper.DictMapper;
import com.wlsup.yygh.model.cmn.Dict;
import com.wlsup.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;
    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    //一行一行读取
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //将DictEeVo实体转为Dict实体
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        //调用方法添加数据库
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
