package demo;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
public class TestCases {
    ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01(){
        System.out.println("Start Test case: testCase01");
       

        
       searchItems(driver, "Washing Machine", "Popularity");

        
        int count = getFoutStarRating(driver);
       System.out.println("Number of washing machines with less than or equal to 4 stars: " + count);

        
  printItemsWithDiscount(driver, "iPhone", 17);

      
        printTopRatedItems(driver, "Coffee Mug", 4, 5);

        System.out.println("end Test case: testCase02");
    }
    
    public static void searchItems(WebDriver driver, String product, String sortBy) {
    	 driver.get("https://www.flipkart.com");
    	WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.click();
       
        searchBox.clear();
        
        searchBox.sendKeys(product);
        searchBox.submit();
       
    
        driver.findElement(By.xpath("//div[contains(text(),'" + sortBy + "')]")).click();
        
    }

   
    public static int getFoutStarRating(WebDriver driver) {
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	driver.get("https://www.flipkart.com");
    	List<WebElement> ratingElements = driver.findElements(By.xpath("//div[@class='_5OesEi']"));
    	int count = 0;
    	for (WebElement ratingElement : ratingElements) {
    	    WebElement ratingSpan = ratingElement.findElement(By.tagName("span"));
    	    double rating = Double.parseDouble(ratingSpan.getText());
    	    if (rating <= 4.0) {
    	        count++;
    	    }
    	}
        return count;
    }

    
    public static void printItemsWithDiscount(WebDriver driver, String searchQuery, int discountThreshold) {
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	driver.get("https://www.flipkart.com");
    	searchItems(driver, searchQuery, "Popularity");

        List<WebElement> items = driver.findElements(By.xpath("//div[@class='_75nlfW']"));
        for (WebElement item : items) {
          
                WebElement titleElement = item.findElement(By.xpath("//div[@class='KzDlHZ']"));
                WebElement discountElement = item.findElement(By.xpath("//div[@class='UkUFwK']"));
                
                String title = titleElement.getText();
                String discount = discountElement.getText();
                int discountPercent = Integer.parseInt(discount.replace("% off", ""));
                
                if (discountPercent > discountThreshold) {
                    System.out.println("Title: " + title + ", Discount: " + discount);
    }

        }
    }
    public static void printTopRatedItems(WebDriver driver, String searchQuery, int minRating, int count) {
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	driver.get("https://www.flipkart.com");
    	 WebElement searchBox = driver.findElement(By.name("q"));
         searchBox.sendKeys(searchQuery);
         searchBox.submit();
         WebElement selectRating = driver.findElement(By.xpath("//*[@id='container']/div/div[3]/div[1]/div[1]/div/div/div/section[5]/div[2]/div/div[1]/div/label/div[1]"));
         //((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectRating);
         selectRating.click();
         List<WebElement> items = driver.findElements(By.xpath("//div[@class='XQDdHH']"));
         for (int i = 1; i < count && i < items.size(); i++) {
        	 WebElement item = items.get(i);
             String title = driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div[2]/div[2]/div/div["+i+"]/div/a[2]")).getText();
             String imageUrl = driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div[2]/div[2]/div/div["+i+"]/div/a[1]")).getAttribute("href");
             System.out.println("Title: " + title + ", Image URL: " + imageUrl);
        }
    }


}
