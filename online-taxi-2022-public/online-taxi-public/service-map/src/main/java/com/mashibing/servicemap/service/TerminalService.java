package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;

import java.util.List;

public interface TerminalService {

    ResponseResult add(String name,String desc);

    ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius);

    ResponseResult<TrSearchResponse> trsearch(String tid, Long starttime, Long endtime);

    ResponseResult<TrSearchResponse> trsearchByTrid(String tid, String trid);
}
