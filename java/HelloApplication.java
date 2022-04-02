package com.example.projectjavaside;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class HelloApplication extends Application{

    Stage window;

    Scene defaultScene;
    Scene sceneBooksExpress, sceneEmag, sceneOlx;
    // Defaults
    Button buttonBooksExpress = new Button("Books Express");
    Button buttonEmag = new Button("Emag");
    Button buttonOlx = new Button("Olx");
    // BooksExpress
    Button stayExpress = new Button("Books Express");
    Button moveToEmag1 = new Button("Emag");
    Button moveToOlx1 = new Button("Olx");
    // Emag
    Button stayEmag = new Button("Emag");
    Button moveToBooksExpress1 = new Button("Books Express");
    Button moveToOlx2 = new Button("Olx");
    //Olx
    Button stayOlx = new Button("Olx");
    Button moveToBooksExpress2 = new Button("Books Express");
    Button moveToEmag2 = new Button("Emag");


    @Override
    public void start(Stage stage) throws IOException {
        try{
            // DATABASE SETUP


            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc-scraper","root","admin");

            Statement statementClearAll = connection.createStatement();
            Statement statementLoadAllData = connection.createStatement();
            Statement statementPrintData = connection.createStatement();
            statementClearAll.executeUpdate("DELETE from scrapedtable");
            int id = -100;
            ResultSet printAllData;



            // Thread initialisation and running
            ArrayList<Hashtable<String,String>> booksExpressList = new ArrayList<Hashtable<String,String>>();
            ArrayList<Hashtable<String,String>> emagList = new ArrayList<Hashtable<String,String>>();
            ArrayList<Hashtable<String,String>> olxList = new ArrayList<Hashtable<String,String>>();

            jsonReady booksExpress = new jsonReady("C:\\\\Users\\\\Darius\\\\Desktop\\\\Individual Project\\\\Python\\\\scrapedBooksExpress.json",booksExpressList);
            jsonThread threadBooksExpress = new jsonThread(booksExpress);

            jsonReady emag = new jsonReady("C:\\\\Users\\\\Darius\\\\Desktop\\\\Individual Project\\\\Python\\\\scrapedEmag.json",emagList);
            jsonThread threadEmag = new jsonThread(emag);

            jsonReady olx = new jsonReady("C:\\\\Users\\\\Darius\\\\Desktop\\\\Individual Project\\\\Python\\\\scrapedOlx.json",olxList);
            jsonThread threadOlx = new jsonThread(olx);

            threadBooksExpress.run();
            threadEmag.run();
            threadOlx.run();
            // Getting the values of the items into Arrays of Dictionaries.
            /* TBA to a database + DISPLAY IT ON GUI */
            booksExpressList = threadBooksExpress.getItem().list;
            emagList = threadEmag.getItem().list;
            olxList = threadOlx.getItem().list;
/*
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/
            // GUI PART
            window = stage;
            // Default Scenery setup.
            String defaultTitle="Scraped Data Visualiser"; window.setTitle(defaultTitle);
            GridPane defaultSceneLayout = new GridPane();
            defaultSceneLayout.add(new Label("Welcome to the data scraper!\n Choose your option."),0,0);
            defaultSceneLayout.addRow(1,new Label(" "),new Label(" "),new Label(" "));
            defaultSceneLayout.add(buttonBooksExpress,1,2);
            defaultSceneLayout.add(buttonEmag,2,2);
            defaultSceneLayout.add(buttonOlx,3,2);
            defaultSceneLayout.setVgap(5);
            defaultSceneLayout.setHgap(5);
            defaultScene = new Scene(defaultSceneLayout,1920,1080);

            window.setScene(defaultScene);

            //  Scene Books Express setup
            GridPane booksExpressLayout = new GridPane();
            booksExpressLayout.add(new Label("Books Express"),1,0);
            booksExpressLayout.addRow(1,new Label(" "),new Label(" "),new Label(" "));
            booksExpressLayout.add(stayExpress,1,2);
            booksExpressLayout.add(moveToEmag1,2,2);
            booksExpressLayout.add(moveToOlx1,3,2);
            booksExpressLayout.setHgap(5);
            booksExpressLayout.setVgap(5);
            for(int i = 0;i<booksExpressList.size(); i = i +1)
            {
                id = id + 1;
                Hashtable<String,String> tempHash = booksExpressList.get(i);
                readyItems items = new readyItems(tempHash.get("Name").replace("'","`"),tempHash.get("Price"),tempHash.get("Link"));
                TextFlow flow = new TextFlow(
                        new Text("Link"), new Hyperlink(items.link)
                );


                String query = String.format("INSERT INTO scrapedtable (ID,Title,Price,Link) VALUES (%d,'%s','%s','%s');",id,items.title,items.price,items.link);
               int count = statementLoadAllData.executeUpdate(query);
                booksExpressLayout.add(new Label(items.title),1,i+3);
                booksExpressLayout.add(new Label(items.price),2,i+3);
                booksExpressLayout.add(flow,3,i+3);


            }
            sceneBooksExpress = new Scene(booksExpressLayout,1920,1080);

            // Scene Emag setup
            GridPane emagLayout = new GridPane();
            emagLayout.add(new Label("Emag"),1,0);
            emagLayout.addRow(1,new Label(" "),new Label(" "),new Label(" "));
            emagLayout.add(moveToBooksExpress1,1,2);
            emagLayout.add(stayEmag,2,2);
            emagLayout.add(moveToOlx2,3,2);
            emagLayout.setHgap(5);
            emagLayout.setVgap(5);
            for(int i = 0;i<emagList.size(); i = i +1)
            {
                id = id + 1;
                Hashtable<String,String> tempHash = emagList.get(i);
                readyItems items = new readyItems(tempHash.get("Name").replace("'","`"),tempHash.get("Price"),tempHash.get("Link"));
                TextFlow flow = new TextFlow(
                        new Text("Link"), new Hyperlink(items.link)
                );

                String query = String.format("INSERT INTO scrapedtable (ID,Title,Price,Link) VALUES (%d,'%s','%s','%s');",id,items.title,items.price,items.link);
              int count = statementLoadAllData.executeUpdate(query);


                emagLayout.add(new Label(items.title),1,i+3);
                emagLayout.add(new Label(items.price),2,i+3);
                emagLayout.add(flow,3,i+3);


            }
            sceneEmag = new Scene(emagLayout,1920,1080);

            //Scene Olx setup
            GridPane olxLayout = new GridPane();
            olxLayout.add(new Label("Olx"),1,0);
            olxLayout.addRow(1,new Label(" "),new Label(" "),new Label(" "));
            olxLayout.add(moveToBooksExpress2,1,2);
            olxLayout.add(moveToEmag2,2,2);
            olxLayout.add(stayOlx,3,2);
            olxLayout.setHgap(5);
            olxLayout.setVgap(5);
            for(int i = 0;i<olxList.size(); i = i +1)
            {
                id = id + 1;
                Hashtable<String,String> tempHash = olxList.get(i);
                readyItems items = new readyItems(tempHash.get("Name").replace("'","`"),tempHash.get("Price"),tempHash.get("Link"));

                TextFlow flow = new TextFlow(
                        new Text("Link"), new Hyperlink(items.link)
                );

                String query = String.format("INSERT INTO scrapedtable (ID,Title,Price,Link) VALUES (%d,'%s','%s','%s');",id,items.title,items.price,items.link);
              int count = statementLoadAllData.executeUpdate(query);
                olxLayout.add(new Label(items.title),1,i+3);
                olxLayout.add(new Label(items.price),2,i+3);
                olxLayout.add(flow,3,i+3);

            }

            sceneOlx = new Scene(olxLayout,1920,1080);

            // Button Setup

            // Books Express
            buttonBooksExpress.setOnAction(e -> {
                window.setScene(sceneBooksExpress);
                window.setTitle("Books Express");

            });
            stayExpress.setOnAction(e -> {
                window.setScene(sceneBooksExpress);
                window.setTitle("Books Express");

            });
            moveToBooksExpress1.setOnAction(e -> {
                window.setScene(sceneBooksExpress);
                window.setTitle("Books Express");

            });
            moveToBooksExpress2.setOnAction(e -> {
                window.setScene(sceneBooksExpress);
                window.setTitle("Books Express");

            });
            // Emag
            buttonEmag.setOnAction(e -> {
                window.setScene(sceneEmag);
                window.setTitle("Emag");

            });
            stayEmag.setOnAction(e -> {
                window.setScene(sceneEmag);
                window.setTitle("Emag");

            });
            moveToEmag1.setOnAction(e -> {
                window.setScene(sceneEmag);
                window.setTitle("Emag");

            });
            moveToEmag2.setOnAction(e -> {
                window.setScene(sceneEmag);
                window.setTitle("Emag");

            });

            // Olx
            buttonOlx.setOnAction(e -> {
                window.setScene(sceneOlx);
                window.setTitle("Olx");

            });
            stayOlx.setOnAction(e -> {
                window.setScene(sceneOlx);
                window.setTitle("Olx");

            });

            moveToOlx1.setOnAction(e -> {
                window.setScene(sceneOlx);
                window.setTitle("Olx");

            });

            moveToOlx2.setOnAction(e -> {
                window.setScene(sceneOlx);
                window.setTitle("Olx");

            });

            window.show();

           printAllData = statementPrintData.executeQuery("select * from scrapedtable");
            while(printAllData.next())
            {
                System.out.println(printAllData.getInt(id)+" "+printAllData.getString("Title")+" "+printAllData.getString("Price")+" "
                        +printAllData.getString("Link"));
            }
        }catch (Exception e){ e.printStackTrace();}
    }

    public static void main(String[] args) {launch();}



}
