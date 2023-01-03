package com.modelbasedpart2;

import java.util.Random;
import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;

import java.io.IOException;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    boolean loggedIn = true, alertAdded = false, deleteAlerts = false, accessAlerts = false, postError = false, deleteError = false;
    public Pair<String,Integer> accessingAlerts(){
        loggedIn = true;
        accessAlerts = true;
        alertAdded = false;
        deleteAlerts = false;
        postError = false;
        deleteError = false;

        System.setProperty("webdriver.chrome.driver", "/Documents/UM/3rd year Sem1/CPS3230-Software Testing/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.marketalertum.com/Alerts/Login");
        WebElement searchField = driver.findElement(By.id("UserId"));
        searchField.sendKeys("46aba3d5-35a9-4850-b5c1-02824284c450");
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.submit();
        int noOfAlerts = driver.findElements(By.tagName("table")).size();
        String url = driver.getCurrentUrl();
        driver.quit();

        Pair<String,Integer> p = Pairs.from(url,noOfAlerts);
        return p;
    }

    public int postRequest() throws IOException {
        loggedIn = true;
        alertAdded = true;
        accessAlerts = false;
        deleteAlerts = false;
        postError = false;
        deleteError = false;

        JSONObject alertData = new JSONObject();
        alertData.put("alertType",6);
        alertData.put("heading","Jumper Windows 11 Laptop");
        alertData.put("description","Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD");
        alertData.put("url","https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth");
        alertData.put("imageURL","https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg");
        alertData.put("postedBy","46aba3d5-35a9-4850-b5c1-02824284c450");
        alertData.put("priceInCents",24999);

        //Setting up HTTP request connection + headers
        URL endpoint = new URL("https://api.marketalertum.com/Alert");
        HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String stringBody = alertData.toString();
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = stringBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        return con.getResponseCode();//Status code to check for status of api call
    }

    public int badPostRequest(){
        loggedIn = true;
        alertAdded = false;
        postError = true;
        accessAlerts = true;
        deleteAlerts = false;
        deleteError = false;

        int statusCode = 0;
        try {
            JSONObject alertData = new JSONObject();
            alertData.put("alertType", 6);
            alertData.put("heading", "Jumper Windows 11 Laptop");
            alertData.put("description", "Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD");
            alertData.put("url", "https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth");
            alertData.put("imageURL", "https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg");
            alertData.put("postedBy", "badCredentials");
            alertData.put("priceInCents", 24999);

            //Setting up HTTP request connection + headers
            URL endpoint = new URL("https://api.marketalertum.com/Alert");
            HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String stringBody = alertData.toString();
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = stringBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            statusCode = con.getResponseCode();//Status code to check for status of api call
        }catch(IOException e){
            System.out.println("Bad Post Request");
        }
        return statusCode;
    }

    public int deleteRequest() throws IOException{
        loggedIn = true;
        accessAlerts = false;
        deleteAlerts = true;
        alertAdded = false;
        postError = false;
        deleteError = false;

        URL endpoint = new URL("https://api.marketalertum.com/Alert?userId=" + "46aba3d5-35a9-4850-b5c1-02824284c450");
        HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(false);
        return con.getResponseCode();
    }

    public int badDeleteRequest(){
        loggedIn = true;
        deleteError = true;
        accessAlerts = false;
        alertAdded = false;
        deleteAlerts = false;
        postError = false;

        int statusCode = 0;
        try {
            URL endpoint = new URL("https://api.marketalertum.com/Alert?userId=" + "badCredentials");
            HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(false);
            statusCode = con.getResponseCode();
        }catch(IOException e){
            System.out.println("Bad Delete Request");
        }
        return statusCode;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean isAccessAlerts(){
        return accessAlerts;
    }

    public boolean isAlertAdded(){
        return alertAdded;
    }

    public boolean isDeleteAlerts(){
        return deleteAlerts;
    }

    public boolean isPostError(){
        return postError;
    }

    public boolean isDeleteError(){
        return deleteError;
    }

    public int systemStatus(String userId) throws IOException{
        URL endpoint = new URL("https://api.marketalertum.com/EventsLog/" + userId);
        HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(false);
        return con.getResponseCode();
    }

    public static void main(String Args[]) throws IOException{
        Random rand = new Random();
        Main r = new Main();

        int noOfAlerts = rand.nextInt(10) + 1;
        int errorChance = rand.nextInt(4) + 1;

        r.deleteRequest();//To ensure that there are no previous alerts present

        Pair<String,Integer> p = r.accessingAlerts();
        int noOfAlertsShown = p.getSecond();
        System.out.println("Number of Alerts shown: " + noOfAlertsShown);

        for(int i = 0; i < noOfAlerts; i++){
            int statusPost = r.postRequest();
            System.out.println("Status Code (Post): " + statusPost);
        }

        if(errorChance == 1){
            r.badPostRequest();
            System.out.println("Bad Post Request");
        }else{
            p = r.accessingAlerts();
            noOfAlertsShown = p.getSecond();
            System.out.println("Number of Alerts shown: " + noOfAlertsShown);

            if(noOfAlerts > 5 && noOfAlertsShown == 5){
                System.out.println("The alert limit of 5 was obeyed. Only last 5 alerts were shown");
            }
        }

        if(errorChance == 2){
            r.badDeleteRequest();
            System.out.println("Bad Delete Request");
        }else{
            int statusDelete = r.deleteRequest();
            System.out.println("Status Code (Delete): " + statusDelete);

            p = r.accessingAlerts();
            noOfAlertsShown = p.getSecond();
            System.out.println("Number of Alerts shown: " + noOfAlertsShown);
        }
    }
}