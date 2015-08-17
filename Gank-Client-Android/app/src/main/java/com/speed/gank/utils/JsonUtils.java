package com.speed.gank.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.speed.gank.models.CategoryModel;
import com.speed.gank.models.GankModel;
import com.speed.gank.models.ResponseModel;

public class JsonUtils {

    public static ResponseModel json2ResponseModel(String string) {
    	ResponseModel model = new ResponseModel();
    	try {
			JSONObject json = new JSONObject(string);
			model.category = json.optString("category");
			model.error = json.optBoolean("error");
			model.results = json.optString("results");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return model;
    }
	
    public static CategoryModel json2CategoryModel(String string) {
    	CategoryModel model = new CategoryModel();
    	try {
			JSONObject json = new JSONObject(string);
			model.Android = json.optString("Android");
			model.iOS = json.optString("iOS");
			model.福利 = json.optString("福利");
			model.拓展资源 = json.optString("拓展资源");
			model.休息视频 = json.optString("休息视频");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return model;
    }
    
    public static List<GankModel> json2GankModels(String string) {
    	List<GankModel> list = new ArrayList<GankModel>();
    	try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				GankModel model = new GankModel();
				model.createdAt = json.optString("createdAt");
				model.desc = json.optString("desc");
				model.objectId = json.optString("objectId");
				model.type = json.optString("type");
				model.updateAt = json.optString("updateAt");
				model.url = json.optString("url");
				model.used = json.optBoolean("used");
				model.who = json.optString("who");
				list.add(model);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return list;
    }
    
    public static Map<String, List<GankModel>> getCurrentData(String string) {
    	Map<String, List<GankModel>> map = new HashMap<String, List<GankModel>>();
    	ResponseModel responseModel = json2ResponseModel(string);
    	CategoryModel categoryModel = json2CategoryModel(responseModel.results);
    	map.put("Android", json2GankModels(categoryModel.Android));
    	map.put("iOS", json2GankModels(categoryModel.iOS));
    	map.put("福利", json2GankModels(categoryModel.福利));
    	map.put("拓展资源", json2GankModels(categoryModel.拓展资源));
    	map.put("休息视频", json2GankModels(categoryModel.休息视频));
    	return map;
    }
    
    public static List<GankModel> json2AndroidGanks(String string) {
    	ResponseModel responseModel = json2ResponseModel(string);
    	if(responseModel.error){
    		return null;
    	} else {
    		List<GankModel> list = json2GankModels(responseModel.results);
        	return list;
    	}
    }
    
	
}
