Feature: Login Scenario

Background: User landed to login page
    Given User landing to logged ecommerce

Scenario: Login using valid credentials
    When User input username "standard_user" and password "secret_sauce"
    Then User redirect to homepage

Scenario Outline: Login using invalid credentials
    When User input username "<username>" and password "<password>"
    Then Verify error message "<errorMsg>"
    
    Examples:
    |username       |password    |errorMsg                                                    |
    |standard_user  |            |*Password is required                                       |
    |               |secret_sauce|*Username is required                                       |
    |standard       |admin123    |*Username and password do not match any user in this service|
    |locked_out_user|secret_sauce|*Sorry, this user has been locked out.                      |
