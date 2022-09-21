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

public class Authentication {

    private final SystemVariables systemVariables = new SystemVariables();
    private final Logger logger = LogManager.getLogger("authentication");
    private final AuthenticationController authenticationController = new AuthenticationController();
    private Response response;

    @Given("I want a request token")
    public void iWantARequestToken(){
        logger.info("Starting a request token...");
    }

    @When("I create the request token")
    public void iCreateTheRequestToken() {
        logger.debug("Getting response...");
        this.response = authenticationController.createRequestToken();
        logger.debug("Response: "+this.response.asString());
    }

    @Then("I get successfully the response")
    public void iGetSuccessfullyTheResponse() {
        Assert.assertEquals(this.response.jsonPath().get("success"), true);
    }

    @And("I get the request token in the response")
    public void iGetTheRequestTokenInTheResponse() {
        Assert.assertTrue(this.response.body().asString().contains("request_token"));
    }

    @And("I get status code <{int}>")
    public void iGetStatusCode(int statusCode) {
        Assert.assertEquals(this.response.statusCode(), statusCode);
    }

    @And("I have an API key")
    public void iHaveAnAPIKey() {
        Assert.assertNotNull(systemVariables.getApiKey());
    }
}
