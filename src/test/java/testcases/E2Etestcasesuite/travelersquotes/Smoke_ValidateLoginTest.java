package testcases.E2Etestcasesuite.travelersquotes;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import base.TestBase;
import utilities.TestUtil;

public class Smoke_ValidateLoginTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void smoke_ValidateLoginTest(Hashtable<String, String> data) throws InterruptedException, IOException {

		// INITIAL TEST CASE SETUP
		execution(data, "Smoke_ValidateLoginTest");

		type("userID_XPATH",config.getProperty("username"));

		type("password_XPATH", data.get("password"));

		click("login_XPATH");

		String txt = driver.findElement(By.xpath(OR.getProperty("welcomeName_XPATH"))).getText().toString();

		assertEquals(StringUtils.substringBetween(txt, "(", ")"), data.get("user_id"));
//		assertEquals(driver.getTitle(), "Personal Insurance Home Page | Travelers Insurance");

	}
}
