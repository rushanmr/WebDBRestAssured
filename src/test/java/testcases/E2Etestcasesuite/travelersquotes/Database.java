package testcases.E2Etestcasesuite.travelersquotes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aventstack.extentreports.gherkin.model.Given;

import base.TestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;

public class Database extends TestBase {
	public static void main(String[] args) {

		String url = "jdbc:mysql://newengdevdb.mysql.database.azure.com:3306/test";
		String username = "newenglandadmin";
		String password = "uckknx6HVH";
		String query = "SELECT id, userID FROM policy WHERE isTravelers = 1";

		try (Connection connection = DriverManager.getConnection(url, username, password);
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			// Assuming the result set has only one row (based on the nature of the query)
			if (resultSet.next()) {
				// Retrieve values from the result set
				String id = resultSet.getString("id");
				String userId = resultSet.getString("userId");

				// Print or use the values as needed
				System.out.println("ID: " + id);
				System.out.println("UserID: " + userId);
				
				String baseApiUrl = "http://backend-dev.20.81.157.238.nip.io/user/getUserDetailsById/%s?policyId=%s";
				

				String apiUrl = String.format(baseApiUrl, userId 
						, id);
				System.out.println(apiUrl);
				
				io.restassured.response.Response response = given().when().get(apiUrl).then().statusCode(200).extract()
						.response();
				String lastname = response.path("data.email");
				System.out.println(lastname);
			} 
			else 
			{
				System.out.println("No records found.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
}
