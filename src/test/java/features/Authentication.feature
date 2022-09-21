Feature: Authentication

  Scenario: Create Request Token
    Given I want a request token
    And I have an API key
    When I create the request token
    Then I get successfully the response
    And I get the request token in the response
    And I get status code <200>