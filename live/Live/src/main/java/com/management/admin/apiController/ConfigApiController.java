/***
 * @pName Live
 * @name ConfigApiController
 * @user HongWei
 * @date 2019/1/26
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.resp.DictionaryEntity;
import com.management.admin.entity.template.DataDictionary;
import com.management.admin.entity.template.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/config")
public class ConfigApiController {
    @Autowired
    private IDictionaryService dictionaryService;

    @GetMapping("reload")
    public JsonResult reload(){
        List<DictionaryEntity> dictionaryList = dictionaryService.getList();
        Map<String,DictionaryEntity> dataDictionaryList = new HashMap<>();
        dictionaryList.forEach(dictionary -> dataDictionaryList.put(dictionary.getKey() , dictionary));
        DataDictionary.DATA_DICTIONARY = dataDictionaryList;
        return JsonResult.successful();
    }
}
