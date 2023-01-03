package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
//		try {
//			getWebDriverSelenium();
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
	}
	public static void getWebDriverSelenium() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:9090/login");
		driver.findElement(By.id("login-button")).click();
//		WebElement inputField = driver.findElement(By.name("q"));
//		inputField.sendKeys("selenium");
//		inputField.submit();
//		List<WebElement> results = driver.findElements(By.cssSelector("div.g a"));
//		for (WebElement element : results) {
//			String link = element.getAttribute("href");
//			System.out.println(link);
//		}
		Thread.sleep(5000);
		driver.quit();
	}

}
