package com.astart;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Iterator;

public class Main {
    public void initMapInfo(String[] corrdinate, int type){
     return;
    }
    public void getMassage(String in){
        JSONObject jsonInput = JSONObject.fromObject(in);
        if(jsonInput.get("type").equals("corrdinate")){
            JSONArray playerObjArray = jsonInput.getJSONArray("pos");
            for (int i = 0; i < playerObjArray.size(); i++) {
                String[] playerCorrdinate = playerObjArray.getJSONObject(i).getString("key").split(",");
                this.initMapInfo(playerCorrdinate,9);
            }
            JSONArray obstacleObjArray = jsonInput.getJSONArray("obstaclePosition");
            for (int i = 0; i < obstacleObjArray.size(); i++) {
                String[] obstacle = obstacleObjArray.getString(i).split(",");
                this.initMapInfo(obstacle,1);
            }
        }

        if(jsonInput.get("type").equals("posion")){
            JSONObject cityObj = jsonInput.getJSONObject("city");
            Iterator iterator = cityObj.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                String[] cityCorrdinate = key.split(",");
                this.initMapInfo(cityCorrdinate,Integer.parseInt(cityObj.getString(key)));
            }
            JSONObject treasuresObj = jsonInput.getJSONObject("treasures");
            iterator = treasuresObj.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                String[] treasuresObjCorrdinate = key.split(",");
                this.initMapInfo(treasuresObjCorrdinate,Integer.parseInt(treasuresObj.getString(key)));
            }
        }

        if (jsonInput.get("type").equals("info")){
            System.out.println("-----info------");
        }
        if (jsonInput.get("type").equals("issue")){
            System.out.println("-----issue------");
        }
    }
    public static void main(String[] args) {
        String corrdinate = "";
        JSONObject jsonObstacle = JSONObject.fromObject(corrdinate);
        JSONArray obstacleObjectArray = jsonObstacle.getJSONArray("obstaclePosition");
        JSONArray playerObjectArray = jsonObstacle.getJSONArray("pos");

        JSONObject jsonInfo = JSONObject.fromObject("info");
        JSONArray
    }
}
