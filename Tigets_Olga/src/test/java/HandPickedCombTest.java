import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

        click(By.xpath("//a[normalize-space()='Statue of Liberty + Empire State Building']"));
        click(By.xpath("//div[@class='px16 py16']/a[normalize-space()='Book now']"));
        pause(2000);
        hideContainer("document.querySelector('#onetrust-group-container').style.display='none'");
        selectADay("15/12/2022");
        pause(2000);
        chooseTimeSlot("12:00");
        selectTickets();

    }

    private void selectTickets() {
        wd.findElement((By.cssSelector("button[class='py12 px16 w100 flex-box borders-solid-1 opacity-100 border-grey500 mt16 mb16" +
                " reset-button text-bold text-ink500 outline@focus round-corners-4 reset-button']"))).click();
        wd.findElement(By.xpath("(//button[@type='button'])[15]")).click();

    }

    private void chooseTimeSlot(String time) {
        WebElement el = wd.findElement(By.cssSelector("div[class='TimeslotSelect relative mt16'] "));
        el.click();
        String locator = String.format("option[value='%s']",time);
        pause(2000);
        click(By.cssSelector(locator));
        wd.navigate().refresh();
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
            // //button[@aria-label='Next month']//*[name()='svg']
        }
    }
    private void pause(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void hideContainer(String locator) {
        JavascriptExecutor js = (JavascriptExecutor) wd;
        js.executeScript(locator);
    }

    private void click(By locator){
        wd.findElement(locator).click();
    }


}

//        String message = wd.findElement(By.cssSelector("div[class='lead-name']")).getText();
//        Assert.assertTrue(message.contains("Post-Pay Per Lead"));
//
//        List<WebElement> list = wd.findElements(By.cssSelector("div[class='selected-categories-list'] div"));
//        System.out.println(list.size());
//        int sizeList = list.size();
//
//        if(sizeList!=2) {
//                String locator = "div[class='suggested-categories-list'] div[class='category-item ']:nth-child("+1+") input";
//                wd.findElement(By.cssSelector(locator)).click();
//                pause(10000);
//            list = wd.findElements(By.cssSelector("div[class='selected-categories-list'] div"));
//            }
//
//        System.out.println(list.size());
//        Assert.assertEquals(list.size(), 2);
//
//    }
//}
