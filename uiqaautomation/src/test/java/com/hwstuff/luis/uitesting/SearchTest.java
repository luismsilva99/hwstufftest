package com.hwstuff.luis.uitesting;

import com.hwstuff.luis.uitesting.helpers.ListHelper;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SearchTest {


    @Test
    public void smartphoneDimensionsTest() throws InterruptedException {

        // Create a new instance of the Firefox driver
        WebDriver driver = new ChromeDriver();
        // visit hostelworld
        driver.get("http://www.hostelworld.com");

        driver.manage().window().setSize(new Dimension(412, 732));

        // find the text input element by its name
        WebElement element = driver.findElement(By.id("home-search-keywords"));

        // enter the place to search
        element.sendKeys("Dublin, Ireland");

        (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement searchResult = d.findElement(By.linkText("Dublin, Ireland"));
                return searchResult.getText().equals("Dublin, Ireland");
            }
        });

        // submit the form
        element.submit();

        // wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("in Dublin");
            }
        });

        // wait for sort dd to load
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath("//dd[@class='sort-button']")).getText().equals("SORT");
            }
        });

        // click on the sort button
        WebElement sortDropdown = driver.findElement(By.xpath("//dd[@class='sort-button']"));
        sortDropdown.click();

        // wait for the dropdown to load
        (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath("//li[@id='sortByName']")).getText().equals("Name");
            }
        });

        // click on the Name text
        WebElement sortByNameElement = driver.findElement(By.xpath("//li[@id='sortByName']"));
        sortByNameElement.click();

        // get all listed hostels name
        List<WebElement> listedHostels = driver.findElements(By.xpath("//div[@id=\"fabResultsContainer\"]/div/div/h2/a"));
        List<String> hostelNameList = new ArrayList<String>(listedHostels.size());
        for (WebElement webElement : listedHostels) {
            hostelNameList.add(webElement.getText());
        }

        Assert.assertTrue(ListHelper.isSorted(hostelNameList));

        // close the browser
        driver.quit();
    }

    @Test
    public void pcDimensionsTest() throws InterruptedException {

        // Create a new instance of the Firefox driver
        WebDriver driver = new ChromeDriver();
        // visit hostelworld
        driver.get("http://www.hostelworld.com");

        driver.manage().window().setSize(new Dimension(768, 1024));

        // find the text input element by its name
        WebElement element = driver.findElement(By.id("home-search-keywords"));

        // enter the place to search
        element.sendKeys("Dublin, Ireland");

        (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement searchResult = d.findElement(By.linkText("Dublin, Ireland"));
                return searchResult.getText().equals("Dublin, Ireland");
            }
        });

        // submit the form
        element.submit();

        // wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("in Dublin");
            }
        });

        // wait for sort dd to load
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath("//dd[@class='topfilter rounded sort-button']")).getText().equals("Sort");
            }
        });

        // click on the sort button
        WebElement sortDropdown = driver.findElement(By.xpath("//dd[@class='topfilter rounded sort-button']"));
        sortDropdown.click();

        // wait for the dropdown to load
        (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath("//li[@id='sortByName']")).getText().equals("Name");
            }
        });

        // click on the Name text
        WebElement sortByNameElement = driver.findElement(By.xpath("//li[@id='sortByName']"));
        sortByNameElement.click();

        // get all listed hostels name
        List<WebElement> listedHostels = driver.findElements(By.xpath("//div[@id=\"fabResultsContainer\"]/div/div/h2/a"));
        List<String> hostelNameList = new ArrayList<String>(listedHostels.size());
        for (WebElement webElement : listedHostels) {
            hostelNameList.add(webElement.getText());
        }

        Assert.assertTrue(ListHelper.isSorted(hostelNameList));

        // close the browser
        driver.quit();
    }

    @Test
    public void fullScreenSearchTest() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        WebDriver driver = new ChromeDriver(options);

        driver.get("http://www.hostelworld.com");

        // find the text input element by its name
        WebElement element = driver.findElement(By.id("home-search-keywords"));

        // enter the place to search
        element.sendKeys("Dublin, Ireland");

        (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement searchResult = d.findElement(By.linkText("Dublin, Ireland"));
                return searchResult.getText().equals("Dublin, Ireland");
            }
        });

        // submit the form
        element.submit();

        // wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("in Dublin");
            }
        });


        // wait for sort dd to load
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath("//dd[@class='topfilter rounded sort-button']")).getText().equals("Sort");
            }
        });

        // click on the sort button
        WebElement sortDropdown = driver.findElement(By.xpath("//dd[@class='topfilter rounded sort-button']"));
        sortDropdown.click();

        // wait for the dropdown to load
        (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath("//li[@id='sortByName']")).getText().equals("Name");
            }
        });

        // click on the Name text
        WebElement sortByNameElement = driver.findElement(By.xpath("//li[@id='sortByName']"));
        sortByNameElement.click();

        // get all listed hostels name
        List<WebElement> listedHostels = driver.findElements(By.xpath("//div[@id=\"fabResultsContainer\"]/div/div/h2/a"));
        List<String> hostelNameList = new ArrayList<String>(listedHostels.size());
        for (WebElement webElement : listedHostels) {
            hostelNameList.add(webElement.getText());
        }

        Assert.assertTrue(ListHelper.isSorted(hostelNameList));

        // close the browser
        driver.quit();
    }
}
