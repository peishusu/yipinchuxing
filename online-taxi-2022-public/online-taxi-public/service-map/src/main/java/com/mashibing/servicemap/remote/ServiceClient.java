package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ServiceResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 16:29
 **/

@Service
public class ServiceClient {

    @Value("${aMap.key}")
    private String aMapKey;

    @Autowired
    private RestTemplate restTemplate;


    /** 
    * @Description: 调用高德地图创建服务的api
    * @Param: [name] 是要创建的服务名称
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/21
    */
    
    public ResponseResult add(String name){
        //https://tsapi.amap.com/v1/track/service/add
        StringBuilder sb = new StringBuilder();
        sb.append(AmapConfigConstants.SERVICE_ADD_URL);
        sb.append("?");
        sb.append("key=");
        sb.append(aMapKey);
        sb.append("&");
        sb.append("name=");
        sb.append(name);
        //调用高德 的远程创建服务
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sb.toString(), null, String.class);
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String sid = data.getString("sid");
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(sid);
        return ResponseResult.success(serviceResponse);


    }


}
