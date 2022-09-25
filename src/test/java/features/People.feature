Feature: People

  Scenario: Get popular
    Given I want to get the list of popular people on TMDB
    When I get the list of popular people
    Then I get status code <200>
    And the list has at least 1 item

  Scenario: Get person details
    Given I have a person ID
    When I get the details of that person
    Then I get status code <200>
    And the person has "id"
    And the person has "name"
    And the person has "birthday"

  Scenario: Get person images
    Given I have a person ID
    When I get the images of that person
    Then I get status code <200>
    And the person ID in the response is the same as the one requested