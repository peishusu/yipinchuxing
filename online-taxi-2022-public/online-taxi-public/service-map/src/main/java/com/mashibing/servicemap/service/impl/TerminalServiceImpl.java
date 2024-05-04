package com.mashibing.servicemap.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;
import com.mashibing.servicemap.remote.TerminalClient;
import com.mashibing.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 17:16
 **/
@Service
public class TerminalServiceImpl implements TerminalService {

    @Autowired
    private TerminalClient terminalClient;


    @Override
    public ResponseResult add(String name,String desc) {
        return terminalClient.add(name,desc);
    }

    @Override
    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius) {
        return terminalClient.aroundsearch(center,radius);
    }

    @Override
    public ResponseResult<TrSearchResponse> trsearch(String tid, Long starttime, Long endtime) {
        return terminalClient.trsearch(tid,starttime,endtime);
    }

    @Override
    public ResponseResult<TrSearchResponse> trsearchByTrid(String tid, String trid) {
        return terminalClient.trsearchByTrid(tid,trid);
    }
}
