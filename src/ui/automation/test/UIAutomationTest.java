package ui.automation.test;

import java.util.Set;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UIAutomationTest {

	static WebDriver driver = null;
	static WebDriverWait wait = null;
	static Actions action = null;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String URL = "https://www.labcorp.com";
		String jobTitle = "Senior Software QA Analyst";
		String jobLocation = null;
		String jobId = null;

		System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 15);
		action = new Actions(driver);
		
		System.out.println("Test Start");
		launchApplication(URL);
		navigateToCareers();
		searchForOpening(jobTitle);

		//capturing job location....
		jobLocation = driver
				.findElement(By.xpath("//h2[text()='" + jobTitle + "']/parent::a/span[@class='job-location']"))
				.getText();
		
		clickJobLink(jobTitle);
		
		//capturing job Id....
		jobId = driver.findElement(By.xpath("//span[@class='job-id job-info']")).getText();
		jobId = jobId.split(" ")[2];
		
		//Assert job Title...
		Assert.assertEquals(driver.findElement(By.xpath("//h1[@class='job-description__heading']")).getText(),
				jobTitle);
		
		//Assert job location...
		Assert.assertEquals(
				driver.findElement(By.xpath("//span[@class='job-location job-info']")).getText().contains(jobLocation),
				true);
		
		clickApplyNow();
		
		//Assert job Title...
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='jobTitle job-detail-title']")).getText(),
				jobTitle);
		
		//Assert job Id...
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='jobnum']")).getText().contains(jobId), true);
		
		////Assert job location...
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='resultLocationLink']/span")).getText()
				.contains(jobLocation.split(",")[0]), true);
		
		clickReturnToSearch();		
		
		driver.close();
		driver.quit();
		System.out.println("Test Complete");
	}

	public static void launchApplication(String URL) {

		driver.get(URL);
		driver.manage().window().maximize();
		WebElement link_Careers = driver.findElement(By.xpath("(//a[text()='Careers'])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(link_Careers));
		System.out.println("LabCorp launched");

	}

	public static void navigateToCareers() throws Exception {
		
		WebElement link_Careers = driver.findElement(By.xpath("(//a[text()='Careers'])[1]"));
		WebElement link_PrivacyStatement = driver.findElement(By.xpath("//a[text()='Privacy Statement']"));
		action.moveToElement(link_PrivacyStatement).build().perform();
		String parent = driver.getWindowHandle();
		link_Careers.click();
		Thread.sleep(3000);
		Set<String> set = driver.getWindowHandles();
		for (String sets : set) {
			if (!sets.equals(parent))
				driver.switchTo().window(sets);
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@id,'search-keyword')]")));
		System.out.println("Navigated to Careers page.");
	}

	public static void searchForOpening(String jobTitle) {
		
		action.moveToElement(driver.findElement(By.xpath("//h2[text()='About Us']"))).build().perform();
		driver.findElement(By.xpath("//input[contains(@id,'search-keyword')]")).sendKeys(jobTitle);
		driver.findElement(By.xpath("//input[contains(@id,'search-location')]")).click();
		driver.findElement(By.xpath("//input[contains(@id,'search-location')]")).clear();
		driver.findElement(By.className("search-form__submit")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[text()='" + jobTitle + "']/parent::a")));
		System.out.println("Searched for "+ jobTitle + " job");
	}
	
	public static void clickJobLink(String jobTitle) {
		driver.findElement(By.xpath("//h2[text()='" + jobTitle + "']/parent::a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='Apply Now'])[1]")));
		System.out.println("Clicked on the Job link");
	}
	
	public static void clickApplyNow() {
		driver.findElement(By.xpath("(//a[text()='Apply Now'])[1]")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Apply']/parent::button")));		
		driver.findElement(By.xpath("//body")).click();
		System.out.println("Clicked on Apply Now button");
	}
	
	public static void clickReturnToSearch() {
		driver.findElement(By.xpath("//span[text()='Return to Job Search']/parent::button")).click();
		System.out.println("Clicked to Return to Search link");
	}

}
