package com.example.projectjavaside;

import com.example.projectjavaside.jsonReady;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class jsonThread extends Thread{
    public JSONParser parser = new JSONParser();
    public jsonReady item;
    public jsonThread(jsonReady item)
    {
    this.item = item;

    }
    public jsonReady getItem()
    {
        return this.item;
    }

    public void display()
    {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        for (Hashtable<String,String> hash:this.item.list) {
            System.out.println(hash.get("Name")+" "+hash.get("Price")+" "+hash.get("Link"));
        }
    }
    @Override
    public void run(){
        try
        {
            jsonPacked pack = new jsonPacked("","","");
            Object obj = parser.parse(new FileReader(item.path));
            JSONArray jsonArray = (JSONArray) obj;
            for (Object data:  jsonArray) {
                Hashtable<String,String> addHash = new Hashtable<String,String>();
                JSONObject mined = (JSONObject) data;
                pack.title =(String) mined.get("Title");
                pack.price =(String) mined.get("Price");
                pack.link  =(String) mined.get("Link");
                addHash.put("Name",pack.title);
                addHash.put("Price",pack.price);
                addHash.put("Link",pack.link);
                this.item.list.add(addHash);

            }

        }
        catch(FileNotFoundException e) { e.printStackTrace();}
        catch(IOException e) { e.printStackTrace();}
        catch(ParseException e) { e.printStackTrace();}
        catch(Exception e) { e.printStackTrace();}
    }
}
