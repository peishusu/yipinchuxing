package com.mashibing.servicemap.service.impl;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import com.mashibing.servicemap.remote.MapDicDistrictClient;
import com.mashibing.servicemap.remote.MapDirectionClient;
import com.mashibing.servicemap.service.DicDistrictService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-18 12:27
 **/
@Service
@Slf4j
public class DicDistrictServiceImpl implements DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @Override
    public ResponseResult initDicDistrict(String keywords) {
        //调用远程服务，获得地图区域的全部信息内容
        String initDicDistrict = mapDicDistrictClient.initDicDistrict(keywords);
        log.info(initDicDistrict);
        //解析传送过来的全部地图信息内容
        JSONObject disDistrictjsonObject = JSONObject.fromObject(initDicDistrict);
        if (disDistrictjsonObject.getInt(AmapConfigConstants.STATUS) != 1) {
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getMessage());
        }
        //到这里代表执行的都是成功的结果
        JSONArray districtsJsonArray = disDistrictjsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
        for (int i = 0; i < districtsJsonArray.size(); i++) {
            JSONObject countryJsonObject = districtsJsonArray.getJSONObject(i);
            String countryAddressCode = countryJsonObject.getString(AmapConfigConstants.ADCODE);
            String countryAddressName = countryJsonObject.getString(AmapConfigConstants.NAME);
            String countryParentAddressCode = "0";
            String countryLevel = countryJsonObject.getString(AmapConfigConstants.LEVEL);

            //将国家插入到数据库中去
            insertDicDistrict(countryAddressCode, countryAddressName, countryLevel, countryParentAddressCode);

            //获取下一层--省
            JSONArray provinceJsonArray = countryJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
            for (int p = 0; p < provinceJsonArray.size(); p++) {
                JSONObject provinceJsonObject = provinceJsonArray.getJSONObject(p);
                String provinceAddressCode = provinceJsonObject.getString(AmapConfigConstants.ADCODE);
                String provinceAddressName = provinceJsonObject.getString(AmapConfigConstants.NAME);
                String provinceParentAddressCode = countryAddressCode;
                String provinceLevel = provinceJsonObject.getString(AmapConfigConstants.LEVEL);
                //将省级的地图字典加入到数据库中
                insertDicDistrict(provinceAddressCode, provinceAddressName, provinceLevel, provinceParentAddressCode);

                //获取下一层--市
                JSONArray cityJsonArray = provinceJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                for (int c = 0; c < cityJsonArray.size(); c++) {
                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(c);
                    String cityAddressCode = cityJsonObject.getString(AmapConfigConstants.ADCODE);
                    String cityAddressName = cityJsonObject.getString(AmapConfigConstants.NAME);
                    String cityParentAddressCode = provinceAddressCode;
                    String cityLevel = cityJsonObject.getString(AmapConfigConstants.LEVEL);
                    //将市级的地图字典加入到数据库中
                    insertDicDistrict(cityAddressCode, cityAddressName, cityLevel, cityParentAddressCode);

                    //获取下一层--县
                    JSONArray districtJsonArray = cityJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                    for (int d = 0; d < districtJsonArray.size(); d++) {
                        JSONObject districtJsonObject = districtJsonArray.getJSONObject(d);
                        String districtAddressCode = districtJsonObject.getString(AmapConfigConstants.ADCODE);
                        String districtAddressName = districtJsonObject.getString(AmapConfigConstants.NAME);
                        String districtParentAddressCode = cityAddressCode;
                        String districtLevel = districtJsonObject.getString(AmapConfigConstants.LEVEL);
                        if("street".equals(districtLevel.trim())){
                            continue;
                        }
                        //将市级的地图字典加入到数据库中
                        insertDicDistrict(districtAddressCode, districtAddressName, districtLevel, districtParentAddressCode);
                    }
                }
            }

        }


        return ResponseResult.success();
    }


    /**
     * @Description: 将该地区添加到数据库中去
     * @Param: [addressCode, addressName, level, parentAddressCode]
     * @return: void
     * @Author: JiLaiYa
     * @Date: 2024/3/18
     */
    private void insertDicDistrict(String addressCode, String addressName, String level, String parentAddressCode) {
        DicDistrict dicDistrict = new DicDistrict(addressCode, addressName, parentAddressCode, generateLevel(level));
        dicDistrictMapper.insert(dicDistrict);
    }


    /**
     * @Description: 将不同地区区域级别转化为对应的数字0 1 2 3
     * @Param: [level]
     * @return: java.lang.Integer
     * @Author: JiLaiYa
     * @Date: 2024/3/18
     */

    private Integer generateLevel(String level) {
        Integer levelInt = 0;
        if ("country".equals(level.trim())) {
            levelInt = 0;
        } else if ("province".equals(level.trim())) {
            levelInt = 1;
        } else if ("city".equals(level.trim())) {
            levelInt = 2;
        } else {
            levelInt = 3;
        }
        return levelInt;
    }
}
