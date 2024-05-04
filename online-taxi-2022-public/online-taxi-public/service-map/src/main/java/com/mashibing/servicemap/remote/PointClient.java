package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointDTO;
import com.mashibing.internalcommon.request.PointRequest;
import com.mashibing.internalcommon.response.TrackResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-21 20:36
 **/
@Service
public class PointClient {
    @Value("${aMap.key}")
    private String amapKey;

    @Value("${aMap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    /**
    * @Description: 可以使用传递多个轨迹点
    * @Param: [pointRequest]
    * @return: com.mashibing.internalcommon.dto.ResponseResult
    * @Author: JiLaiYa
    * @Date: 2024/3/28
    */

    public ResponseResult upload(PointRequest pointRequest) {
        // &key=<用户的key>
        // 拼装请求的url
        //https://tsapi.amap.com/v1/track/point/upload?key=cc312500ddf7007c278c49f6cf5dcd00&sid=1023213&tid=865293116&
        // trid=340&points=[{"location":"116.397439,39.90937",    "locatetime":1544176935000},    {"location":"116.397451,39.90949",    "locatetime":1544176945000}]
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.POINT_UPLOAD);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+amapSid);
        url.append("&");
        url.append("tid="+pointRequest.getTid());
        url.append("&");
        url.append("trid="+pointRequest.getTrid());
        url.append("&");
        url.append("points=");
        PointDTO[] points = pointRequest.getPoints();
        url.append("%5B");//[
        for (int i = 0; i < points.length; i++) {
            PointDTO p = points[i];
            url.append("%7B");//{
            String locatetime = p.getLocatetime();
            String location = p.getLocation();
            url.append("%22location%22");//"location"
            url.append("%3A");//:
            url.append("%22"+location+"%22");//"116.397439,39.90937"
            url.append("%2C");//,

            url.append("%22locatetime%22");//"locatetime"
            url.append("%3A");//:
            url.append(locatetime);//1544176935000

            url.append("%7D");//}
            if (i != points.length - 1){
                url.append("%2C");
            }
        }

        url.append("%5D");//]

        System.out.println("上传位置请求："+url.toString());
        System.out.println(URI.create(url.toString()));
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);
        System.out.println("上传位置响应："+stringResponseEntity.getBody());

        return ResponseResult.success();
    }
}
