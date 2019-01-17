/***
 * @pName mi-ocr-web-app
 * @name ThymeleafContextConfiguration
 * @user DF
 * @date 2018/7/11
 * @desc
 */
package com.management.admin.config;


import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.resp.DictionaryEntity;
import com.management.admin.exception.InfoException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Configuration
@ComponentScan("com.management.admin.config")
public class ThymeleafContextConfiguration {

    @Resource
    private IDictionaryService dictionaryService;

    /**
     * 配置全局变量
     * @param viewResolver
     */
    @Resource
    private void configureThymeleafVars(ThymeleafViewResolver viewResolver) {
        if(viewResolver == null) return;

        Map<String, Object> vars = new HashMap<>();

        // 提取前端模板引擎静态配置信息
        extractTemplateStaticVars(vars);

        // 提取域名列表中的信息
        extractDomainManager(vars);

        // 提取多页面动态配置信息
        extractMultiPageSettings(vars);

        viewResolver.setStaticVariables(vars);
    }

    /**
     * 提取多页面动态配置信息
     * @param vars
     */
    private void extractMultiPageSettings(Map<String, Object> vars) {
        vars.put("title","卫星直播");
        vars.put("keywords","#");
        vars.put("description","#");
        vars.put("brand","卫星直播");
        List<DictionaryEntity> list = dictionaryService.getList();
        if(!(list == null || list.isEmpty())){
            List<DictionaryEntity> collect = list.stream().filter(item -> item.getKey().contains("pc.")).collect(Collectors.toList());
            Optional<DictionaryEntity> footerHtmlOptional = collect.stream().filter(item -> item.getKey().equals("pc.footer.html")).findFirst();
            Optional<DictionaryEntity> footerAboutOptional = collect.stream().filter(item -> item.getKey().equals("pc.footer.about")).findFirst();
            Optional<DictionaryEntity> title = collect.stream().filter(item -> item.getKey().equals("pc.header.title")).findFirst();
            Optional<DictionaryEntity> keywords = collect.stream().filter(item -> item.getKey().equals("pc.header.keywords")).findFirst();
            Optional<DictionaryEntity> description = collect.stream().filter(item -> item.getKey().equals("pc.header.description")).findFirst();

            if(footerHtmlOptional.isPresent()){
                vars.put("footerHtml", footerHtmlOptional.get().getValue());
            }else{
                vars.put("footerHtml","");
            }

            if(footerAboutOptional.isPresent()){
                vars.put("footerAbout", footerAboutOptional.get().getValue());
            }else{
                vars.put("footerAbout","");
            }

            if(title.isPresent()){
                vars.put("title", title.get().getValue());
            }else{
                vars.put("title","卫星直播");
            }

            if(keywords.isPresent()){
                vars.put("keywords", keywords.get().getValue());
            }else{
                vars.put("keywords","卫星直播");
            }

            if(description.isPresent()){
                vars.put("description", description.get().getValue());
            }else{
                vars.put("description","卫星直播");
            }

        }
    }

    /**
     * 提取域名列表中的信息
     * @param vars
     */
    private void extractDomainManager(Map<String, Object> vars) {
        vars.put("domain_resource", "");
    }

    /**
     * 提取前端模板引擎静态配置信息
     * @param vars
     */
    private void extractTemplateStaticVars(Map<String, Object> vars) {
        vars.put("link","shared/link");
        vars.put("script","shared/script");
    }
}