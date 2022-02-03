package test;

import org.openqa.selenium.By;

    import org.openqa.selenium.WebElement;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.testng.annotations.BeforeTest;

    import javax.imageio.ImageIO;
    import java.awt.image.BufferedImage;
    import java.io.File;
    import java.io.IOException;
    import java.net.URL;
    import java.util.List;
    import java.util.Properties;
    import java.util.concurrent.TimeUnit;
import test.helpers.Support;


public class Test {


    @BeforeTest
    public void setSystemProperties() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/Users/anshikaagrawal/Downloads/chromedriver");
    }


    @org.testng.annotations.Test
    public void saveFirstImage() throws InterruptedException, IOException {
        ChromeDriver driver = new ChromeDriver();
        Support support = new Support();
        Properties prop = support.readPropertiesFile("testing.properties");
        driver.get(prop.getProperty("googleLink"));
        driver.manage().window().maximize();
        WebElement searchElement = driver.findElement(By.name("q"));

        searchElement.sendKeys("Selen");
        driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
        List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@class='G43f7e']//li//span"));
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        for (WebElement element : suggestions) {
            if (element.getText().equalsIgnoreCase("selenium")) {
                element.click();
                break;
            }
        }
        outerloop:
        for (int i = 0; i < 10; i++) {
            List<WebElement> results = driver.findElements(By.xpath("//div[@class='yuRUbf']//a//h3"));


            for (WebElement res : results) {
                if (res.getText().contains("Selenium (software) - Wikipedia")) {
                    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                    res.click();
                    break outerloop;
                }
            }

            driver.findElement(By.id("pnnext")).click();
        }


        List<WebElement> images = driver.findElements(By.xpath("//img"));
        BufferedImage saveImage = ImageIO.read(new URL(images.get(0).getAttribute("src")));

        ImageIO.write(saveImage, "png", new File(prop.getProperty("ImageLocation")));
    }

}