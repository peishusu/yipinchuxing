package com.mashibing.servicessepush.controller;

import com.mashibing.internalcommon.utils.SsePrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-26 19:47
 **/
@RestController
@Slf4j
public class SseController {

    private static Map<String, SseEmitter> sseEmitterMap = new HashMap<>();


    /**
     * @Description: 建立连接
     * @Param: [userId, identity]
     * @return: org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     * @Author: JiLaiYa
     * @Date: 2024/3/26
     */
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId, @RequestParam String identity) {
        SseEmitter sseEmitter = new SseEmitter(0l);
        String mapKey = SsePrefixUtils.generatorSseKey(userId, identity);
        sseEmitterMap.put(mapKey, sseEmitter);
        return sseEmitter;
    }


    /**
     * @Description: 发送消息
     * @Param: [userId 用户id, identity 身份类型, content 消息内容]
     * @return: java.lang.String
     * @Author: JiLaiYa
     * @Date: 2024/3/26
     */

    @GetMapping("/push")
    public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content) {
        log.info("用户ID:" + userId + ",身份类型：" + identity);
        String mapKey = SsePrefixUtils.generatorSseKey(userId, identity);
        try {
            if (sseEmitterMap.containsKey(mapKey)) {
                log.info("1");
                sseEmitterMap.get(mapKey).send(content);
            } else {
                log.info("2");
                return "此用户不存在,推送失败";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "给用户：" + mapKey + "，发送了消息：" + content;
    }


    /**
     * @Description: 关闭连接
     * @Param: [userId, identity]
     * @return: java.lang.String
     * @Author: JiLaiYa
     * @Date: 2024/3/26
     */
    @GetMapping("/close")
    public String close(@RequestParam Long userId, @RequestParam String identity) {
        log.info("userId:" + userId + ",identity:" + identity) ;
        String mapKey = SsePrefixUtils.generatorSseKey(userId, identity);
        if (sseEmitterMap.containsKey(mapKey)) {
            sseEmitterMap.remove(mapKey);
        }
        return "close success!";
    }
}
