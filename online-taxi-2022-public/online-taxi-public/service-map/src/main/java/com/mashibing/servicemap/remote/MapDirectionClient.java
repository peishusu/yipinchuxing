package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.PrintStream;

/**
 * @program: online-taxi-public
 * @description: 这个类也类似于远程调用，调用高德地图的乘车规划api
 * @author: lydms
 * @create: 2024-03-17 15:08
 **/
@Service
@Slf4j
public class MapDirectionClient {

    @Value("${aMap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Description: 远程调用高德地图的api
     * @Param: [depLongitude, depLatitude, destLongitude, destLatitude]
     * @return: com.mashibing.internalcommon.response.DirectionResponse
     * @Author: JiLaiYa
     * @Date: 2024/3/17
     */
    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //组装要远程调用url
        //格式如下：key=cc312500ddf7007c278c49f6cf5dcd00
        StringBuilder sb = new StringBuilder(AmapConfigConstants.DIRECTION_URL);
        sb.append("?");
        sb.append("origin=" + depLongitude + "," + depLatitude);
        sb.append("&");
        sb.append("destination=" + destLongitude + "," + destLatitude);
        sb.append("&");
        sb.append("extensions=all");
        sb.append("&");
        sb.append("output=json");
        sb.append("&");
        sb.append("key=" + amapKey);

        log.info(sb.toString());


        //调用高德接口
        ResponseEntity<String> forEntity = restTemplate.getForEntity(sb.toString(), String.class);
        String jsonBody = forEntity.getBody();
        log.info("高德地图路径规划:" + jsonBody);
        //解析高德接口传送过来的json
        DirectionResponse directionResponse = parseDirectionEntity(jsonBody);

        return directionResponse;
    }

    /**
     * @Description: 解析高德接口传送过来的json
     * @Param: [jsonBody]
     * @return: com.mashibing.internalcommon.response.DirectionResponse
     * @Author: JiLaiYa
     * @Date: 2024/3/17
     */
    private DirectionResponse parseDirectionEntity(String jsonBody) {
        DirectionResponse directionResponse = null;
        try {
            JSONObject res = JSONObject.fromObject(jsonBody);
            if(res.has(AmapConfigConstants.STATUS)){
                int status = res.getInt(AmapConfigConstants.STATUS);
                if(status == 1){
                    if(res.has(AmapConfigConstants.ROUTE)){
                        JSONObject routeObject = res.getJSONObject(AmapConfigConstants.ROUTE);
                        JSONArray pathsArray = routeObject.getJSONArray(AmapConfigConstants.PATHS);
                        JSONObject pathObject = pathsArray.getJSONObject(0);

                        directionResponse = new DirectionResponse();

                        if(pathObject.has(AmapConfigConstants.DISTANCE)){
                            int distance = pathObject.getInt(AmapConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }

                        if(pathObject.has(AmapConfigConstants.DURATION)){
                            int duration = pathObject.getInt(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);

                        }


                    }
                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return directionResponse;
    }
}
