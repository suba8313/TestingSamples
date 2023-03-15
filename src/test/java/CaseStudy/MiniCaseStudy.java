package CaseStudy;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import commonUtils.Utility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MiniCaseStudy {
	WebDriver driver;
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	
	@BeforeTest(groups = "Run@Time")
	public void setup() throws IOException {
	    WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://www.demoblaze.com/");
		
		reports = new ExtentReports();
		spark = new ExtentSparkReporter("target\\Report.html");
		reports.attachReporter(spark);
	}
    @Test(priority=1,groups = "Run@Time")
    public void logIn() {
    	extentTest = reports.createTest("Login Test");
    	driver.get("https://www.demoblaze.com/");
    	driver.findElement(By.id("login2")).click();
    	driver.findElement(By.id("loginusername")).sendKeys("Lijo");
    	driver.findElement(By.id("loginpassword")).sendKeys("lijo@123");
    	driver.findElement(By.xpath("//button[text()='Log in']")).click();
    	boolean isDisp = driver.findElement(By.xpath("//a[text()='Welcome Lijo']")).isDisplayed();
    	String Disp = driver.findElement(By.xpath("//a[text()='Welcome Lijo']")).getText();
    	Assert.assertTrue(isDisp);
    	System.out.println(Disp);
    }
    @Test(priority=2)
    public void addItem() throws InterruptedException {
    	
    	extentTest = reports.createTest("addItem");
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//a[text()='Phones']")).click();
    	driver.findElement(By.xpath("//a[text()='Nexus 6']")).click();
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    	WebElement btn = driver.findElement(By.xpath("//a[text()='Add to cart']"));
		wait.until(ExpectedConditions.elementToBeClickable(btn));
		btn.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		
//		System.out.println(alert.getText());

    }
    @Test(priority=3,dataProvider="items",groups = "Run@Time")
    public void manyItem(String item) throws InterruptedException{
    	
    	extentTest = reports.createTest("manyItem");
    	Thread.sleep(3000);
    	driver.navigate().to("https://www.demoblaze.com/index.html");
    	//driver.findElement(By.xpath("(//a[@href=\"index.html\"])[2]")).click();
    	Thread.sleep(2000);
    	driver.findElement(By.xpath("//a[text()='Phones']")).click();
    	Thread.sleep(2000);
    	driver.findElement(By.linkText(item)).click();
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    	WebElement btn1 = driver.findElement(By.xpath("//a[text()='Add to cart']"));
		wait.until(ExpectedConditions.elementToBeClickable(btn1));
		btn1.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
	//	driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();
    }	
    
    @Test(priority=4)
    public void deleteItem() throws InterruptedException {
    	
    	extentTest = reports.createTest("DeleteItem");
    	driver.findElement(By.id("cartur")).click();
    	Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'Delete')][1]")).click();
    	Thread.sleep(2000);
  //  	List<WebElement> Deletion= driver.findElements(By.xpath(null))
    }
    
    @Test(priority=5,groups = "Run@Time")
    public void purchaseproduct() throws InterruptedException {
    	
    	extentTest = reports.createTest("Purchaseproduct");
    	Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")).click();
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Lijo");
		driver.findElement(By.xpath("//input[@id='country']")).sendKeys("India");
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Tirunelveli");
		driver.findElement(By.xpath("//input[@id='card']")).sendKeys("94628462");
		driver.findElement(By.xpath("//input[@id='month']")).sendKeys("May");
		driver.findElement(By.xpath("//input[@id='year']")).sendKeys("2023");
    	Thread.sleep(2000);
    	driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();
    	Thread.sleep(2000);
//    	driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//    	Thread.sleep(1000);
    	boolean isDisplay = driver.findElement(By.xpath("//h2[(text()='Thank you for your purchase!')]")).isDisplayed();
    	String Display = driver.findElement(By.xpath("//h2[(text()='Thank you for your purchase!')]")).getText();
    	Assert.assertTrue(isDisplay);
    	System.out.println(Display);
    	driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
    	Thread.sleep(2000);
    	
    	
     }
    
    @DataProvider(name="items")								
    public Object[][] getData() throws CsvValidationException, IOException{
  	  String path = System.getProperty("user.dir")+"//src//test//resources//ConfigFiles//product.csv";
  	  String[] cols;											
  	  CSVReader reader = new CSVReader(new FileReader(path));
  	  ArrayList<Object> dataList = new ArrayList<Object>();
  	  while((cols = reader.readNext())!=null){					
  		  Object[] record = {cols[0]};					
  		  dataList.add(record);									
  	  }	  
  	  return dataList.toArray(new Object[dataList.size()][]);
    }
       
    @AfterMethod(groups = "Run@Time")
	 public void tearDown(ITestResult result) {
		 if(ITestResult.FAILURE == result.getStatus()) {
			 extentTest.log(Status.FAIL, result.getThrowable().getMessage());
			 String strPath = Utility.getScreenshotpathO(driver);
			 extentTest.addScreenCaptureFromPath(strPath);
		 }
	 }
      @AfterTest
     public void closeApp() {
    	 driver.close();
    	 reports.flush();
     }
}


