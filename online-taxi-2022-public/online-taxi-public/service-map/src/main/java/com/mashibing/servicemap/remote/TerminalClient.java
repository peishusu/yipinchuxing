package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 17:16
 **/
@Service
public class TerminalClient {

    @Value("${aMap.key}")
    private String amapKey;

    @Value("${aMap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult add(String name, String desc) {
        //https://tsapi.amap.com/v1/track/terminal/add
        StringBuilder sb = new StringBuilder();
        sb.append(AmapConfigConstants.TERMINAL_ADD);
        sb.append("?");
        sb.append("key=" + amapKey);
        sb.append("&");
        sb.append("sid=" + amapSid);
        sb.append("&");
        sb.append("name=" + name);
        sb.append("&");
        sb.append("desc=" + desc);
        //调用高德 的远程创建服务
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sb.toString(), null, String.class);
        /*
        *
        *
            {
                "errcode": 10000,
                "errmsg": "OK",
                "data": {
                    "name": "车辆2",
                    "tid": 862590911,
                    "sid": 1023213
                }
            }
        *
        * */
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        /*
        * {
                "code": 1,
                "message": "success",
                "data": {
                    "tid": "862924565"
                }
            }
        *
        *
        * */
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius) {
        StringBuilder sb = new StringBuilder();
        sb.append(AmapConfigConstants.TERMINAL_AROUNDSEARCH);
        sb.append("?");
        sb.append("key=" + amapKey);
        sb.append("&");
        sb.append("sid=" + amapSid);
        sb.append("&");
        sb.append("center=" + center);
        sb.append("&");
        sb.append("radius=" + radius);
        System.out.println("终端搜索请求：" + sb.toString());
        //调用高德 的远程创建服务
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sb.toString(), null, String.class);
        System.out.println("终端搜索响应:" + stringResponseEntity.getBody());

        //解析终端搜索结果
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        JSONArray results = data.getJSONArray("results");

        List<TerminalResponse> terminalResponseList = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            JSONObject jsonObject = results.getJSONObject(i);
            //这个desc就是我们的carId
            String desc = jsonObject.getString("desc");
            String tid = jsonObject.getString("tid");

            //
            JSONObject location = jsonObject.getJSONObject("location");
            String longitude = location.getString("longitude");
            String latitude = location.getString("latitude");



            TerminalResponse terminalResponse = new TerminalResponse();
            terminalResponse.setTid(tid);
            terminalResponse.setCarId(Long.parseLong(desc));
            terminalResponse.setLongitude(longitude);
            terminalResponse.setLatitude(latitude);

            terminalResponseList.add(terminalResponse);
        }
        return ResponseResult.success(terminalResponseList);
    }


    public ResponseResult<TrSearchResponse> trsearch(String tid, Long starttime, Long endtime) {
        StringBuilder sb = new StringBuilder();
        sb.append(AmapConfigConstants.TERMINAL_TRSEARCH);
        sb.append("?");
        sb.append("key=" + amapKey);
        sb.append("&");
        sb.append("sid=" + amapSid);
        sb.append("&");
        sb.append("tid=");
        sb.append(tid);
        sb.append("&");
        sb.append("starttime=" + starttime);
        sb.append("&");
        sb.append("endtime=" + endtime);
        System.out.println("高德地图查询轨迹结果请求：" + sb.toString());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(sb.toString(), String.class);
        System.out.println("高德地图查询轨迹结果相应：" + forEntity.getBody());

        JSONObject result = JSONObject.fromObject(forEntity.getBody());
        JSONObject data = result.getJSONObject("data");
        int counts = data.getInt("counts");
        //代表该终端下面没有轨迹
        if (counts == 0){
            return null;
        }
        JSONArray tracks = data.getJSONArray("tracks");
        long driveMile = 0l;
        long driveTime = 0l;
        for (int i = 0; i < tracks.size(); i++) {
            JSONObject jsonObject = tracks.getJSONObject(i);
            long distance = jsonObject.getLong("distance");
            driveMile = driveMile + distance;

            long time = jsonObject.getLong("time");
            time = time / (1000 * 60);
            driveTime = driveTime + time;
        }

        TrSearchResponse trSearchResponse = new TrSearchResponse();
        trSearchResponse.setDriveMile(driveMile);
        trSearchResponse.setDriveTime(driveTime);
        return ResponseResult.success(trSearchResponse);
    }


    public ResponseResult<TrSearchResponse> trsearchByTrid(String tid, String trid) {
        StringBuilder sb = new StringBuilder();
        sb.append(AmapConfigConstants.TERMINAL_TRSEARCH);
        sb.append("?");
        sb.append("key=" + amapKey);
        sb.append("&");
        sb.append("sid=" + amapSid);
        sb.append("&");
        sb.append("tid=" + tid);
        sb.append("&");
        sb.append("trid=" + trid);
        System.out.println("高德地图查询轨迹结果请求：" + sb.toString());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(sb.toString(), String.class);
        System.out.println("高德地图查询轨迹结果相应：" + forEntity.getBody());
        JSONObject result = JSONObject.fromObject(forEntity.getBody());
        JSONObject data = result.getJSONObject("data");
        int counts = data.getInt("counts");
        if (counts == 0){
            return ResponseResult.fail("当前车辆的轨迹尚无轨迹信息");
        }
        JSONArray tracks = data.getJSONArray("tracks");
        long driveMile = 0l;
        long driveTime = 0l;
        for (int i = 0; i < tracks.size(); i++) {
            JSONObject jsonObject = tracks.getJSONObject(i);
            long distance = jsonObject.getLong("distance");
            driveMile = driveMile + distance;

            long time = jsonObject.getLong("time");
            time = time / (1000 * 60);
            driveTime = driveTime + time;

        }
        TrSearchResponse trSearchResponse = new TrSearchResponse();
        trSearchResponse.setDriveMile(driveMile);
        trSearchResponse.setDriveTime(driveTime);
        return ResponseResult.success(trSearchResponse);
    }
}
