package steps;

import controllers.BaseController;
import controllers.ListsController;
import controllers.MoviesController;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import utils.BodyFactory;
import utils.ScenarioContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Lists {

    private final ScenarioContext context;

    public Lists(ScenarioContext context){
        this.context = context;
    }

    private final Logger logger = LogManager.getLogger("lists");

    private final ListsController listsController = new ListsController();
    private final MoviesController moviesController = new MoviesController();

    private String listID;
    private String mediaID;

    @Given("I want to create a movie list")
    public void iWantToCreateAMovieList(){
        logger.info("Creating a movie list...");
    }

    @When("I create a movie list with the following data")
    public void iCreateAMovieListWithTheFollowingData(DataTable table) {
        Map<String, String> bodyAsMap = new HashMap<>(table.asMap(String.class, String.class));
        float randomNumber = new Random().nextFloat();
        bodyAsMap.put("name", bodyAsMap.get("name") + " " + randomNumber);
        bodyAsMap.put("description", bodyAsMap.get("description") + " " + randomNumber);
        String body = BodyFactory.mapToJson(bodyAsMap);
        context.setResponse(listsController.createMovieList(context.getSessionID(), body));
    }

    @And("I have a list ID")
    public void iHaveAListID() {
        this.listID = listsController.getListID(context.getSessionID());
    }


    @When("I get the details of the list")
    public void iGetTheDetailsOfTheList() {
        context.setResponse(listsController.getListDetails(this.listID));
    }

    @Then("the list ID in the response is the same as the one requested")
    public void theListIDInTheResponseIsTheSameAsTheOneRequested() {
        String responseListID = BaseController.getValueFromResponse(context.getResponse(), "id");
        Assert.assertNotNull(responseListID);
        Assert.assertEquals(responseListID, this.listID);
    }

    @And("I have a media ID")
    public void iHaveAMediaID() {
        this.mediaID = moviesController.getMovieID();
    }

    @When("I add that item to the list")
    public void iAddThatItemToTheList() {
        this.listID = listsController.getListID(context.getSessionID());
        context.setResponse(listsController.addMediaToList(context.getSessionID(), this.mediaID, this.listID));
    }

    @When("I clear the list")
    public void iClearTheList() {
        context.setResponse(listsController.clearList(context.getSessionID(), this.listID));
    }

    @And("I have the ID of a list with at least one item")
    public void iHaveTheIDOfAListWithAtLeastOneItem() {
        this.listID = listsController.getListID(context.getSessionID());
        this.mediaID = moviesController.getMovieID();
        listsController.addMediaToList(context.getSessionID(), this.mediaID, this.listID);
    }

    @And("the list has <{int}> items")
    public void theListHasItems(int nItems) {
        String actualNItems = BaseController.getValueFromResponse(listsController.getListDetails(this.listID), "item_count");
        Assert.assertEquals(Integer.parseInt(actualNItems), nItems);
    }

    @When("I delete the list")
    public void iDeleteTheList() {
        context.setResponse(listsController.deleteList(context.getSessionID(), this.listID));
    }
}
