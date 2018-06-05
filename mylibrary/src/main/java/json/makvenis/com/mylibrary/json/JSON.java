package json.makvenis.com.mylibrary.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class JSON {


    /**
     * @ 解释 使用于当只有一层的时候 jsonArray -  jsonObject
     * @param mJson 传递过来的原始参数
     * @param keyString 需要解析的键值对关系
     */
    public static List<Map<String,String>> GetJson(String mJson, String[] keyString){
        if(mJson == null || keyString == null){
            return new ArrayList<>();
        }

        try {
            List<Map<String,String>> data = new ArrayList<>();

            //便利所有的键
            List<String> mKey = new ArrayList<>();
            for (String str:keyString) {
                mKey.add(str);
            }

            JSONArray arr = new JSONArray(mJson);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject object=arr.getJSONObject(i);

                Map<String, String> map=new HashMap<>();
                for (int j = 0; j < mKey.size(); j++) {
                    map.put(mKey.get(j), object.optString(mKey.get(j)));
                }
                data.add(map);
            }
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new ArrayList<>();
    }


    /**
     * {@linkplain=Android} 适应与当只有一层Object的时候
     * 使用此方法
     * @param json
     * @param key string[]{}
     * @return 返回的类型  Map<String, Object>
     */
    public static Map<String, Object> getObjectJson(String json,String[] key) {
        try {
            if(json == null || key.length == 0)
                return new HashMap<>();
            //返回的集合
            Map<String, Object> data=new HashMap<>();
            JSONObject object=new JSONObject(json);
            List<String> mKey=new ArrayList<>();
            for (int i = 0; i < key.length; i++) {
                mKey.add(key[i]);
            }

            for (int i = 0; i < mKey.size(); i++) {
                data.put(mKey.get(i), object.get(mKey.get(i)));
            }
            if(data.size() != 0)
                return data;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
