package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class ASPnetawesomePage {
	
	WebDriver driver;
	//locators as string so it can be reused
	String divGridFilter = "//div[@id='GridFrow2']";
	String divGridFilterContent = divGridFilter+"//div[@class='awe-mcontent']";
	String trGridFilterHeaderRow = "//div[@id='GridFrow2']//div[@class='awe-colw']//tr[1]";
	String divLoading = divGridFilter+"//span[@class='awe-loading']";
	String buttonCurrentPage = divGridFilter+"//button[contains(@class,'awe-selected')]";
	String buttonNextPage = divGridFilter+"//button[@data-act = 'f']";
	String tdIdTableColumn = divGridFilter+"//td[.='Id']";
	String buttonCookieMsg = "//button[@id='btnCookie']";
	String buttonForward = divGridFilter+"//button[@data-act='f']";
	String buttonCurrent = divGridFilter+"//button[@data-act='c']";
	String buttonBack = divGridFilter+"//button[@data-act='b']";
	String tdLastRow = divGridFilterContent+"//tr[last()]";
	String buttonSelectedPage = divGridFilter+"//button[contains(@class,'awe-selected')]";
	
	public ASPnetawesomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getGridFilterRowXpathById(String id) {
		
		return divGridFilter+"//td[text()='"+id+"']/..";
	}
	
	//To return the correct next page locator since this design adjusts depending on window size
	//Returns null if next page is disabled or there is no more next page
	public String getNextPageButtonXpathByPage(int currentPage) {
		String nextPageLocator = divGridFilter+"//button[text()='"+Integer.toString(currentPage+1)+"']";
		if(!driver.findElements(By.xpath(nextPageLocator)).isEmpty())
			return	nextPageLocator;
		else if(!driver.findElements(By.xpath(buttonCurrent)).isEmpty())
			if(driver.findElement(By.xpath(buttonCurrent)).isEnabled())
				return buttonCurrent;
		return null;
	}
	
	public String getLastRowId()
	{
		return driver.findElement(By.xpath(tdLastRow+"//td[1]")).getText();
	}
	
	public void removeCookieMessage()
	{
		WebElement cookieButton = driver.findElement(By.xpath(this.buttonCookieMsg));
		if(cookieButton.isDisplayed()) cookieButton.click();
	}
	
	//should be in a common java class to be extended for all pages but since we only have one page this is okay for now
	public void scrollToElement(String locator) {
		Actions actions = new Actions(driver);
		WebElement element = driver.findElement(By.xpath(locator));
		if(element.isDisplayed()) actions.scrollToElement(element).perform();
	}
	
	public void clickElement(String locator) {
		this.scrollToElement(locator);
		driver.findElement(By.xpath(locator)).click();;
	}
	
	//should be in a common java class to be extended for all pages but since we only have one page this is okay for now
	public boolean doesElementExist(String locator) {
		
		return !driver.findElements(By.xpath(locator)).isEmpty();
	}
	
	//accepts ascending or descending only
	public void sortId(String sort){
		String sortBy = (sort.toLowerCase().contains("ascending"))?"asc":"desc";
		By tdIdTableColumnXpath = By.xpath(tdIdTableColumn);
		this.scrollToElement(tdIdTableColumn);
		String id_class = driver.findElement(tdIdTableColumnXpath).getAttribute("class");
		while(!id_class.contains("awe-"+sortBy)){
			driver.findElement(tdIdTableColumnXpath).click();
			id_class = driver.findElement(tdIdTableColumnXpath).getAttribute("class");
		}
	}
	
	public String[] returnRowDetails(String rowLocator) {
		int columnCount = driver.findElements(By.xpath(rowLocator+"//td")).size();
		String[] entry = new String[columnCount];
		for(int i=0;i<entry.length;i++)
		{
			entry[i]=driver.findElement(By.xpath(rowLocator+"//td["+Integer.toString(i+1)+"]")).getText();
		}
		return entry;
	}
	
	public String[] returnTableHeaders() {
		int columnCount = driver.findElements(By.xpath(trGridFilterHeaderRow+"//td")).size();
		String[] entry = new String[columnCount];
		for(int i=0;i<entry.length;i++)
		{
			entry[i]=driver.findElement(By.xpath(trGridFilterHeaderRow+"//td["+Integer.toString(i+1)+"]")).getText();
		}
		return entry;
	}
}