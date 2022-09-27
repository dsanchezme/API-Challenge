package steps;

import controllers.BaseController;
import controllers.MoviesController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Assert;
import utils.ScenarioContext;

public class Movies{

    private final ScenarioContext context;
    public Movies(ScenarioContext context){
        this.context = context;
    }

    private final Logger logger = LogManager.getLogger("movies");

    private final MoviesController moviesController = new MoviesController();
    private String mediaID;

    @Given("I have a movie ID")
    public void iHaveAMovieID(){
        this.mediaID = moviesController.getMovieID();
    }


    @When("I get the details of the movie")
    public void iGetTheDetailsOfTheMovie() {
        context.setResponse(moviesController.getMovieDetails(this.mediaID));
    }

    @Then("the movie ID in the response is the same as the one requested")
    public void theMovieIDInTheResponseIsTheSameAsTheOneRequested() {
        String responseMovieID = BaseController.getValueFromResponse(context.getResponse(), "id");
        Assert.assertNotNull(responseMovieID);
        Assert.assertEquals(responseMovieID, this.mediaID);
    }

    @When("I get the most newly created movie")
    public void iGetTheMostNewlyCreatedMovie() {
        context.setResponse(moviesController.getLatestMovie());
    }

    @And("the movie has {string}")
    public void theMovieHas(String key) {
        String value = BaseController.getValueFromResponse(context.getResponse(), key);
        Assert.assertNotNull(value);
    }

    @When("I rate a movie with a value of {float}")
    public void iRateAMovie(float rateValue) {
        context.setResponse(moviesController.rateMovie(context.getSessionID(), this.mediaID, rateValue));
    }

    @Given("I want to get the latest movie")
    public void iWantToGetTheLatestMovie() {
        logger.info("Getting latest movie...");
    }
}
