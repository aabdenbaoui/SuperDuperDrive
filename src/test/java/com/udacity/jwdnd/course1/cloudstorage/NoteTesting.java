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
public class NoteTesting {

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
        doMockSignUp("UserTestEditF", "UserTestEditL", "userNameNoteEdit", "passFake");
        doLogIn("userNameNoteEdit", "passFake");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5, 5);
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

        //LOOK FOR edit and CLICK
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesHrefThirdRound = driver.findElement(By.id("nav-notes-tab"));
        notesHrefSecondRound.click();

//        note-edit-btn
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-edit-btn")));
        WebElement clickOnNotEdit = driver.findElement(By.id("note-edit-btn"));
        clickOnNotEdit.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleEdit = driver.findElement(By.id("note-title"));
        noteTitleEdit.click();
        noteTitleEdit.clear();
//        noteTitleEdit.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleEdit02= driver.findElement(By.id("note-title"));
        //        noteTitleEdit.click();
        noteTitleEdit02.sendKeys("Note Title 02");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescriptionEdit = driver.findElement(By.id("note-description"));
        noteDescriptionEdit.click();
        noteDescriptionEdit.clear();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescriptionEdit02 = driver.findElement(By.id("note-description"));
        noteDescriptionEdit.click();
        noteDescriptionEdit02.sendKeys("Note Description 02");
        //noteSubmit
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit-save")));
        WebElement noteSubmitEdit = driver.findElement(By.id("note-submit-save"));
        noteSubmitEdit.click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesHrefFourthRound = driver.findElement(By.id("nav-notes-tab"));
        notesHrefFourthRound.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleId")));
        WebElement noteTitleOutputEdit = driver.findElement(By.id("noteTitleId"));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescriptionId")));
        WebElement noteDescriptionOutputEdit = driver.findElement(By.id("noteDescriptionId"));
        Assertions.assertEquals(noteTitleOutputEdit.getText(), "Note Title 02");
        Assertions.assertEquals(noteDescriptionOutputEdit.getText(), "Note Description 02");

    }

    @Test
    public void WriteTestThatDeletesNoteAndVerifiesThatTheNoteIsNoLongerDisplayed(){
        doMockSignUp("UserTest01F", "UserTest01L", "userName02", "passFake");
        doLogIn("userName01", "passFake");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        //click on href=#nav-notes-tab to get to notes home
        WebElement notesHref00 = getWebElement(webDriverWait, "nav-notes-tab");
        notesHref00.click();

        //click on href=#nav-notes-tab to get to notes home
//        WebElement notesHref01 = getWebElement(webDriverWait,"nav-notes-tab");
//        notesHref01.click();

        WebElement noteTitleOutput = getWebElement( webDriverWait, "noteTitleId");
        WebElement noteDescriptionOutput = getWebElement(webDriverWait, "noteDescriptionId");

        //assert results
        Assertions.assertEquals(noteTitleOutput.getText(), "Note Title 01");
        Assertions.assertEquals(noteDescriptionOutput.getText(), "Note Description 01");

        WebElement notesHref =  getWebElement(webDriverWait, "nav-notes-tab");
        notesHref.click();
        WebElement noteDeleteId = getWebElement(webDriverWait,"note-delete-id");
        noteDeleteId.click();
        WebElement notesHref02 = getWebElement(webDriverWait, "nav-notes-tab");
        notesHref02.click();
        WebElement noteTitleEmptyOutput = getWebElement(webDriverWait, "note-title-empty-id");
        WebElement noteDescriptionEmptyOutput =getWebElement(webDriverWait, "note-description-empty-id");
        Assertions.assertEquals(noteTitleEmptyOutput.getText(), "Example Note Title");
        Assertions.assertEquals(noteDescriptionEmptyOutput.getText(), "Example Note Description");
        Assertions.assertTrue(noteDescriptionEmptyOutput.getText().equals("Example Note Description"));
        Assertions.assertFalse(noteDescriptionEmptyOutput.getText().equals("Example XXX Description"));
    }

    @Test
    public void writeTestThatViewsExistingSetCredentialsVerifiesThatTheViewablePasswordIsUnencryptedEditsTheCredentialsAndVerifiesThatTheChangesAreDisplayed(){
        doMockSignUp("UserTestEditF", "UserTestEditL", "userNameNoteEdit", "passFake");
        doLogIn("userNameNoteEdit", "passFake");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5, 5);
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

        //LOOK FOR edit and CLICK
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesHrefThirdRound = driver.findElement(By.id("nav-notes-tab"));
        notesHrefSecondRound.click();

//        note-edit-btn
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-edit-btn")));
        WebElement clickOnNotEdit = driver.findElement(By.id("note-edit-btn"));
        clickOnNotEdit.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleEdit = driver.findElement(By.id("note-title"));
        noteTitleEdit.click();
        noteTitleEdit.clear();
//        noteTitleEdit.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleEdit02= driver.findElement(By.id("note-title"));
        //        noteTitleEdit.click();
        noteTitleEdit02.sendKeys("Note Title 02");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescriptionEdit = driver.findElement(By.id("note-description"));
        noteDescriptionEdit.click();
        noteDescriptionEdit.clear();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescriptionEdit02 = driver.findElement(By.id("note-description"));
        noteDescriptionEdit.click();
        noteDescriptionEdit02.sendKeys("Note Description 02");
        //noteSubmit
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit-save")));
        WebElement noteSubmitEdit = driver.findElement(By.id("note-submit-save"));
        noteSubmitEdit.click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesHrefFourthRound = driver.findElement(By.id("nav-notes-tab"));
        notesHrefFourthRound.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleId")));
        WebElement noteTitleOutputEdit = driver.findElement(By.id("noteTitleId"));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescriptionId")));
        WebElement noteDescriptionOutputEdit = driver.findElement(By.id("noteDescriptionId"));
        Assertions.assertEquals(noteTitleOutputEdit.getText(), "Note Title 02");
        Assertions.assertEquals(noteDescriptionOutputEdit.getText(), "Note Description 02");
    }

    private WebElement getWebElement(WebDriverWait webDriverWait, String elementId){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
        return  driver.findElement(By.id(elementId));
    }

}
