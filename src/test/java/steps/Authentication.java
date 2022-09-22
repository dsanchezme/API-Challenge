package steps;

import controllers.AuthenticationController;
import helpers.SystemVariables;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class Authentication{

    private final SystemVariables systemVariables = new SystemVariables();
    private final Logger logger = LogManager.getLogger("authentication");
    private final AuthenticationController authenticationController = new AuthenticationController();

    private Response response;
    private String requestToken;
    private String sessionID;

    @Given("I want a request token")
    public void iWantARequestToken(){
        logger.info("Starting a request token process...");
    }

    @And("I have an API key")
    public void iHaveAnAPIKey() {
        Assert.assertNotNull(systemVariables.getApiKey());
    }

    @When("I create the request token")
    public void iCreateTheRequestToken() {
        logger.debug("Getting response...");
        this.response = authenticationController.createRequestToken();
    }

    @Then("I get successfully the response")
    public void iGetSuccessfullyTheResponse() {
        Assert.assertEquals(this.response.jsonPath().get("success"), true);
        logger.debug("Success!");
    }

    @And("I get the {string} value in the response")
    public void iGetTheRequestedValueInTheResponse(String requestedValue) {
        Assert.assertTrue(this.response.body().asString().contains(requestedValue));
    }

    @And("I get status code <{int}>")
    public void iGetStatusCode(int statusCode) {
        Assert.assertEquals(this.response.statusCode(), statusCode);
    }

    @Given("I want to login to the application")
    public void iWantToLoginToTheApplication() {
        logger.info("Login to The Movie DB...");
    }

    @And("I have a request token")
    public void iHaveARequestToken() {
        this.requestToken = authenticationController.getRequestToken();
        Assert.assertNotNull(this.requestToken);
    }

    @When("I create a session with Login")
    public void iCreateASessionWithLogin() {
        logger.debug("Getting response...");
        this.response = authenticationController.createSessionWithLogin(this.requestToken);
    }

    @Given("I want to create a new session")
    public void iWantToCreateANewSession() {
        logger.info("Creating a new session...");
    }

    @When("I create a new session")
    public void iCreateANewSession() {
        logger.debug("Getting response...");
        this.response = authenticationController.createNewSession(this.requestToken);
        logger.debug("Response: " + this.response.asString());
    }

    @Given("I am logged into the application")
    public void iAmLoggedIntoTheApplication() {
        this.sessionID = authenticationController.getSessionID();
    }

    @When("I logout from the application")
    public void iLogoutFromTheApplication() {
        this.response = authenticationController.deleteSession(this.sessionID);
    }
}
