Feature: Lists

  Background:
    Given I am logged into the application

  Scenario: Create list
    Given I want to create a movie list
    When I create a movie list with the following data
    | name        | My list |
    | description | This is the description for my list |
    | language    | en            |
    Then I get successfully the response
    And I get the "list_id" value in the response
    And I get status code <201>

  Scenario: Get list details
    Given I have a list ID
    When I get the details of the list
    Then the list ID in the response is the same as the one requested
    And I get status code <200>

  Scenario: Add item to list
    Given I have a list ID
    And I have a media ID
    When I add that item to the list
    Then I get successfully the response
    And I get status code <201>

  Scenario: Clear list
    Given I have the ID of a list with at least one item
    When I clear the list
    Then I get successfully the response
    And the list has <0> items
    And I get status code <201>

  Scenario: Delete list
    Given I have a list ID
    When I delete the list
    Then I get successfully the response
    And I get status code <201>