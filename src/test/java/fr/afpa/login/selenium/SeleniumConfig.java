package fr.afpa.login.selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

// @Configuration
public class SeleniumConfig {
    
    private WebDriver driver;
    private String serverUrl = "http://localhost:8888";

    public SeleniumConfig() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }
    
    public WebDriver getWebDriver() {
        return driver;
    }

    public String getServerUrl() {
        return serverUrl;
    }
}
