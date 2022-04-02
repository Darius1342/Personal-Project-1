package com.example.projectjavaside;
import java.util.*;
public class jsonReady {
    public ArrayList<Hashtable<String,String>> list;
    public String path;

    public jsonReady(String link,ArrayList<Hashtable<String,String>> list)
    {
        this.list = list;
        this.path = link;
    }
}
