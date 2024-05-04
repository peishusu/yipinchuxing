package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-18 12:01
 **/
@Service
@Slf4j
public class MapDicDistrictClient {
    @Value("${aMap.key}")
    private String aMapKey;

    @Autowired
    private RestTemplate restTemplate;

    public String initDicDistrict(String keywords){
        //拼接用于调用地图区域字典的url
        //cc312500ddf7007c278c49f6cf5dcd00
        StringBuilder sb = new StringBuilder();
        sb.append(AmapConfigConstants.DISTINCT_URL);
        sb.append("?");
        sb.append("keywords=");
        sb.append(keywords);
        sb.append("&");
        sb.append("subdistrict=3");
        sb.append("&");
        sb.append("key=");
        sb.append(aMapKey);
        log.info("远程要调用的api：" + sb.toString());

        //调用高德地图的远程api获取所有地区区域
        ResponseEntity<String> forEntity = restTemplate.getForEntity(sb.toString(), String.class);
        String mapScopeContent = forEntity.getBody();


        //返回结果
        return mapScopeContent;

    }
}
