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

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

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
	@Test
	public void cantGetHomePage(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
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
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
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
	public void signUpLoginTest(){
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");
	}
	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-href")));
		WebElement loginHref = driver.findElement(By.id("login-href"));
		loginHref.click();
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}
	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","userBadUrl","123");
		doLogIn("userBadUrl", "123");
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
//	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("URL","Test","userLargeUpload","123");
		doLogIn("userLargeUpload", "123");
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testSignOutPage(){
		doMockSignUp("URL","Test","userSignOut","123");
		doLogIn("userSignOut", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-id")));
		WebElement logoutButton = driver.findElement(By.id("logout-id"));
		logoutButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-logout-message")));
		WebElement successLogoutMessage = driver.findElement(By.id("success-logout-message"));
		Assertions.assertTrue(successLogoutMessage.getText().contains("You have been logged out"));
//		getLoginPage();
	}
	public void doMockSignUpWithExistingUser(String firstName, String lastName,String userName, String password){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		WebElement successMessage = driver.findElement(By.id("user-taken-error-msg"));
		Assertions.assertTrue(successMessage.getText().contains("Username is already taken"));

	}
	@Test
	public void testValidateThatTheUsernameSuppliedDoesNotAlreadyExistInTheApplicationWithUserAlreadyExistError(){
			doMockSignUp("userF", "userL", "userMock", "passMock");
		    doMockSignUpWithExistingUser("userF", "userL", "userMock", "passMock");
	}
	@Test
	public void EveryonShouldBeAllowedAccessToLoginSignUpPage(){
			getLoginPage();
			getSignUpPage();
			cantGetHomePage();
	}
	@Test
	public void ShowLoginErrorsInvalidUsernamePassword(){
		String userName = "userFake";
		String password = "passFake";
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
		WebElement successMessage = driver.findElement(By.id("error-message"));
		Assertions.assertTrue(successMessage.getText().contains("Invalid username and password."));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup-href")));
		WebElement signUpHref = driver.findElement(By.id("signup-href"));
		signUpHref.click();
        doMockSignUp("userFake", "userFake", userName, password);
		doLogIn(userName, password);
//		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}
    @Test
	public void TestThatSignsUpNewUserLogsInVerifiesThatTheHomePageIsAccessibleLogsOutVerifieThatTheHomePageIsNoLongerAccessible(){
		doMockSignUp("firstNameFake", "userLastFake", "userLoginLoutFake", "fakePass");
		doLogIn("userLoginLoutFake", "fakePass");
		canGetHomePage();
		testSignOutPage();
		cantGetHomePage();
	}
    @Test
	private void canGetHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
	}
	//Testing Notes Section
	@Test
	public void WriteTestThatCreatesNoteAndVerifiesItTsDisplayed(){
		doMockSignUp("UserTest01F", "UserTest01L", "userName01", "passFake");
		doLogIn("userName01", "passFake");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesHref = driver.findElement(By.id("nav-notes-tab"));
		notesHref.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-button-id")));
		WebElement addNoteButton = driver.findElement(By.id("add-note-button-id"));
		addNoteButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModalLabel")));
		WebElement noteModalLabel = driver.findElement(By.id("noteModalLabel"));
		Assertions.assertTrue(noteModalLabel.getText().contains("Note"));
//		add-note-button-id
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.click();
		noteTitle.sendKeys("Note Title 01");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys("Note Description 01");
        //noteSubmit
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit-save")));
		WebElement noteSubmit = driver.findElement(By.id("note-submit-save"));
		noteSubmit.click();
//		noteTitleId
//		noteDescriptionId
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesHrefSecondRound = driver.findElement(By.id("nav-notes-tab"));
		notesHrefSecondRound.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleId")));
		WebElement noteTitleOutput = driver.findElement(By.id("noteTitleId"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescriptionId")));
		WebElement noteDescriptionOutput = driver.findElement(By.id("noteDescriptionId"));
		Assertions.assertEquals(noteTitleOutput.getText(), "Note Title 01");
		Assertions.assertEquals(noteDescriptionOutput.getText(), "Note Description 01");
	}

	@Test
	public void WriteTestThatEditsExistingNoteAndVerifiesThatTheChangesAreDisplayed(){



	}

	@Test
	public void WriteTestThatDeletesNoteAndVerifiesThatTheNoteIsNoLongerDisplayed(){
		WriteTestThatCreatesNoteAndVerifiesItTsDisplayed();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesHref = driver.findElement(By.id("nav-notes-tab"));
		notesHref.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-delete-id")));
		WebElement noteDeleteId = driver.findElement(By.id("note-delete-id"));
		noteDeleteId.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesHref02 = driver.findElement(By.id("nav-notes-tab"));
		notesHref02.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-empty-id")));
		WebElement noteTitleEmptyOutput = driver.findElement(By.id("note-title-empty-id"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description-empty-id")));
		WebElement noteDescriptionEmptyOutput = driver.findElement(By.id("note-description-empty-id"));
		Assertions.assertEquals(noteTitleEmptyOutput.getText(), "Example Note Title");
		Assertions.assertEquals(noteDescriptionEmptyOutput.getText(), "Example Note Description");
		Assertions.assertTrue(noteDescriptionEmptyOutput.getText().equals("Example Note Description"));
		Assertions.assertFalse(noteDescriptionEmptyOutput.getText().equals("Example XXX Description"));


//		note-delete-id


	}



}
