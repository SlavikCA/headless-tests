import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.Assert
import org.testng.annotations.*
import java.util.logging.Level

class SimpleTest {

    @DataProvider(name = 'browsers')
    static Object[][] browsers() {
        [
                [ { SimpleTest.startChromeDriver() } ] ,
                [ { SimpleTest.startFirefoxDriver() } ]
        ]
    }

    static WebDriver startFirefoxDriver () {
        FirefoxBinary firefoxBinary = new FirefoxBinary()
        firefoxBinary.addCommandLineOptions("--headless")
        FirefoxOptions firefoxOptions = new FirefoxOptions()
        firefoxOptions.setBinary(firefoxBinary)
        RemoteWebDriver driver = new FirefoxDriver(firefoxOptions)
        Capabilities cap = driver.getCapabilities()
        println "*** Testing with browser: ${cap.browserName} ${cap.version} on OS: ${cap.platform}"
        return driver
    }

    static WebDriver startChromeDriver () {
        // https://developers.google.com/web/updates/2017/04/headless-chrome
        ChromeOptions options = new ChromeOptions()
        options.addArguments('--headless') //, '--disable-gpu', '--no-sandbox', 'window-size=1024,768', '--disable-extensions')
        LoggingPreferences logPrefs = new LoggingPreferences()
        logPrefs.enable(LogType.BROWSER, Level.ALL)
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
        RemoteWebDriver driver = new ChromeDriver(options)
        Capabilities cap = driver.getCapabilities()
        println "*** Testing with browser: ${cap.browserName} ${cap.version} on OS: ${cap.platform}"
        return driver
    }

    @BeforeSuite
    void beforeSuite() {
        WebDriverManager.chromedriver().setup()
        WebDriverManager.firefoxdriver().setup()
    }


    @Test(dataProvider='browsers')
    void titleOfPage(Closure c) {
        WebDriver driver = c()
        driver.get 'https://www.google.com/'
        String title = driver.title
        println "Page's title: $title"
        Assert.assertEquals(title, 'Google')
        stopDriver(driver)
    }

    void stopDriver (WebDriver driver) {
        if (driver != null) {
            driver.quit()
        }
    }
}
