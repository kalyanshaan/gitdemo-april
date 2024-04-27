import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TestCase0001 {

    public String baseUrl="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public WebDriver driver;

    @BeforeTest
     public void setup(){
         //Launching Browser
         System.out.println("Before Test Executed");
         driver=new ChromeDriver();
         //Maximise windows
         driver.manage().window().maximize();
         driver.get(baseUrl);
         //Implicit wait
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
     }


     @Test(priority = 1)
     public  void logOutWithInvalidCredentials() throws InterruptedException {
         //Login with ValidCredentials
         driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("admin");
         driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Admin123");
         //Login Button click
         driver.findElement(By.xpath("//button[@type='submit']")).submit();
         String message_expected = "Invalid credentials";
        String message_actual= driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
         //Assert.assertTrue(message_actual.contains(message_expected));

         Assert.assertEquals(message_expected, message_actual);

         Thread.sleep(3000);
     }
    @Test(priority = 2)
    public void loginTestWithValidCredentials(){
        //Login with ValidCredentials
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
        //Login Button click
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        // Verify if the login was successful by checking the page title
        String pageTitle= driver.getTitle();
        Assert.assertEquals("OrangeHRM",pageTitle);
        /*
         if(pageTitle.equals("OrangeHRM")){
             System.out.println("Login Successfull");
         }
         else{
             System.out.println("Login Failed!");
         }
        */

    }
     @Test(priority = 3)
     public void addEmployee() throws InterruptedException {
        //logIn();
         //find PIM Menu and click on PIM Menu
         driver.findElement(By.xpath("//span[text()='PIM']")).click();
         //find Add employee and click on Add Employee option
         driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
         //enter first name
         driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("radhe0");
        //enter middle name
         driver.findElement(By.xpath("//input[@placeholder='Middle Name']")).sendKeys("kalyan0");
         //enter last name
         driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("shyam1");

         driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
         // Verify if the employee is successfully added by checking the employee list personal details
         String confirmationMessage = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();


         if (confirmationMessage.contains("Personal Details")) {
             System.out.println("Employee added successfully!");
         } else {
             System.out.println("Failed to add employee!");
         }
         logout();

         Assert.assertEquals("Personal Details", confirmationMessage);
     }
     @Test(priority=4)
     public void searchEmployeeByName() throws InterruptedException {
       logIn();
         //find PIM Menu and click on PIM Menu
         driver.findElement(By.xpath("//span[text()='PIM']")).click();
         //Select Employee List
         driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
         driver.findElements(By.tagName("input")).get(1).sendKeys("Radha");

         //Click the search button.
         driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

         //    //span[@class='oxd-text oxd-text--span']
         Thread.sleep(5000)	;
         List<WebElement> element=	driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));

         String expected_message = "Record Found";
         String message_actual = element.get(0).getText();
         System.out.println(message_actual);

         logout();

         Assert.assertTrue(message_actual.contains(expected_message));
    }

    @Test(priority =5, enabled=false)
    public void searchEmployeeById() throws InterruptedException
    {

        String empId = "0372";
        String message_actual ="";
        logIn();


        //find PIM Menu and click on PIM Menu
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        //Select Employee List
        driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();

        //enter empoyee id
        driver.findElements(By.tagName("input")).get(2).sendKeys(empId);

        //Click the search button.
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

        Thread.sleep(2000)	;

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0," + 500 + ")");

        Thread.sleep(2000)	;


        List<WebElement> rows = driver.findElements(By.xpath("(//div[@role='row'])"));


        if(rows.size()>1)
        {
            message_actual = driver.findElement(By.xpath("((//div[@role='row'])[2]/div[@role='cell'])[2]")).getText();

        }

        logout();
        Assert.assertEquals(empId, message_actual);

    }

    @Test(priority=6,enabled = false)
    public void fileUpload() throws IOException, InterruptedException
    {
        logIn();

        //find PIM Menu and click on PIM Menu
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        //click on configuration button
        driver.findElement(By.xpath("//span[@class='oxd-topbar-body-nav-tab-item']")).click();


        //click on Data import
        driver.findElement(By.partialLinkText("Data ")).click();

        //click on browse button
        driver.findElement(By.xpath("//div[@class='oxd-file-button']")).click();


        Thread.sleep(5000);//pause of 5 seconds

        Runtime.getRuntime().exec("C://Users//ASUS//Desktop//CS_SeleniumExercises//SeleniumPractice//FileUploadOrangeHRM.exe");

        Thread.sleep(5000);

        //click on upload button
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

        logout();


    }
    @Test(priority=7, enabled=false)
    public void deleteEmployee() throws InterruptedException
    {
        logIn();

        //find PIM Menu and click on PIM Menu
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        //Select Employee List
        driver.findElement(By.xpath("//a[text()='Employee List']")).click();

        //enter employee name
        driver.findElements(By.tagName("input")).get(1).sendKeys("Odis");

        //driver.findElement(By.tagName("input")).get(1).sendKeys("Nesta");


        //Click the search button.
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();


        Thread.sleep(3000);
        ///////////////////Delete/////////////////////////

        //click on delete icon of the record
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();


        //click on yes, delete messaage button
        driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']")).click();

        //check for message "No Record Found"
        String msg = driver.findElement(By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]")).getText();

        Assert.assertEquals(msg, "No Records Found");

        Thread.sleep(5000);
        logout();

    }



    public void logIn()
    {
        //find username and enter username "Admin"
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");

        //find password and enter password admin123
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");

        //login button click
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

    }
    @Test(priority=8, enabled=false)
    public void ListEmployees() throws InterruptedException {
        logIn();
        //find PIM Menu and click on PIM Menu
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        //Select Employee List
        driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
        Thread.sleep(3000);

        //find total links
        List<WebElement> totalLinksElements = driver.findElements(By.xpath("//ul[@class='oxd-pagination__ul']/li"));

        int totalLinks = totalLinksElements.size();

        for (int i = 0; i < totalLinks; i++)//0,1,2,3,
        {

            try {
                String currentLinkText = totalLinksElements.get(i).getText();


                int page = Integer.parseInt(currentLinkText);
                System.out.println("Page: " + page);

                totalLinksElements.get(i).click();

                Thread.sleep(2000);

                List<WebElement> emp_list = driver.findElements(By.xpath("//div[@class='oxd-table-card']/div /div[4]"));

                for (int j = 0; j < emp_list.size(); j++) {
                    //print last name of each row
                    String lastName = emp_list.get(j).getText();
                    System.out.println(lastName);
                }
            } catch (Exception e) {
                System.out.println("Not a number.");
            }


        }

        Thread.sleep(5000);
        logout();

    }


    @Test(priority=9, enabled=true)
    public void applyLeave() throws InterruptedException
    {
        //find username and enter username "Admin"
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");

        //find password and enter password admin123
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");

        //login button click
        driver.findElement(By.xpath("//button[@type='submit']")).submit();


        //click on leave menu
        driver.findElement(By.linkText("Leave")).click();

        //click on Apply menu
        driver.findElement(By.linkText("Apply")).click();

        //click on leave type drop down
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")).click();

        //select CAN-FMLA option from leave type dropdown
        driver.findElement(By.xpath("//*[contains(text(),'CAN')]")).click();

        //enter from date
        driver.findElement(By.xpath("//div[@class='oxd-date-input']/input")).sendKeys("2024-08-04");


        //enter comment
        driver.findElement(By.tagName("textarea")).sendKeys("This is my personal leave");
        Thread.sleep(3000);


        //click on Apply button
        driver.findElement(By.xpath("//button[@type='submit']")).click();


        Thread.sleep(5000);
        logout();



    }




    public void logout() throws InterruptedException {
        driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
        //driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
         List<WebElement> elementList=driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
          for(int i=0;i<elementList.size();i++){
            //Thread.sleep(3000);
            System.out.println(i+":"+elementList.get(i).getText());
         }
         //click on logout
         elementList.get(3).click();

         System.out.println("Logout Successfull");

     }
     @AfterTest
    public void tearDown() throws InterruptedException {
        //logout();
        Thread.sleep(5000);
        driver.close();
        driver.quit();
     }
}
