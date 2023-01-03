package main;

import java.util.Random;

import java.io.IOException;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Runner {
	public int accessingAlerts(){
		System.setProperty("webdriver.chrome.driver", "/Documents/UM/3rd year Sem1/CPS3230-Software Testing/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.marketalertum.com/Alerts/Login");
        WebElement searchField = driver.findElement(By.id("UserId"));
        searchField.sendKeys("46aba3d5-35a9-4850-b5c1-02824284c450");
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.submit();
        int noOfAlerts = driver.findElements(By.tagName("table")).size();
        driver.quit();
        return noOfAlerts;
	}
	
	public int postRequest() throws IOException, JSONException {
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
	
	public int badPostRequest() throws IOException, JSONException {
		JSONObject alertData = new JSONObject();
        alertData.put("alertType",6);
        alertData.put("heading","Jumper Windows 11 Laptop");
        alertData.put("description","Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD");
        alertData.put("url","https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth");
        alertData.put("imageURL","https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg");
        alertData.put("postedBy","badCredentials");
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
        System.out.println("Bad Post Request");
        return con.getResponseCode();//Status code to check for status of api call
    }
	
	public int deleteRequest() throws IOException{
        URL endpoint = new URL("https://api.marketalertum.com/Alert?userId=" + "46aba3d5-35a9-4850-b5c1-02824284c450");
        HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(false);
        return con.getResponseCode();
    }
	
	public int badDeleteRequest() throws IOException{
        URL endpoint = new URL("https://api.marketalertum.com/Alert?userId=" + "badCredentials");
        HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(false);
        System.out.println("Bad Delete Request");
        return con.getResponseCode();
    }
	
	public static void main(String Args[]) throws IOException, JSONException{
		Random rand = new Random();
		Runner r = new Runner();
		
        int noOfAlerts = rand.nextInt(10) + 1;
        int errorChance = rand.nextInt(4) + 1;
        
        r.deleteRequest();//To ensure that there are no previous alerts present
        
        int noOfAlertsShown = r.accessingAlerts();
        System.out.println("Number of Alerts shown: " + noOfAlertsShown);

        for(int i = 0; i < noOfAlerts; i++){
	        int statusPost = r.postRequest();
	        System.out.println("Status Code (Post): " + statusPost);
        }
        
        if(errorChance == 1){
        	r.badPostRequest();
	        noOfAlertsShown = r.accessingAlerts();
	        System.out.println("Number of Alerts shown: " + noOfAlertsShown);
        }else{
	        noOfAlertsShown = r.accessingAlerts();
	        System.out.println("Number of Alerts shown: " + noOfAlertsShown);
	        if(noOfAlerts > 5 && noOfAlertsShown == 5){
	        	System.out.println("The alert limit of 5 was obeyed. Only last 5 alerts were shown from the current " + noOfAlerts + " alerts.");
	        }
        }
        
        if(errorChance == 2){
        	r.badDeleteRequest();
	        noOfAlertsShown = r.accessingAlerts();
	        System.out.println("Number of Alerts shown: " + noOfAlertsShown);
        }else{
        	int statusDelete = r.deleteRequest();
            System.out.println("Status Code (Delete): " + statusDelete);
            
            noOfAlertsShown = r.accessingAlerts();
            System.out.println("Number of Alerts shown: " + noOfAlertsShown);
        }
	}
}