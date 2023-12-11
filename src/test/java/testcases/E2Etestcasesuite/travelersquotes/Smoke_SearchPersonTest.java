package testcases.E2Etestcasesuite.travelersquotes;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.google.inject.spi.Element;

import base.TestBase;
import freemarker.core.ReturnInstruction.Return;
import io.restassured.RestAssured;
import utilities.TestUtil;

public class Smoke_SearchPersonTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void smoke_SearchPersonTest(Hashtable<String, String> data)
			throws InterruptedException, IOException, Exception {

		execution(data, "smoke_SearchPersonTest");

		RestAssured.baseURI = "http://backend-dev.20.81.157.238.nip.io";

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

				String apiUrl = String.format(baseApiUrl, userId, id);
				System.out.println(apiUrl);

				io.restassured.response.Response response = given().when().get(apiUrl).then().statusCode(200).extract()
						.response();

//Scenario: Personal Insurance > Searching Customer in Database:
				try {
					if (driver.findElement(By.xpath("//button[text()='Close']")).isDisplayed())
						driver.findElement(By.xpath("//button[text()='Close']")).click();
				} catch (Exception e) {
					System.out.println("Pop-up not visible");
				}

				// Searching quote

				// enter last name
				String lastname = response.path("data.Vehicles[0].Driver.lastName");
				type("lastName_XPATH", lastname);

				// enter first name
				String firstname = response.path("data.Vehicles[0].Driver.firstName");
				type("firstName_XPATH", firstname);

				// select the state
				String stateName = response.path("data.Drivers[0].stateName");
				select("state_XPATH", stateName);

				// enter city
				String city = response.path("data.Drivers[0].cityname");
				type("city_XPATH", city);

				// enter zip code
				String zipCode = response.path("data.Drivers[0].zipcode");
				type("zipCode_XPATH", zipCode);

				// click on the search button
				click("searchBtn_XPATH");

				// wait for the element
				Thread.sleep(2000);

				// locate parent frame
				WebElement ele_iframe = driver.findElement(By.xpath(OR.getProperty("custSearchiFrame_XPATH")));
				driver.switchTo().frame(ele_iframe);

				// locate main frame
				WebElement ele_frame = driver.findElement(By.xpath(OR.getProperty("custSearchFrame_XPATH")));
				driver.switchTo().frame(ele_frame);

				// wait for the element
				Thread.sleep(2000);
				if (!driver
						.findElements(
								By.xpath("//span[contains(text(),'No results matched the criteria you entered.')]"))
						.isEmpty()) {
					// Add Customer
					click("addcustomer_XPATH");

					// enter last name
					String lastName1 = response.path("data.Vehicles[0].Driver.lastName");
					type("addcustomer_lastname_XPATH", lastName1);

					// enter first name
					String firstName1 = response.path("data.Vehicles[0].Driver.firstName");
					type("addcustomer_firstname_XPATH", firstName1);

					// select the state
					String state1 = response.path("data.Drivers[0].stateName");
					type("addcustomer_street_XPATH", state1);

					// enter city
					String city1 = response.path("data.Drivers[0].cityname");
					type("addcustomer_city_XPATH", city1);

					// enter zip code
					String zipCode1 = response.path("data.Drivers[0].zipcode");
					type("addcustomer_zip_XPATH", zipCode1);

					click("addcustomer_initiateQuote_XPATH");

					// Switch back to the default content if needed
					driver.switchTo().defaultContent();

					// wait for the element
					Thread.sleep(2000);

					// return the parent window name as a String
					String parent = driver.getWindowHandle();

					// return the list of window name as a String
					Set<String> s = driver.getWindowHandles();

					// Now iterate using Iterator
					Iterator<String> I1 = s.iterator();

					// switched to child window
					while (I1.hasNext()) {

						String child_window = I1.next();

						if (!parent.equals(child_window)) {
							driver.switchTo().window(child_window);

						}
					}

					// maximize the child window
					driver.manage().window().maximize();

					// wait for the element
					Thread.sleep(2000);

					// moved to respective frame
					driver.switchTo().frame(2);

					// wait for the element
					Thread.sleep(2000);

					// wait for the element
					Thread.sleep(2000);

					// Selecting the rating state
					String ratingState = response.path("data.Drivers[0].USstateList.stateName");
					String upperCase = ratingState.toUpperCase();
					select("ratingState_massachuesetts_XPATH", upperCase);

					// Selecting the life of business
					select("lifeofbusiness_auto_XPATH", config.getProperty("LifeOfbusiness"));

					// enter the effective date
					String fieldValue = response.path("data.Drivers[0].FormalInquires[0].effectiveDate");
					LocalDate date = LocalDate.parse(fieldValue);

					// Define the desired date format
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

					// Format the date to the desired format
					String formattedDate = date.format(formatter);

					type("effectivedate_XPATH", formattedDate);
					// click on the continue button
					click("continue1_XPATH");
				}

				else {
					click("selectCustomer_XPATH");

					// click on the select customer button
					// click("selectCustomer_XPATH");

					// Switch back to the default content if needed
					driver.switchTo().defaultContent();

					// wait for the element
					Thread.sleep(2000);

					// return the parent window name as a String
					String parent = driver.getWindowHandle();

					// return the list of window name as a String
					Set<String> s = driver.getWindowHandles();

					// Now iterate using Iterator
					Iterator<String> I1 = s.iterator();

					// switched to child window
					while (I1.hasNext()) {

						String child_window = I1.next();

						if (!parent.equals(child_window)) {
							driver.switchTo().window(child_window);

						}
					}

					// maximize the child window
					driver.manage().window().maximize();

					// wait for the element
					Thread.sleep(2000);

					// moved to respective frame
					driver.switchTo().frame(2);

					// wait for the element
					Thread.sleep(2000);

					// click on the initiate new policy button
					click("initiatenewpolicy_XPATH");

					// wait for the element
					Thread.sleep(2000);

					// Selecting the rating state
					String ratingState = response.path("data.Drivers[0].USstateList.stateName");
					String upperCase = ratingState.toUpperCase();
					select("ratingState_massachuesetts_XPATH", upperCase);

					// Selecting the life of business
					select("lifeofbusiness_auto_XPATH", config.getProperty("LifeOfbusiness"));

					// enter the effective date
					String fieldValue = response.path("data.Drivers[0].FormalInquires[0].effectiveDate");
					LocalDate date = LocalDate.parse(fieldValue);

					// Define the desired date format
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

					// Format the date to the desired format
					String formattedDate = date.format(formatter);

					type("effectivedate_XPATH", formattedDate);

					// click on the continue button
					click("continue1_XPATH");

//Scenario:  Verifying Account> Account Details

					// return the parent window name as a String
					String parent1 = driver.getWindowHandle();

					// return the list of window name as a String
					Set<String> s1 = driver.getWindowHandles();

					// Now iterate using Iterator
					Iterator<String> I2 = s1.iterator();

					// switched to child window
					while (I1.hasNext()) {

						String child_window1 = I2.next();

						if (!parent.equals(child_window1)) {
							driver.switchTo().window(child_window1);
							Thread.sleep(6000);

						}
					}
				}

				// Entering Mobile
				String accountDetails_mobile = response.path("data.phone");
				type("accountDetails_mobile_XPATH", accountDetails_mobile);

				// Entering Email
				String accountDetails_email = response.path("data.email");
				type("accountDetails_email_XPATH", accountDetails_email);

				// Entering Confirm Email
				String accountDetails_confirmEmail = response.path("data.email");
				type("accountDetails_confirmEmail_XPATH", accountDetails_confirmEmail);

				// Entering Date of Birth
				String accountDetails_dateOfBirth = response.path("data.Drivers[0].dob");
				LocalDate date = LocalDate.parse(accountDetails_dateOfBirth);

				// Define the desired date format
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");

				// Format the date to the desired format
				String formattedDate1 = date.format(formatter1);

				type("accountDetails_dateOfBirth_XPATH", formattedDate1);

				// Entering Social Security number
				type("accountDetails_socialSecurityNumber_XPATH", config.getProperty("socialSecurityNumber"));

				String mailingAddress = response.path("data.LienHolders[0].mailingAddress");
				System.out.println(mailingAddress);
				WebElement residence_address = driver
						.findElement(By.xpath(OR.getProperty("accountDetails_residenceAddress_XPATH")));
				System.out.println(residence_address.getText());
				if (!mailingAddress.equals(residence_address)) {
					click("accountDetails_mailingAddressYes_XPATH");

					// Entering Address
					String accountDetails_mailingAddress = response.path("data.Drivers[0].primaryAddress");
					type("accountDetails_mailingAddress_XPATH", accountDetails_mailingAddress);

					// Entering City
					String accountDetails_mailingCity = response.path("data.Drivers[0].cityname");
					type("accountDetails_mailingCity_XPATH", accountDetails_mailingCity);

					// Entering State
					String accountDetails_mailingState = response.path("data.DriverAddresses[1].USstateList.stateName");
					select("accountDetails_mailingState_XPATH", accountDetails_mailingState);

					// Entering Zip
					String accountDetails_mailingZip = response.path("data.Drivers[0].zipcode");
					type("accountDetails_mailingZip_XPATH", accountDetails_mailingZip);

					// Clicking Continue

					click("accountDetails_mailingContinue_XPATH");

					// Clicking Original
					click("accountDetails_mailingUseOriginal_XPATH");

					// Clicking Continue
					click("accountDetails_continue_XPATH");
					Thread.sleep(2000);

					if (!driver
							.findElements(
									By.xpath("//span[contains(text(),'Address information appears to be incorrect.')]"))
							.isEmpty()) {
						System.out.println("Handling Address appearing Incorrect pop-up");
						click("accountDetails_continue_XPATH");
					} else {
						System.out.println("Address appearing correct");
					}

					// Clicking Original
					if (!driver.findElements(By.xpath("//button[@alias='Use Original']")).isEmpty()) {
						System.out.println("Pop-up visible");
						click("accountDetails_continue_XPATH");
					} else {
						System.out.println("Pop-up not visible");
					}
				} else {

					// Clicking Continue
					click("accountDetails_continue_XPATH");
					Thread.sleep(2000);

//			if (!driver
//					.findElements(By.xpath("//span[contains(text(),'Address information appears to be incorrect.')]"))
//					.isEmpty()) {
//				System.out.println("Handling Address appearing Incorrect pop-up");
//				click("accountDetails_continue_XPATH");
//			} else {
//				System.out.println("Address appearing correct");
//			}

					// Clicking Original
					// if (!driver.findElements(By.xpath("//button[@alias='Use
					// Original']")).isEmpty()) {
					if (driver.findElement(By.xpath(OR.getProperty("accountDetails_mailingUseOriginal_XPATH")))
							.isDisplayed()) {
						System.out.println("Pop-up visible");
						click("accountDetails_continue_XPATH");
					} else {
						System.out.println("Pop-up not visible");
					}
				}

//Scenario: Verifying Account> About Policy		

				// Entering prefix
				type("aboutPolicy_prefix_XPATH", config.getProperty("prefix"));

				// Entering Suffix
				type("aboutPolicy_suffix_XPATH", config.getProperty("suffix"));

				// Entering Sub code
				type("aboutPolicy_subCode_XPATH", config.getProperty("subCode"));

				int agencyLoyaltyCredit = response.path("data.Drivers[0].agencyLoyalityCredit");
				String agencystringValue = String.valueOf(agencyLoyaltyCredit);
				if (agencystringValue.equals("1")) {
					// Selecting Book Transfer
					select("aboutPolicy_agencyMove_XPATH", config.getProperty("agencymove"));

					// Choosing Travelers Policies
					if (driver.findElement(By.xpath(OR.getProperty("aboutPolicy_checkboxTenant_XPATH")))
							.isDisplayed()) {
						click("aboutPolicy_checkboxTenant_XPATH");
					} else {
						System.out.println("Tenant not visible");
					}
				} else {
					System.out.println("agencyLoyalityCredit is 0");
				}

				try {
					if (!driver.findElement(By.xpath(OR.getProperty("aboutPolicy_continue_XPATH"))).isDisplayed())
						click("aboutPolicy_continue_XPATH");
				} catch (Exception e) {
					System.out.println("Clicked");
				}

				try {
					if (driver.findElement(By.xpath("//span[text()='Territory is required for the STREET ADDRESS']"))
							.isDisplayed())
						click("accountdetails_tab_XPATH");
					Thread.sleep(2000);
					click("aboutPolicy_tab_XPATH");
					click("aboutPolicy_continue_XPATH");
				} catch (Exception e) {
					System.out.println("No Error Message related to Territory");
				}

//Scenario: Verifying Account Report Information Confirmation

				// Confirming Report
				if (!driver.findElements(By.xpath("//div[text()=' Report Information']")).isEmpty()) {
					click("aboutPolicy_ReportInfoNo_XPATH");
					click("aboutPolicy_ReportInfoYes_XPATH");
					click("aboutPolicy_ReportInfoContinue_XPATH");
				} else {
					System.out.println("About Policy Report Pop-up Not Available");
				}
				Thread.sleep(8000);

//Scenario: Verifying Vehicle and Driver Information				

				if (!driver.findElements(By.xpath("//h4[text()='Vehicle and Driver Information']")).isEmpty()) {
					System.out.println("Vehicle and Driver Information pop-up available");
					// Changing vehicle status
					try {
						if (!driver.findElements(By.xpath("(//select[@data-label='Status'])[1]")).isEmpty())
							select("vehicle_status1_XPATH", config.getProperty("vehiclestatus"));
					} catch (Exception e) {
						System.out.println("Vehicle status 1 not available");
					}

					try {
						if (!driver.findElements(By.xpath("(//select[@data-label='Status'])[2]")).isEmpty())
							select("vehicle_status2_XPATH", config.getProperty("vehiclestatus"));
					} catch (Exception e) {
						System.out.println("Vehicle status 2 not available");
					}

					try {
						if (!driver.findElements(By.xpath("(//select[@data-label='Status'])[3]")).isEmpty())
							select("vehicle_status3_XPATH", config.getProperty("vehiclestatus"));
					} catch (Exception e) {
						System.out.println("Vehicle status 3 not available");
					}

					try {
						if (!driver.findElements(By.xpath("(//select[@data-label='Status'])[4]")).isEmpty())
							select("vehicle_status4_XPATH", config.getProperty("vehiclestatus"));
					} catch (Exception e) {
						System.out.println("Vehicle status 4 not available");
					}

					try {
						if (!driver.findElements(By.xpath("(//select[@data-label='Status'])[5]")).isEmpty())
							select("vehicle_status5_XPATH", config.getProperty("vehiclestatus"));
					} catch (Exception e) {
						System.out.println("Vehicle status 5 not available");
					}
					Thread.sleep(3000);
					click("vehicle_driverInfo_continue_XPATH");

				} else {
					System.out.println("Vehicle and Driver Information pop-up Not available");
				}

//Scenario: Verifying Auto>Vehicles Screen

				// Adding New section
				click("vehicle_add_XPATH");

//		// Entering plateNumber
//		String vehicle_plateNumber = response.path("data.Vehicles[0].plateNumber");
//		type("vehicle_plateNumber_XPATH", vehicle_plateNumber);
//
//		// Selecting plateType
//		// String vehicle_plateType = response.path("data.Vehicles[0].plateType");
//		select("vehicle_plateType_XPATH", config.getProperty("vehicleplateType"));

				// Entering VIN
				String vehicle_vin = response.path("data.Vehicles[0].VINNumber");
				type("vehicle_vin_XPATH", vehicle_vin);

				// Selecting vehicleUse
				// String vehicle_vehicleUse = response.path("data.Vehicles[0].vehicleUse");
				select("vehicle_vehicleUse_XPATH", config.getProperty("vehicleUse"));

				// Entering annualMileage
				// String vehicle_annualMileage =
				// response.path("data.Vehicles[0].currentVehicleMileage");
				type("vehicle_annualMileage_XPATH", config.getProperty("annualMileage"));

				// Clicking purchased 90 Days option
				click("vehicle_purchased90DaysNo_XPATH");

				// Selecting ownershipLength
				// String vehicle_ownershipLength = response.path("");
				select("vehicle_ownershipLength_XPATH", config.getProperty("vehicleOwnershipLength"));

				// Selecting ownershipStatus
				// String vehicle_ownershipStatus = response.path("");
				select("vehicle_ownershipStatus_XPATH", config.getProperty("vehicleOwnershipStatus"));

				// Clicking ownershipStatusDetails
				click("vehicle_ownershipStatusDetails_XPATH");

				// Entering leinHolder name
				String leinHolder_name = response.path("data.LienHolders[0].lienHolderName");
				type("leinHolder_name_XPATH", leinHolder_name);

				// Entering leinHolder Address
				String leinHolder_address = response.path("data.DriverAddresses[0].streetAddress");
				type("leinHolder_address_XPATH", leinHolder_address);

				// Entering leinHolder City
				String leinHolder_city = response.path("data.Drivers[0].cityname");
				type("leinHolder_city_XPATH", leinHolder_city);

				// Entering leinHolder Country
				String leinHolder_country = response.path("data.Drivers[0].countryName");
				type("leinHolder_country_XPATH", leinHolder_country);

				// Entering leinHolder State
				String leinHolder_state = response.path("data.DriverAddresses[1].USstateList.stateName");
				select("leinHolder_state_XPATH", leinHolder_state);

				// Entering leinHolder Zip
				String leinHolder_zip = response.path("data.DriverAddresses[0].zipcode");
				type("leinHolder_zip_XPATH", leinHolder_zip);

				// Clicking leinHolder Continue
				click("leinHolder_continue_XPATH");

//		// Clicking Garage
//		click("garage_yes_XPATH");
//
//		// Entering garage Address
//		String garage_address = response.path("data.DriverAddresses[0].streetAddress");
//		type("garage_address_XPATH", garage_address);
//
//		// Entering garage City
//		String garage_city = response.path("data.Drivers[0].cityname");
//		type("garage_city_XPATH", garage_city);
//
//		// Entering garage Zip
//		String garage_zip = response.path("data.DriverAddresses[0].zipcode");
//		type("garage_zip_XPATH", garage_zip);
//
//		// Clicking garage
//		click("garage_continue_XPATH");
//		Thread.sleep(2000);

				// Clicking Remove
				click("vehicle_remove2_XPATH");

				// Scrolling
				scrollTillElement("vehicle_continue_XPATH");
				Thread.sleep(2000);

				// Clicking Continue
				click("vehicle_continue_XPATH");
				Thread.sleep(5000);

//Scenario: Verifying Auto > Drivers Screen

				// Clicking remove
				try {
					if (!driver.findElements(By.xpath("(//a[text()='Remove'])[3]")).isEmpty())
						click("vehicle_remove2_XPATH");
				} catch (Exception e) {
					System.out.println("Remove not displayed");
				}
				try {
					if (!driver.findElements(By.xpath("(//a[text()='Remove'])[4]")).isEmpty())
						click("vehicle_remove3_XPATH");
				} catch (Exception e) {
					System.out.println("Remove not displayed");
				}

				try {
					if (!driver.findElements(By.xpath("(//a[text()='Remove'])[5]")).isEmpty())
						click("vehicle_remove4_XPATH");
				} catch (Exception e) {
					System.out.println("Remove not displayed");
				}

				// Entering FirstName
				String driver_firstName = response.path("data.Vehicles[0].Driver.firstName");
				type("driver_firstName_XPATH", driver_firstName);

				// Entering LastName
				String driver_lastName = response.path("data.Vehicles[0].Driver.lastName");
				type("driver_lastName_XPATH", driver_lastName);

				// Entering Mid Initial
				String driver_midInitial = response.path("data.Vehicles[0].Driver.middle_name");
				type("driver_midInitial_XPATH", driver_midInitial);

				// Entering Date Of Birth
				String driver_dob = response.path("data.Drivers[0].dob");
				LocalDate date2 = LocalDate.parse(driver_dob);

				// Define the desired date format
				DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");

				// Format the date to the desired format
				String formattedDate2 = date2.format(formatter2);
				type("driver_dob_XPATH", formattedDate2);

				// Choosing Gender
				click("driver_male_XPATH");

				// Selecting Marital Status
				String driver_marital = response.path("data.Drivers[0].maritalStatus");
				select("driver_marital_XPATH", driver_marital);

				// Selecting Insured Name
				// String driver_namedInsured =
				// response.path("data.Drivers[0].relationToApplicant");
				select("driver_namedInsured_XPATH", config.getProperty("driver_namedInsured"));

				// Entering License Number
				String licenseNumber = response.path("data.Drivers[0].licenceNumber");
				type("driver_licenseNumber_XPATH", licenseNumber);

				// Selecting License state
				String driver_licenseState = response.path("data.DriverAddresses[1].USstateList.stateName");
				select("driver_licenseState_XPATH", driver_licenseState);

				// Entering License Dated
				String driver_dateLicensed = response.path("data.Vehicles[0].vehicletitles[0].issuedDate");

				// Convert to Date object
				Date date3;
				SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				date3 = originalFormat.parse(driver_dateLicensed);

				// Format as MM/dd/yyyy
				SimpleDateFormat targetFormat3 = new SimpleDateFormat("MM/dd/yyyy");
				String formattedDate3 = targetFormat3.format(date3);

				type("driver_dateLicensed_XPATH", formattedDate3);

				// Choosing Primary option
				// click("driver_primary1_XPATH");

				// Choosing IntelliDrive
				click("driver_intelliDrive_XPATH");
				Thread.sleep(2000);
//		// Entering Mobile
//		String driver_mobile1 = response.path("data.phone");
//		type("driver_mobile_XPATH", driver_mobile1);

				scrollTillElement("driver_continue_XPATH");

				// Clicking Continue
				click("driver_continue_XPATH");

				try {
					if (driver
							.findElements(By.xpath("//p[text()='Please verify, married driver but no spouse listed.']"))
							.isEmpty())
						click("driver_spouseVerifyOk_XPATH");
				} catch (Exception e) {
					System.out.println("Spouse Pop-up not visible");
				}

//Scenario: Verifying Auto > UnderWriting Screen		

				// Clicking UnderWriting tab
				click("underwriting_XPATH");

//		// Selecting Insurance Status
//		String Insurance_status = response.path("data.Drivers[0].relationToApplicant");
//		try {
//			if (Insurance_status.equals("Insured"))
//				;
//			selectOptionByText(By.xpath(OR.getProperty("underwriting_InsuranceStatus_XPATH")), "Currently Insured");
//		} catch (Exception e) {
//			System.out.println("Not insured");
//		}
//
//		// Selecting Current Auto Insurance Carrier
//		String insuranceCarrier = response.path("data.Home.homeInsuranceWith");
//		try {
//			if (insuranceCarrier.equals("Arbella"))
//				type("underwriting_insuranceCarrier_XPATH", insuranceCarrier);
//			selectOptionByText(By.xpath(OR.getProperty("underwriting_insuranceCarrier_XPATH")),
//					"Arbella Insurance Group - Arbella Indemnity Insurance Company");
//		} catch (Exception e) {
//			System.out.println("homeInsuranceWith is not Arbella");
//		}
//
//		// Entering Current Policy Expiration Date
//		String fieldValue = response.path("data.Drivers[0].FormalInquires[0].expirationDate");
//		LocalDate date4 = LocalDate.parse(driver_dob);
//
//		DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//
//		String formattedDate4 = date4.format(formatter4);
//		type("underwriting_ExpirationDate_XPATH", formattedDate4);
//
//		// Entering Current Bodily Injury Limits
//		String InjuryLimits = response.path("currentPolicyLimits");
//		try {
//			if (InjuryLimits.contains("100,000  / 300,000"))
//				;
//			selectOptionByText(By.xpath(OR.getProperty("underwriting_InjuryLimits_XPATH")),
//					"Greater than or Equal to 100/300 CSL(300)");
//		} catch (Exception e) {
//			selectOptionByText(By.xpath(OR.getProperty("underwriting_InjuryLimits_XPATH")), "20/40 (CSL 50)");
//		}

				// Selecting Length of time with Current Company
				String currentCompany = response
						.path("data.Drivers[0].FormalInquires[0].contInsuranceWithCurrentCarrier");
				try {
					if (currentCompany.equals("12"))
						;
					selectOptionByText(By.xpath(OR.getProperty("underwriting_currentCompany_XPATH")),
							"Less than 2 years");

				} catch (Exception e) {
					selectOptionByText(By.xpath(OR.getProperty("underwriting_currentCompany_XPATH")),
							"Less than 6 months");
				}

				// Selecting ContinuousInsurance
				String underwriting_ContinuousInsurance = response
						.path("data.Drivers[0].FormalInquires[0].contInsuranceWithCurrentCarrier");
				try {
					if (underwriting_ContinuousInsurance.equals("12"))
						;
					selectOptionByText(By.xpath(OR.getProperty("underwriting_ContinuousInsurance_XPATH")),
							"Less than 2 years");

				} catch (Exception e) {
					selectOptionByText(By.xpath(OR.getProperty("underwriting_currentCompany_XPATH")),
							"Less than 6 months");
				}

				// Selecting miscellaneousQuestion1
				click("underwriting_miscellaneousQuestion1_XPATH");

				// Selecting miscellaneousQuestion2
				click("underwriting_miscellaneousQuestion2_XPATH");

				// Selecting miscellaneousQuestion3
				click("underwriting_miscellaneousQuestion3_XPATH");

				// Selecting miscellaneousQuestion4
				click("underwriting_miscellaneousQuestion4_XPATH");

				// Selecting miscellaneousQuestion5
				click("underwriting_miscellaneousQuestion5_XPATH");

				// Selecting PrimaryResidence

				String PrimaryResidence = response.path("data.Home.homeType");
				try {
					if (PrimaryResidence.contains("OWN"))
						;
					selectOptionByText(By.xpath(OR.getProperty("underwriting_currentCompany_XPATH")),
							"Own and Insure Home");
				} catch (Exception e) {
					System.out.println("OWN not available");
				}
				select("underwriting_miscellaneous_PrimaryResidence_XPATH",
						config.getProperty("underwriting_PrimaryResidence"));

				// Clicking Continue
				click("underwriting_continue_XPATH");

//Scenario: Verifying Auto > Coverage Screen		

				selectOptionByText(By.xpath(OR.getProperty("coverage_bodilyInjurytoOthers_XPATH")), "20/40");

				selectOptionByText(By.xpath(OR.getProperty("coverage_personalInjuryProtection_XPATH")), "8000");

				String personalInjuryProtectionDeductible = response.path("data.DeductibleLimits[0].personalInjuryDed");
				select("coverage_personalInjuryProtectionDeductible_XPATH", personalInjuryProtectionDeductible);

				select("coverage_bodilyInjuryCausedbyanUninsuredAuto_XPATH",
						data.get("coverage_bodilyInjuryCausedbyanUninsuredAuto"));

				select("coverage_optionalBodilyInjurytoOthers_XPATH",
						data.get("coverage_optionalBodilyInjurytoOthers"));

				select("coverage_medicalPayments_XPATH", data.get("coverage_medicalPayments"));

				select("coverage_collision_XPATH", data.get("coverage_collision"));

				click("coverage_waiverofCollisionDeductible_XPATH");

				select("coverage_comprehensive_XPATH", data.get("coverage_comprehensive"));

				select("coverage_glassDeductible_XPATH", data.get("coverage_glassDeductible"));

				scrollTillElement("coverage_rate_XPATH");

				select("coverage_substituteTransportation_XPATH", data.get("coverage_substituteTransportation"));

				select("coverage_roadsideAssistance_XPATH", data.get("coverage_roadsideAssistance"));

				select("coverage_bodilyInjuryCausedbyanUnderinsuredAuto_XPATH",
						data.get("coverage_bodilyInjuryCausedbyanUnderinsuredAuto"));

				click("coverage_loan/LeaseGap_XPATH");

				click("coverage_rate_XPATH");

				scrollTillElement("coverage_continue_XPATH");

				click("coverage_continue_XPATH");

//Scenario: Verifying Auto > Violations & Losses	

			} else {
				System.out.println("No records found.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
