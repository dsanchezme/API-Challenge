Feature: Authentication

  Scenario: Create Request Token
    Given I want a request token
    And I have an API key
    When I create the request token
    Then I get successfully the response
    And I get the "request_token" value in the response
    And I get status code <200>

  Scenario: Create Session with Login
    Given I want to login to the application
    And I have an API key
    And I have a request token
    When I create a session with Login
    Then I get successfully the response
    And I get status code <200>

  Scenario: Create New session
    Given I want to create a new session
    And I have an API key
    And I have a request token
    When I create a new session
    Then I get successfully the response
    And I get the "session_id" value in the response
    And I get status code <200>

  Scenario: Delete session
    Given I am logged into the application
    When I logout from the application
    Then I get successfully the response
    And I get status code <200>
