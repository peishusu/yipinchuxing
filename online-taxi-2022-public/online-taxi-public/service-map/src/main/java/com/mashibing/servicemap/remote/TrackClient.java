package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 19:24
 **/
@Service
public class TrackClient {
    @Value("${aMap.key}")
    private String amapKey;

    @Value("${aMap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult add(String tid) {
        //
        StringBuilder sb = new StringBuilder();
        sb.append(AmapConfigConstants.TRACK_ADD);
        sb.append("?");
        sb.append("key=" + amapKey);
        sb.append("&");
        sb.append("sid=" + amapSid);
        sb.append("&");
        sb.append("tid=" + tid);
        //调用高德 的远程创建轨迹
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sb.toString(), null, String.class);

        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        //轨迹id
        String trid = data.getString("trid");
        //轨迹名称
        String trname = "";
        if (data.has("trname")){
            trname = data.getString("trname");
        }
        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setTrname(trname);
        return ResponseResult.success(trackResponse);
    }

}
