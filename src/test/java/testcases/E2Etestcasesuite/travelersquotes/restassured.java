package testcases.E2Etestcasesuite.travelersquotes;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.support.ui.Select;

import freemarker.core.ParseException;

public class restassured {
	public static void main(String[] args) throws java.text.ParseException, ClassNotFoundException {
	
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 
		String url = "jdbc:mysql:newengdevdb.mysql.database.azure.com";
        String username = "newenglandadmin";
        String password = "uckknx6HVH";
        								
        			
        // JDBC variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        
        try {
            // Establish database connection
            connection = DriverManager.getConnection(url, username, password);

            // SQL query
            String sql = "SELECT id, userId FROM policy WHERE isTravelers = 1";

            // Prepare and execute the query
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            // Iterate through the result set
            while (resultSet.next()) {
                // Retrieve values
                String userId = resultSet.getString("id");
                String policyId = resultSet.getString("userId");

                // Check your condition (e.g., value = 1)
                // If the condition is met, use userId and policyId as needed
                System.out.println("User ID: " + userId);
                System.out.println("Policy ID: " + policyId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC resources in the reverse order of their creation
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	
//	        String baseUrl = "/user/getUserDetailsById/%id?policyId=%userId";
//
//
//    
//	        String dynamicUrl = String.format(baseUrl, id, userID);
//    
//	        System.out.println("Dynamic URL: " + dynamicUrl);
//	
//	
//	 RestAssured.baseURI = "http://backend-dev.20.81.157.238.nip.io";
//
//     // Write your Rest Assured test
//     io.restassured.response.Response response =
//             given()
//                 .when()
//                     .get(dynamicUrl)
//             .then()
//                 .statusCode(200)
//                 .extract().response();

     
     // Print the response details
//     System.out.println("Status Code: " + response.getStatusCode());
//     System.out.println("Headers: " + response.getHeaders());
//     System.out.println("Body: " + response.getBody().asString());
//     String asString = response.getBody().asString();
//     if ( asString.contains("agencyLoyalityCredit\":0")) {
//		System.out.println("True");
//	} else {
//		System.out.println("false");
//	}
     //int agencyLoyaltyCredit = response.path("data.Drivers[0].agencyLoyalityCredit");
     //System.out.println("Field Value: " + agencyLoyaltyCredit);
    // String fieldValue = response.path("data.phone");
//     LocalDate date = LocalDate.parse(fieldValue);

//     // Define the desired date format
//     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//
//     // Format the date to the desired format
//     String formattedDate = date.format(formatter);
//
//     // Print the formatted date
//     System.out.println("Formatted Date: " + formattedDate);
////
//     // Print the extracted field value
    // System.out.println("Field Value: " + fieldValue);
     	
//     String driver_dateLicensed = response.path("data.Vehicles[0].vehicletitles[0].issuedDate");
//
//     // Convert to Date object
//     Date date3;
//     SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//	 date3 = originalFormat.parse(driver_dateLicensed);
//
//	 // Format as MM/dd/yyyy
//	 SimpleDateFormat targetFormat3 = new SimpleDateFormat("MM/dd/yyyy");
//	 String formattedDate3 = targetFormat3.format(date3);
//
//	 System.out.println(formattedDate3);
     
     	
	}
	
}
