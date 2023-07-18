package stepDefinitions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.*;
import pages.ASPnetawesomePage;

public class GridFilterRowSteps {
	
	private WebDriver driver = null;
	private WebDriverWait wait = null;
	private ASPnetawesomePage aspNetAwesomepage;
	private String id;
	private String rowIdLocator;
	private int currentPage = 1;
	
	@Given("a valid ID")
	public void a_valid_id() throws IOException{
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
        
        System.out.println("Input a valid ID:");
        // Reading data using readLine
        id = reader.readLine();
    }
	
	@Given("a broswer is open")
	public void a_broswer_is_open() {
	    // Write code here that turns the phrase above into concrete actions
		String localDir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver",localDir+"/src/test/resources/Drivers/chromedriver.exe");
		
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}


	@Given("I am on the ASP demo page")
	public void i_am_on_the_asp_demo_page() {
	    // Write code here that turns the phrase above into concrete actions
		driver.navigate().to("https://demo.aspnetawesome.com/");
		aspNetAwesomepage = new ASPnetawesomePage(driver);
		aspNetAwesomepage.removeCookieMessage();
	}
	
	@When("^I sort the ID in (.*) order$")
	public void i_sort_the_id_in_ascending_order(String sort) {
	    // Write code here that turns the phrase above into concrete actions
		aspNetAwesomepage.sortId(sort);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			System.out.println("interrupted");
		}
	}


	@When("the user searches for the ID")
	public void the_user_searches_for_the_id() {
	    // Write code here that turns the phrase above into concrete actions
		rowIdLocator = aspNetAwesomepage.getGridFilterRowXpathById(id);
		String lastId = aspNetAwesomepage.getLastRowId();
		String nextPageLocator=aspNetAwesomepage.getNextPageButtonXpathByPage(currentPage);
		while(Integer.parseInt(lastId)<Integer.parseInt(id) && nextPageLocator!=null)
		{
			aspNetAwesomepage.clickElement(nextPageLocator);
			wait.until(ExpectedConditions
	                .presenceOfElementLocated(By.xpath(nextPageLocator+"[contains(@class,'awe-selected')]")));
			currentPage++;
			nextPageLocator = aspNetAwesomepage.getNextPageButtonXpathByPage(currentPage);
			lastId = aspNetAwesomepage.getLastRowId();
		}
		boolean isIdFound = aspNetAwesomepage.doesElementExist(rowIdLocator);
		if(!isIdFound) Assert.fail("ID was not found");
	}

	@Then("the row for the corresponding ID should be visible")
	public void the_row_for_the_corresponding_id_should_be_visible() {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Return");
		aspNetAwesomepage.scrollToElement(rowIdLocator);
		
		
	}

	@Then("the entry details is printed in the console")
	public void the_entry_details_is_printed_in_the_console() {
	    // Write code here that turns the phrase above into concrete actions
		String[] headers = aspNetAwesomepage.returnTableHeaders();
	    String[] entry = aspNetAwesomepage.returnRowDetails(rowIdLocator);
	    for(int i=0;i<entry.length;i++)
	    {
	    	System.out.println(headers[i]+": "+entry[i]);
	    }
	}
}
