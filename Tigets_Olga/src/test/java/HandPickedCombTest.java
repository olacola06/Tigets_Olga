import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class HandPickedCombTest {
    WebDriver wd = new ChromeDriver();

    @Test
    public void newFutureTest() {
        wd.manage().window().maximize();
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wd.navigate().to("https://www.tiqets.com/en/new-york-attractions-c260932/?show=attractions");
        click(By.xpath("//button[.='Accept All']"));

        click(By.xpath("(//div[contains(@class,'BundleCardsSlider__slide flex-box')])[1]/article[1]"));
        click(By.xpath("//div[@class='px16 py16']/a[normalize-space()='Book now']"));
        pause(2000);
        selectADay("15/12/2022");
        pause(2000);
        chooseTimeSlotFirst("12:00");
        selectTicketsFirst();
        click(By.xpath("//button[.='Go to the next step']"));
        selectADay("16/12/2022");
        pause(2000);
        chooseTimeSlotSecond("11:00");
        selectTicketsSecond();
        click(By.xpath("//button[.='Go to the next step']"));
        type(By.cssSelector("input[name='customerFirstName']"),"Olga");
        type(By.cssSelector("input[name='customerLastName']"),"Mar");
        type(By.cssSelector("input[name='customerEmail']"),"ola@gmail.com");
        type(By.cssSelector("input[name='phone-number']"),"523445699");
        click(By.xpath("//button[.='Confirm your booking']"));
        pause(5000);
        Assert.assertTrue(assertDone());
    }

    private boolean assertDone() {
        String message = wd.findElement(By.xpath("(//div[@class='CheckoutCommon__grid-mobile'])[2]")).getText();
        return message.contains("Select a payment method");
    }

    private void selectTicketsFirst() {
        wd.findElement(By.xpath("(//button[@type='button'])[8]")).click();
        wd.findElement(By.xpath("(//button[@type='button'])[15]")).click();
        click(By.xpath("//button[.='Save and continue']"));
    }
    private void selectTicketsSecond() {
        wd.findElement(By.xpath("(//button[@type='button'])[11]")).click();
        wd.findElement(By.xpath("(//button[@type='button'])[15]")).click();
        click(By.xpath("//button[.='Save and continue']"));
    }

    private void chooseTimeSlotFirst(String time) {
        WebElement el = wd.findElement(By.cssSelector("div[class='TimeslotSelect relative mt16'] "));
        el.click();
        String locator = String.format("//option[.='%s']",time);
        pause(2000);
        click(By.xpath(locator));
   }
    private void chooseTimeSlotSecond(String time) {
        WebElement el = wd.findElement(By.cssSelector("div[class='TimeslotSelect relative mt16'] "));
        el.click();
        String locator = String.format("(//option[.='%s'])[2]",time);
        pause(2000);
        click(By.xpath(locator));
    }

    private void selectADay(String date) {
        click(By.xpath("(//span[@class='mx8 text-normal'][.='Select a date'])[1]"));
        LocalDate neededDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate now = LocalDate.now();

        int diff;
        if ((neededDate.getYear() - now.getYear())!=0){
                diff = 12 - now.getMonthValue() + neededDate.getMonthValue();
            }
            else
                diff = neededDate.getMonthValue() - now.getMonthValue();
            clickNextMonth(diff);
            pause(3000);
            String locator = String.format("//div[@class='text-16']//time[.='%s']",neededDate.getDayOfMonth());
            click(By.xpath(locator));

        }
    private void clickNextMonth(int diff) {
        for (; diff > 0; diff--) {
            click(By.xpath("//button[@aria-label='Next month']"));
            pause(2000);
        }
    }

    private void pause(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void type(By locator, String message){
        WebElement e =wd.findElement(locator);
        e.click();
        e.clear();
        e.sendKeys(message);
    }

    private void click(By locator){
        wd.findElement(locator).click();
    }
}
