package steps;

import controllers.AuthenticationController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import utils.ScenarioContext;

public class Authentication{
    private final Logger logger = LogManager.getLogger("authentication");
    private final AuthenticationController authenticationController = new AuthenticationController();

    private String requestToken;

    private final ScenarioContext context;

    public Authentication(ScenarioContext context){
        this.context = context;
    }

    @Given("I want a request token")
    public void iWantARequestToken(){
        logger.info("Starting a request token process...");
    }

    @When("I create the request token")
    public void iCreateTheRequestToken() {
        logger.debug("Getting request token response...");
        context.setResponse(authenticationController.createRequestToken());
    }

    @And("I get the {string} value in the response")
    public void iGetTheRequestedValueInTheResponse(String requestedValue) {
        Assert.assertTrue(context.getResponse().body().asString().contains(requestedValue));
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
        context.setResponse(authenticationController.createSessionWithLogin(this.requestToken));
    }

    @Given("I want to create a new session")
    public void iWantToCreateANewSession() {
        logger.info("Creating a new session...");
    }

    @When("I create a new session")
    public void iCreateANewSession() {
        context.setResponse(authenticationController.createNewSession(this.requestToken));
    }

    @When("I logout from the application")
    public void iLogoutFromTheApplication() {
        context.setResponse(authenticationController.deleteSession(context.getSessionID()));
    }
}
