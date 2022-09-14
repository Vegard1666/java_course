package ru.javacourse.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.time.Duration;
import java.util.Objects;

public class ApplicationManager {
  private final Browser browser;
  WebDriver wd;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private JavascriptExecutor js;

  public ApplicationManager(Browser browser) {
    this.browser = browser;
  }

  public void init() {
    if (Objects.equals(browser, Browser.FIREFOX)) {
      wd = new FirefoxDriver();
    } else if (Objects.equals(browser, Browser.CHROME)) {
      wd = new ChromeDriver();
    } else if (Objects.equals(browser, Browser.EDGE)) {
      wd = new EdgeDriver();
    }
//    System.setProperty("webdriver.chrome.driver", "D:\\Git\\java_course\\sandbox\\chromedriver.exe");
    wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    js = (JavascriptExecutor) wd;
    wd.get("http://localhost/addressbook/group.php");
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login("admin", "secret");
  }

  public void stop() {
    wd.quit();
  }

  public boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }
}
