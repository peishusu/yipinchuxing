package com.mashibing.servicemap.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.TrackClient;
import com.mashibing.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 19:23
 **/
@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackClient trackClient;

    @Override
    public ResponseResult add(String tid) {
        return trackClient.add(tid);
    }
}
