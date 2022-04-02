package com.example.projectjavaside;

public class readyItems implements Price,Title,Link {
    public String title, price,link;

    readyItems(String title,String price,String link)
    {
        this.title = title;
        this.price = price;
        this.link = link;
    }

    @Override
    public String getLink() {
        return this.title;
    }

    @Override
    public String getPrice() {
        return this.price;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
