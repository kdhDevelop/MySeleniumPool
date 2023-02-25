package xyz.kdeDevelop.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import xyz.kdeDevelop.MyUtils;

import java.io.File;
import java.time.Duration;

public class Selenium {
    private final Wait<WebDriver> driverWait;
    private final FirefoxDriver driver;

    public Selenium(String URL, String WEB_DRIVER_ID, String WEB_DRIVER_PATH, String PROFILE) {

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(true);

        FirefoxProfile firefoxProfile = new FirefoxProfile(new File(PROFILE));
        firefoxOptions.setProfile(firefoxProfile);

        driver = new FirefoxDriver(firefoxOptions);
        driver.get(URL);

        driverWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMinutes(10)).pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class);

    }

    public WebElement waitElement(String by, String name) {
        System.out.println("WAIT " + by + " " + name);

        WebElement webElement = driverWait.until(Driver -> {
            WebElement Temp = null;
            switch(by)
            {
                case "id" : Temp = Driver.findElement(By.id(name));
                    break;
                case "className" : Temp = Driver.findElement(By.className(name));
                    break;
                case "xpath" : Temp = Driver.findElement(By.xpath(name));
                    break;
                case "cssSelector" : Temp = Driver.findElement(By.cssSelector(name));
                    break;
                case "linkText" : Temp = Driver.findElement(By.linkText(name));
                    break;
                case "partialLinkText" : Temp = Driver.findElement(By.partialLinkText(name));
                    break;
            }
            return Temp;
        });

        MyUtils.sleep(1);

        return webElement;
    }

    public WebElement getElement(String by, String name) {
        System.out.println("GET " + by + " " + name);

        return waitElement(by, name);
    }

    public void clearCookie() {
        System.out.println("CLEAR COOKIE");
        MyUtils.sleep(5);
        driver.manage().deleteAllCookies();
        MyUtils.sleep(5);
    }

    public void removeAlert() {
        System.out.println("REMOVE ALERT");
        driverWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void sendClick(String by, String name) {
        System.out.println("SEND CLICK " + by + " " + name);
        getElement(by, name).click();
    }

    public void sendKeys(String by, String name, String keys) {
        System.out.println("SEND KEYS " + by + " " + name + " " + keys);
        getElement(by, name).sendKeys(keys);
    }

    public void runJsFunction(String functionName) {
        System.out.println("RUN JS FUNCTION " + functionName);
        driver.executeScript(functionName);
    }

    public void url(String url) {
        System.out.println("URL " + url);
        driver.get(url);
    }

    public boolean is404() {
        WebElement webElement = driverWait.until(Driver -> {
            WebElement temp = driver.findElement(By.xpath("/html/body"));
            return temp;
        });

        return webElement.getText().equals("404 Not Found");
    }

    public void quitDriver() {
        driver.close();
    }
}