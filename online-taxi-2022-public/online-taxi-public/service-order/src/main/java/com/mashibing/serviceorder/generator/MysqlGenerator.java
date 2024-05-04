package com.mashibing.serviceorder.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @program: online-taxi-public
 * @description: 根据数据库表反向生成代码工具类
 * @author: lydms
 * @create: 2024-03-19 20:35
 **/
public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-order?characterEncoding=utf-8&serverTimezone=GMT%2B8","root","106331")
                //设置全局配置：作者以及覆盖生成文件输出目录
                .globalConfig(builder -> {
                    builder.author("JiLaiYa").fileOverride().outputDir("E:\\code\\project\\online-taxi-2022-public\\online-taxi-public\\service-order\\src\\main\\java");
                })
                //配置包的属性:父包和mapper文件地址
                .packageConfig(builder -> {
                    builder.parent("com.mashibing.serviceorder").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "E:\\code\\project\\online-taxi-2022-public\\online-taxi-public\\service-order\\src\\main\\java\\com\\mashibing\\serviceorder\\mapper"));
                })
                //设置生成策略:表名
                .strategyConfig(builder -> {
                    builder.addInclude("order_info");
                })
                //使用模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
