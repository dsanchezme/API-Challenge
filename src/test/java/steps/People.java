package steps;

import controllers.BaseController;
import controllers.PeopleController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.ScenarioContext;

public class People {

    private final ScenarioContext context;

    public People(ScenarioContext context){
        this.context = context;
    }

    private final Logger logger = LogManager.getLogger("people");
    private final PeopleController peopleController = new PeopleController();

    private String personID;

    @Given("I want to get the list of popular people on TMDB")
    public void iWantToGetTheListOfPopularPeople(){
        logger.info("Getting list of popular people");
    }


    @When("I get the list of popular people")
    public void iGetTheListOfPopularPeople() {
        context.setResponse(peopleController.getPopularPeopleResponse());
    }

    @And("the list has at least {int} item")
    public void theListHasAtLeastItem(int minSize) {
        Assert.assertTrue(peopleController.getPopularPeopleList().size() >= minSize);
    }

    @Given("I have a person ID")
    public void iHaveAPersonID() {
        this.personID = peopleController.getPopularPerson().get("id").getAsString();
    }

    @When("I get the details of that person")
    public void iGetTheDetailsOfThatPerson() {
        context.setResponse(peopleController.getPersonDetails(this.personID));
        logger.debug(context.getResponse().asString());
    }

    @And("the person has {string}")
    public void thePersonHas(String key) {
        Assert.assertNotNull(BaseController.getValueFromResponse(context.getResponse(), key));
    }
}

