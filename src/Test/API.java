package Test;

import static com.jayway.restassured.RestAssured.given;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import com.jayway.restassured.response.Response;

public class API {
	
	static String filePath = "/API/src/Data/input.json";
	static String strURL = "https://6143a99bc5b553001717d06a.mockapi.io/testapi/v1//Users";
	
	public static void main(String[] args) {
		post();
		get();
	}
	
	public static void get() {
		
		Response res = given()
		.header("Content-type", "application/json")
        .when()
        .get(strURL)
        .then()
        .extract().response();
		
		if(res.getStatusCode()==200){
			
			if(res.jsonPath().getString("employee_firstname").equals("TestData12345") &&
	        res.jsonPath().getString("employee_lastname").equals("TestData12345") && 
	        res.jsonPath().getString("employee_phonenumbe").equals("264-783-9453"))
			System.out.println("Success");
			
			else{
				System.out.println("failed");
				System.exit(0);
			}
		}
		else{
			System.out.println("failed");
			System.exit(0);
		}
	}
	
	public static void post() {
		// TODO Auto-generated method stub
		
		String data = readData(filePath);
		
		//Response res =  given().contentType("application/json").post(strURL, data);
		
		Response res = given()
		.header("Content-type", "application/json")
        .and()
        .body(data)
        .when()
        .post(strURL)
        .then()
        .extract().response();
		
		if(res.getStatusCode()==200) System.out.println("Success");
		else{
			System.out.println("Failed");
			//System.exit(0);
		}
	}
	
	public static String readData(String fileName){
		try {
			return FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
