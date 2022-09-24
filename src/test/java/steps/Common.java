package steps;

import controllers.AuthenticationController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import utils.ScenarioContext;

public class Common {
    AuthenticationController authenticationController = new AuthenticationController();
    private final ScenarioContext context;

    public Common(ScenarioContext context){
        this.context = context;
    }

    @Given("I am logged into the application")
    public void iAmLoggedIntoTheApplication() {
        context.setSessionID(authenticationController.getSessionID());
    }

    @Then("I get status code <{int}>")
    public void iGetStatusCode(int statusCode) {
        Assert.assertEquals(context.getResponse().statusCode(), statusCode);
    }

    @Then("I get successfully the response")
    public void iGetSuccessfullyTheResponse() {
        Assert.assertEquals(context.getResponse().jsonPath().get("success"), true);
    }
}
