package com.summary.biz.authority;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * mybatis plus 代码自动生成器
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class MybatisPlusAutoGenerator {
    public static void main(String[] args) {

        // 需要生成的表
        String[] tables = {"auth_admin","auth_admin_role","auth_menu","auth_role","auth_role_menu"};
        // 模块名称
        String module = "authority";

        String projectPath = System.getProperty("user.dir") + "\\summary-business\\summary-" + module;
        System.out.println(projectPath);

        /**  **/
        FastAutoGenerator.create("jdbc:mysql://192.168.31.100:13306/summary-" + module + "?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("myabtis-plus") // 设置作者
//                            .fileOverride()
                            .outputDir(projectPath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.summary.biz." + module) // 设置父包名
                            .entity("entity")
                            .service("service")
                            .mapper("mapper")
                            .pathInfo(new HashMap<OutputFile, String>(5) {{
                                this.put(OutputFile.entity, projectPath + "\\src\\main\\java\\com\\summary\\biz\\" + module + "\\entity");
                                this.put(OutputFile.service, projectPath + "\\src\\main\\java\\com\\summary\\biz\\" + module + "\\service");
                                this.put(OutputFile.serviceImpl, projectPath + "\\src\\main\\java\\com\\summary\\biz\\" + module + "\\service\\impl");
                                this.put(OutputFile.mapper, projectPath + "\\src\\main\\java\\com\\summary\\biz\\" + module + "\\mapper");
                                this.put(OutputFile.controller, projectPath + "\\src\\main\\java\\com\\summary\\biz\\" + module + "\\rest");
                                this.put(OutputFile.xml, projectPath + "/src/main/resources/mapper");
                            }});
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .addTablePrefix("auth_","sys_")
                            .entityBuilder()
                            .superClass("com.summary.component.repository.base.BaseDO")
                            .enableLombok()
                            .enableActiveRecord()
                            .formatFileName("%sDO")
                            .addSuperEntityColumns("version", "create_time", "update_time", "delete_flag")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .enableMapperAnnotation()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper");
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                    // DTO
                    customFile.put("entity.java", "/templates/entity.java.ftl");
                    consumer.customFile(customFile);
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}
