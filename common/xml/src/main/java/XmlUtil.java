import org.json.JSONObject;
import org.json.XML;

/***
 * @pName xml
 * @name XmlUtil
 * @user HongWei
 * @date 2019/1/5
 * @desc
 */

public class XmlUtil {
    /**
     *
     * @param xmlResponse
     * @return
     */
    public static String toJson(String xmlResponse){
        //将xml转为json
        JSONObject xmlJSONObj = XML.toJSONObject(xmlResponse);
        //设置缩进
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
        //输出格式化后的json
        System.out.println(jsonPrettyPrintString);
        return jsonPrettyPrintString;
    }
}
