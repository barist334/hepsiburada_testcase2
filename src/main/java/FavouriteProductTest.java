import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FavouriteProductTest {

    /*
    Senaryo 2
    • Uygulama açılır.
    • Anasayfada Süper Fiyat, Süper Teklif’e tıklanır.
    • Süper Fiyat, Süper Teklif sayfasından bir ürüne gidilir.(Birden fazla görseli olan ürün
    seçilir.)
    • Ürün detayda ürünün görseline tıklanır ve yana doğru scroll edilir.
    • Görsel ekranı kapatılır.
    • Ürün detaydan favori butonuna basılır.
    • Açılan login sayfasından login olunur ve login olunduğu kontrol edilir.
    • Beğendiklerim sayfasına gidilip ürünün beğendiklerime eklendi görülür.
     */

    private AndroidDriver driver;
    private WebDriverWait wait;
    String folderName;
    DateFormat df;

    private final By superPrice = By.id("com.pozitron.hepsiburada:id/layoutAll");
    private final By superPriceSelectProduct = By.id("com.pozitron.hepsiburada:id/tv_product_list_item_name");
    private final By selectedProductClickImage = By.id("com.pozitron.hepsiburada:id/productImage");
    private final By productImageScrollOne = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[2]/android.widget.ImageView");
    private final By productImageScrollTwo = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[3]/android.widget.ImageView");
    private final By productImageScrollThree = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[4]/android.widget.ImageView");
    private final By productImageClose = By.xpath("//android.widget.ImageView[@content-desc='Geri']");
    private final By productClickFavourite = By.xpath("//android.widget.ImageView[@content-desc='favoriye ekle']");
    private final By loginWithEMail = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText");
    String email = "hbtesting1@mailinator.com";
    private final By sendEMailButton = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.widget.Button");
    private final By loginWithPassword = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText");
    String password = "Hbtesting123.";
    private final By loginButton = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.widget.Button");
    private final By loginOkPopUp = By.id("android:id/button1");
    private final By backButton = By.xpath("//android.widget.ImageView[@content-desc='Geri']");
    private final By userMenuButton = By.id("com.pozitron.hepsiburada:id/iv_toolbar_user_account_menu");
    private final By favouritesTab = By.xpath("//android.view.ViewGroup[@content-desc=\"account_menu_5\"]/android.widget.TextView");
    private final By productCapture = By.xpath("//android.view.View[@content-desc=\"123\"]/android.view.View/android.view.View/android.view.View");

    /*
    public void swipeUp(int fromX, int fromY, int toX, int toY){
        TouchAction touchAction = new TouchAction(driver);

        touchAction.press(PointOption.point(fromX,fromY))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(3000)))
                .moveTo(PointOption.point(toX,toY))
                .release()
                .perform();
    }
    */

    public void captureScreenshots() throws IOException {
        folderName = "screenshot";
        File f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        df = new SimpleDateFormat("dd-MM-yyyy__hh_mm_ssaa");
        new File(folderName).mkdir();
        String fileName = df.format(new Date())+ ".png";
        FileUtils.copyFile(f,new File(folderName+ "/" + fileName));
    }

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:app", "C:\\Users\\baris\\Downloads\\Hepsiburada_ Online Alışveriş_5.4.0_apkcombo.com.apk");
        desiredCapabilities.setCapability("appium:appPackage", "com.pozitron.hepsiburada");
        desiredCapabilities.setCapability("appium:deviceName", "sdk gphone64_x86_64");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:automationName", "UIAutomator2");
        desiredCapabilities.setCapability("appium:appActivity", "com.hepsiburada.ui.startup.SplashActivity");
        desiredCapabilities.setCapability("appium:noReset", false);
        desiredCapabilities.setCapability("appium:fullReset", true);
        desiredCapabilities.setCapability("appium:autoGrantPermissions", true);


        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        wait = new WebDriverWait(driver,15);
        driver.manage().timeouts().implicitlyWait(15000, TimeUnit.SECONDS);

    }

    public void swipeUp(){
        TouchAction action =new TouchAction(driver);
        Dimension size	=driver.manage().window().getSize();
        int width=size.width;
        int height=size.height;
        int middleOfX=width * 1/2;
        int startYCoordinate= (int)(height*2/3); //ortalaam 1890
        int endYCoordinate= (int)(height*1/4); //ortalama 600
        System.out.println("Width:" + width);
        System.out.println("Height:" + height);
        System.out.println("MiddleOfX:" + middleOfX);
        System.out.println("Start Y Coordinate:" + startYCoordinate);
        System.out.println("End Y Coordinate:" + endYCoordinate);
        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
    }


    @Test
    public void sampleTest() throws IOException, InterruptedException {
        swipeUp();
        wait.until(ExpectedConditions.visibilityOfElementLocated(superPrice)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(superPriceSelectProduct)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectedProductClickImage)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productImageScrollOne)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productImageScrollTwo)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productImageScrollThree)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productImageClose)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productClickFavourite)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginWithEMail)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(sendEMailButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginWithPassword)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginOkPopUp)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(backButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(userMenuButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(favouritesTab)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCapture));
        captureScreenshots();
        Thread.sleep(5000);
    }

    @AfterTest
    public void close(){
        driver.quit();
    }

}
