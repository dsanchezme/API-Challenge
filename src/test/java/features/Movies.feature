Feature: Movies

  Scenario: Get latest movie
    Given I want to get the latest movie
    When I get the most newly created movie
    Then I get status code <200>
    And the movie has "id"
    And the movie has "title"
    And the movie has "overview"

  Scenario: Get movie details
    Given I have a movie ID
    When I get the details of the movie
    Then the movie ID in the response is the same as the one requested
    And I get status code <200>

  Scenario: Rate movie
    Given I am logged into the application
    And I have a movie ID
    When I rate a movie with a value of 8.5
    Then I get successfully the response
    And I get status code <201>