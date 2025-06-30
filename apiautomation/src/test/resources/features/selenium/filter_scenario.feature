Feature: Filter product

Background: User landed to login page
    Given User landing to logged ecommerce
    When User input username "standard_user" and password "secret_sauce"
    Then User redirect to homepage

Scenario: Filter product by Name (A to Z)
    When User select filter by "Name (A to Z)"
    Then Verify the product filtered by "Name (A to Z)"

Scenario: Filter product by Name (Z to A)
    When User select filter by "Name (Z to A)"
    Then Verify the product filtered by "Name (Z to A)"

Scenario: Filter product by Price (low to high)
    When User select filter by "Price (low to high)"
    Then Verify the product filtered by "Price (low to high)"

Scenario: Filter product by Price (high to low)
    When User select filter by "Price (high to low)"
    Then Verify the product filtered by "Price (high to low)"
