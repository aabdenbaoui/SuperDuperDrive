package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTesting {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }
    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }
    @Test
    public void getSignUpPage(){
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password){
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        WebElement inputFirstName = getWebElement(webDriverWait, "inputFirstName");
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        WebElement inputLastName = getWebElement(webDriverWait, "inputLastName");
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        WebElement inputUsername = getWebElement(webDriverWait, "inputUsername");
        inputUsername.click();
        inputUsername.sendKeys(userName);

        WebElement inputPassword = getWebElement(webDriverWait, "inputPassword");
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        WebElement buttonSignUp = getWebElement(webDriverWait, "buttonSignUp");
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
        WebElement successMessage = driver.findElement(By.id("success-msg"));
        Assertions.assertTrue(successMessage.getText().contains("You successfully signed up!"));
    }
    @Test
    public void testSignUpPage(){
        doMockSignUp("UserF", "UserL", "user", "user");
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password)
    {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    @Test
    public void WriteTestThatCreatesSetCredentialsVerifiesThatAreDisplayedAndVerifiesThatDisplayedPasswordEncrypted(){
        doMockSignUp("UserTest01F", "UserTest01L", "userName01", "passFake");
        doLogIn("userName01", "passFake");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        getWebElement(webDriverWait, "nav-credentials-tab").click();

//        add-new-credential-id
        WebElement addNewCredential = getWebElement(webDriverWait, "add-new-credential-id");
        addNewCredential.click();
//        credential-url
        WebElement credentialUrl = getWebElement(webDriverWait, "credential-url");
        credentialUrl.click();
        credentialUrl.sendKeys("jdbc:h2:mem:testdb");

        WebElement credentialUserName = getWebElement(webDriverWait, "credential-username");
        credentialUserName.click();
        credentialUserName.sendKeys("userName01");

        WebElement credentialPassword = getWebElement(webDriverWait, "credential-password");
        credentialPassword.click();
        credentialPassword.sendKeys("passFake01");
       //submit the result
        WebElement noteSubmit = getWebElement(webDriverWait, "credential-submit-save");
        noteSubmit.click();

        //click on crednetial-nav-tab
        getWebElement(webDriverWait, "nav-credentials-tab").click();

        WebElement credentialTextIdUrl =  getWebElement(webDriverWait, "credentialTextIdUrl");
        credentialTextIdUrl.click();
        Assertions.assertTrue(credentialTextIdUrl.getText().equals("jdbc:h2:mem:testdb"));
        Assertions.assertFalse(credentialTextIdUrl.getText().equals("jdbc:h2:mem:testdb:will:fail"));
        WebElement credentialTextIdUserName = getWebElement(webDriverWait, "credentialTextIdUserName");
        credentialTextIdUserName.click();
        Assertions.assertTrue(credentialTextIdUserName.getText().equals("userName01"));
        Assertions.assertFalse(credentialTextIdUserName.getText().equals("userName02"));

        WebElement credentialTextIdPassword = getWebElement(webDriverWait, "credentialTextIdPassword");
        credentialTextIdPassword.click();
        //test if the string is 24 and is password is not displayed in original text
        Assertions.assertTrue(credentialTextIdPassword.getText().length() == 24);
        Assertions.assertFalse(credentialTextIdPassword.getText().equals("passFake01"));
    }

    @Test
    public void WriteTestThatDeletesAExistingSetOfCredentialsAndVerifiesThatCredentialsLongerDisplayed(){
       doMockSignUp("UserTest01F", "UserTest01L", "userName03", "passFake");
       doLogIn("userName03", "passFake");

       WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

       getWebElement(webDriverWait, "nav-credentials-tab").click();

//        add-new-credential-id
       WebElement addNewCredential = getWebElement(webDriverWait, "add-new-credential-id");
       addNewCredential.click();
//        credential-url
       WebElement credentialUrl = getWebElement(webDriverWait, "credential-url");
       credentialUrl.click();
       credentialUrl.sendKeys("jdbc:h2:mem:testdb");

       WebElement credentialUserName = getWebElement(webDriverWait, "credential-username");
       credentialUserName.click();
       credentialUserName.sendKeys("userName01");

       WebElement credentialPassword = getWebElement(webDriverWait, "credential-password");
       credentialPassword.click();
       credentialPassword.sendKeys("passFake01");
       //submit the result
       WebElement noteSubmit = getWebElement(webDriverWait, "credential-submit-save");
       noteSubmit.click();

       //click on crednetial-nav-tab
       getWebElement(webDriverWait, "nav-credentials-tab").click();

       WebElement credentialTextIdUrl =  getWebElement(webDriverWait, "credentialTextIdUrl");
       credentialTextIdUrl.click();
       Assertions.assertTrue(credentialTextIdUrl.getText().equals("jdbc:h2:mem:testdb"));
       Assertions.assertFalse(credentialTextIdUrl.getText().equals("jdbc:h2:mem:testdb:will:fail"));
       WebElement credentialTextIdUserName = getWebElement(webDriverWait, "credentialTextIdUserName");
       credentialTextIdUserName.click();
       Assertions.assertTrue(credentialTextIdUserName.getText().equals("userName01"));
       Assertions.assertFalse(credentialTextIdUserName.getText().equals("userName02"));

       WebElement credentialTextIdPassword = getWebElement(webDriverWait, "credentialTextIdPassword");
       credentialTextIdPassword.click();
       //test if the string is 24 and is password is not displayed in original text
       Assertions.assertTrue(credentialTextIdPassword.getText().length() == 24);
       Assertions.assertFalse(credentialTextIdPassword.getText().equals("passFake01"));

       getWebElement(webDriverWait, "nav-credentials-tab").click();

       getWebElement(webDriverWait, "delete-credential-id").click();

        getWebElement(webDriverWait, "nav-credentials-tab").click();

        WebElement emptyUrlId = getWebElement(webDriverWait, "empty-url-id");
       Assertions.assertEquals(emptyUrlId.getText(), "Example Credential URL");

   }
   @Test
   public void writeTestThatViewsExistingSetCredentialsVerifiesThatTheViewablePasswordIsUnencryptedEditsTheCredentialsAndVerifiesThatTheChangesAreDisplayed() {
       doMockSignUp("UserTest01F", "UserTest01L", "userName02", "passFake");
       doLogIn("userName02", "passFake");

       WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

       getWebElement(webDriverWait, "nav-credentials-tab").click();

//        add-new-credential-id
       WebElement addNewCredential = getWebElement(webDriverWait, "add-new-credential-id");
       addNewCredential.click();
//        credential-url
       WebElement credentialUrl = getWebElement(webDriverWait, "credential-url");
       credentialUrl.click();
       credentialUrl.sendKeys("jdbc:h2:mem:testdb");

       WebElement credentialUserName = getWebElement(webDriverWait, "credential-username");
       credentialUserName.click();
       credentialUserName.sendKeys("userName01");

       WebElement credentialPassword = getWebElement(webDriverWait, "credential-password");
       credentialPassword.click();
       credentialPassword.sendKeys("passFake01");
       //submit the result
       getWebElement(webDriverWait, "credential-submit-save").click();

       //click on crednetial-nav-tab
       getWebElement(webDriverWait, "nav-credentials-tab").click();

       WebElement credentialTextIdUrl =  getWebElement(webDriverWait, "credentialTextIdUrl");
       credentialTextIdUrl.click();
       Assertions.assertTrue(credentialTextIdUrl.getText().equals("jdbc:h2:mem:testdb"));
       Assertions.assertFalse(credentialTextIdUrl.getText().equals("jdbc:h2:mem:testdb:will:fail"));
       WebElement credentialTextIdUserName = getWebElement(webDriverWait, "credentialTextIdUserName");
       credentialTextIdUserName.click();
       Assertions.assertTrue(credentialTextIdUserName.getText().equals("userName01"));
       Assertions.assertFalse(credentialTextIdUserName.getText().equals("userName02"));

       WebElement credentialTextIdPassword = getWebElement(webDriverWait, "credentialTextIdPassword");
       credentialTextIdPassword.click();
       //test if the string is 24 and is password is not displayed in original text
       Assertions.assertTrue(credentialTextIdPassword.getText().length() == 24);
       Assertions.assertFalse(credentialTextIdPassword.getText().equals("passFake01"));

       getWebElement(webDriverWait, "nav-credentials-tab").click();
       getWebElement(webDriverWait, "update-credential-id").click();
       WebElement credentialUrlEdit = getWebElement(webDriverWait, "credential-url");
       credentialUrlEdit.click();
       credentialUrlEdit.clear();
       credentialUrlEdit.click();
       credentialUrlEdit.sendKeys("jdbc:h2:mem:testdb01");

       WebElement credentialUserNameEdit = getWebElement(webDriverWait, "credential-username");
       credentialUserNameEdit.click();
       credentialUserNameEdit.clear();
       credentialUserNameEdit.click();
       credentialUserNameEdit.sendKeys("userName02");

       WebElement credentialPasswordEdit = getWebElement(webDriverWait, "credential-password");
       credentialPasswordEdit.click();
       credentialPasswordEdit.clear();
       credentialPasswordEdit.click();
       credentialPasswordEdit.sendKeys("passFake01");
       //submit the result
       getWebElement(webDriverWait, "credential-submit-save").click();

       getWebElement(webDriverWait, "nav-credentials-tab").click();

       WebElement credentialTextIdUrlEdit =  getWebElement(webDriverWait, "credentialTextIdUrl");
       credentialTextIdUrlEdit.click();
       Assertions.assertTrue(credentialTextIdUrlEdit.getText().equals("jdbc:h2:mem:testdb01"));
       Assertions.assertFalse(credentialTextIdUrlEdit.getText().equals("jdbc:h2:mem:testdb:will:fail"));
       WebElement credentialTextIdUserNameEdit = getWebElement(webDriverWait, "credentialTextIdUserName");
       credentialTextIdUserNameEdit.click();
       Assertions.assertTrue(credentialTextIdUserNameEdit.getText().equals("userName02"));
       Assertions.assertFalse(credentialTextIdUserNameEdit.getText().equals("userName01"));


   }

   private WebElement getWebElement(WebDriverWait webDriverWait, String elementId){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
        return  driver.findElement(By.id(elementId));
    }

}
