package testcases.E2Etestcasesuite.getaquote;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import base.TestBase;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.TestUtil;

public class Smoke_GetaQuoteTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void smoke_GetaQuoteTest(Hashtable<String, String> data) throws InterruptedException, IOException {

		// INITIAL TEST CASE SETUP
		execution(data, "Smoke_GetaQuoteTest");

		// START THE QUOTE
		// enter the data in the zip field

		String zip = data.get("zip");
		System.out.println("zip::::::" + zip);
		String[] zip_split = zip.split(".0");
		System.out.println("zip_split::::::" + zip_split[0]);
		type("quotezip_getaquote_XPATH", zip_split[0]);

		// click on the start quote button
		click("startquote_getaquote_XPATH");

		// wait for the element
		Thread.sleep(10000);

		// get the url title
		String navigationURL = driver.getTitle();

		// validate url navigation
		while (!(navigationURL.contains("Insurance Quotes"))) {

			// quit the browser
			driver.quit();

			// launch the chrome browser
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			consoleMessage("Chrome launched successfully!!!");

			// launch the url
			driver.get(config.getProperty("testsiteurl"));
			consoleMessage("Test site launched successfully!!!" + config.getProperty("testsiteurl"));

			// maximize the screen
			driver.manage().window().maximize();
			consoleMessage("The window maximize successfully!!!");

			// set the implicit wait
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implecit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 60);

			// enter the data in the zip field
			type("quotezip_getaquote_XPATH", zip_split[0]);

			// click on the start quote button
			click("startquote_getaquote_XPATH");

			// wait for the element
			Thread.sleep(10000);

			// get the url title
			navigationURL = driver.getTitle();
		}

		// wait for the element
		explicitWait("no_currentuser_getaquote_XPATH");

		// CONFIRMATION OF TRAVELERS CUSTOMER

		// select no for current customer option
		click("no_currentuser_getaquote_XPATH");

		// click on the continue button
		click("continue_currentuser_getaquote_XPATH");

		// ENTER BASIC CUSTOMER INFIRMATION

		// enter the first name
		type("firstname_getaquote_XPATH", data.get("first_name"));

		// enter the last name
		type("lastname_getaquote_XPATH", data.get("last_name"));

		// select the suffix name
		select("suffix_getaquote_XPATH", data.get("suffix"));

		// enter an email address
		type("email_getaquote_XPATH", data.get("email"));

		// enter the address 1
		type("addr_getaquote_XPATH", data.get("street_address"));

		// enter the city
		type("city_getaquote_XPATH", data.get("city"));

		// select the state
		select("state_getaquote_XPATH", data.get("state"));

		// enter the zip code
		type("zip_getaquote_XPATH", zip_split[0]);

		// click on the terms and condition checkbox
		WebElement element = driver.findElement(By.id("consent"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		consoleMessage("Clicked on the terms and condition checkbox.");

		// wait for the element
		Thread.sleep(2000);

		// click on the continue my quotes
		click("continueMyQuotes_getaquote_XPATH");

		// ENTER LITTLE MORE BASIC CUSTOMER INFIRMATION

		// wait for the element
		Thread.sleep(2000);

		// select the gender
		select("gender_getaquote_XPATH", data.get("gender"));

		// enter date of birth
		type("dob_getaquote_XPATH", data.get("dob"));

		// select the marital status
		select("maritalStatus_getaquote_XPATH", data.get("marital_status"));

		// enter the phone number
		type("tel_getaquote_XPATH", data.get("phone_number"));

		// wait for the element
		Thread.sleep(2000);

		// click on the continue button
		click("continue2_getaquote_XPATH");

		// ENTER DRIVING INFORMATION OF THE CUSTOMER

		// select the license
		select("license_getaquote_XPATH", data.get("license"));

		// enter the license age
		type("ageLicensed_getaquote_XPATH", data.get("age_licensed"));

		// select the license state
		select("licenseState_getaquote_XPATH", data.get("license_state"));

		// select the option for the driver require an SR22
		click("no_requireSR22_getaquote_XPATH");

		// select the option for the driver's license ever been suspended
		click("no_everSuspended_getaquote_XPATH");

		// select the option for receiving a discount for paperless billing
		click("yes_paperlessBilling_getaquote_XPATH");

		// select the option for driver taken a Defensive Driver course
		click("no_defensiveDriverCourse_getaquote_XPATH");

		// select the own or rent your home
		select("homeOwnership_getaquote_XPATH", data.get("home_ownership"));

		// click on the no button for the previous history
		click("no_allIncidentsQuestion_getaquote_XPATH");

		// wait for the element
		Thread.sleep(2000);

		// click on the Save Driver Button
		WebElement element2 = driver.findElement(By.xpath(OR.getProperty("saveDriver_getaquote_XPATH")));
		executor.executeScript("arguments[0].click();", element2);
		consoleMessage("Clicked on the Save Driver Button.");

		// wait for the element
		Thread.sleep(2000);

		// click on the continue button
		click("continue3_getaquote_XPATH");

		// ENTER VEHICLE INFORMATION OF THE CUSTOMER

		// wait for the element
		Thread.sleep(2000);

		// select vehicle year
		select("vehicleYear_getaquote_XPATH", "2020");

		// select vehicle make
		select("vehicleMake_getaquote_XPATH", data.get("vehicle_make"));

		// select vehicle model
		select("vehicleModel_getaquote_XPATH", data.get("vehicle_model"));

		// select vehicle body style
		select("vehicleBodyStyle_getaquote_XPATH", data.get("vehicle_bodystyle"));

		// select vehicle use
		select("vehicleUse_getaquote_XPATH", data.get("vehicle_use"));

		// select vehicle use
		select("vehicleUse_getaquote_XPATH", data.get("vehicle_use"));

		// enter the annual miles
		type("vehicleAnnualMiles_getaquote_XPATH", data.get("vehicle_annual_miles"));

		// select vehicle ownership
		select("vehicleOwnership_getaquote_XPATH", data.get("vehicle_ownership"));

		// select vehicle purchase date
		select("vehiclePurchaseDate_getaquote_XPATH", data.get("vehicle_purchase_date"));

		// scrolling down
		scrollByPixel(300);

		// wait for the element
		Thread.sleep(2000);

		// click on the save vehicle button
		WebElement element3 = driver.findElement(By.xpath(OR.getProperty("saveVehicle_getaquote_XPATH")));
		executor.executeScript("arguments[0].click();", element3);
		consoleMessage("Clicked on the save vehicle button.");

		// wait for the element
		Thread.sleep(2000);

		// click on the continue to auto history
		WebElement element4 = driver.findElement(By.xpath(OR.getProperty("continue4_getaquote_XPATH")));
		executor.executeScript("arguments[0].click();", element4);
		consoleMessage("Clicked on the continue to auto history.");

		// ENTER AUTO HISTORY OF THE CUSTOMER

		// wait for the element
		Thread.sleep(2000);

		// select the auto carrier
		select("autoCarrier_getaquote_XPATH", data.get("auto_carrier"));

		// select the auto years with carrier
		select("autoYearsWithCarrier_getaquote_XPATH", data.get("auto_years_with_carrier"));

		// select the auto continuous years insured
		select("autoContinuousYearsInsured_getaquote_XPATH", data.get("auto_continuous_years_insured"));

		// select the bodily injury limit
		select("bodilyInjuryLimit_getaquote_XPATH", data.get("bodily_injury_limit"));

		// wait for the element
		Thread.sleep(2000);

		// click on the continue button
		WebElement element5 = driver.findElement(By.xpath(OR.getProperty("continue5_getaquote_XPATH")));
		executor.executeScript("arguments[0].click();", element5);
		consoleMessage("Clicked on the continue button.");

		// wait for the element
		explicitWait("estimatedQuote_getaquote_XPATH");

		// VALIDATE THE ESTIMATED TRAVELERS QUOTE
		FileWriter textFile1 = new FileWriter(System.getProperty("user.dir") + "/src/test/resources/logs/quote.txt");
		BufferedWriter textFile = new BufferedWriter(textFile1);
		verifyEquals("estimatedQuote_getaquote_XPATH", data.get("validation_1"));

		String quote1_dollarSign = driver
				.findElement(By.xpath(OR.getProperty("dollarSign_estimatedQuote_getaquote_XPATH"))).getText();
		String quote1_amount = driver.findElement(By.xpath(OR.getProperty("amount_estimatedQuote_getaquote_XPATH")))
				.getText();
		String quote1_unit = driver.findElement(By.xpath(OR.getProperty("unit_estimatedQuote_getaquote_XPATH")))
				.getText();

		textFile.write("********** Below is The Estimated Travelers Quote **********");
		textFile.newLine();

		Thread.sleep(2000);
		System.out.println("Travelers Quote : " + quote1_dollarSign + quote1_amount + "/" + quote1_unit);
		textFile.write("Travelers Quote : " + quote1_dollarSign + quote1_amount + "/" + quote1_unit);
		textFile.newLine();
		textFile.newLine();

		// VALIDATE THE QUOTES PROVIDED BY INSTAMATCH
		verifyEquals("instaMatchQuotes_getaquote_XPATH", data.get("validation_2"));

		List<WebElement> quote_list = driver
				.findElements(By.xpath(OR.getProperty("quoteList_instaMatchQuotes_getaquote_XPATH")));

		for (int i = 0; i < quote_list.size(); i++) {
			String logoName = driver.findElement(By.xpath("(//div[@class='carrierLogo1']//img)[" + i + "+1]"))
					.getAttribute("alt");

			String dollar = driver.findElement(By.xpath(
					"(//div[@class='carrierProductsWrap']//div[@class='priceBlock priceBlock-1 autoResult']//span[@class='dollarSign'])["
							+ i + "+1]"))
					.getText();

			String amount = driver.findElement(By.xpath(
					"(//div[@class='carrierProductsWrap']//div[@class='priceBlock priceBlock-1 autoResult']//span[@class='priceNumber'])["
							+ i + "+1]"))
					.getText();

			String unit = driver.findElement(By.xpath(
					"(//div[@class='carrierProductsWrap']//div[@class='priceBlock priceBlock-1 autoResult']//span[@class='priceUnit']/span)["
							+ i + "+1]"))
					.getText();

			if (i == 0) {
				textFile.write("********** Below are the Quotes provided by InsuraMatch **********");
				textFile.newLine();
			}
			System.out.println(logoName + " : " + dollar + amount + "/" + unit);
			textFile.write(logoName + " : " + dollar + amount + "/" + unit);
			textFile.newLine();

		}
		textFile.close();
	}
}
