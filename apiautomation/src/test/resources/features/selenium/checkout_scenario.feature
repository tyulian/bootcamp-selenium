Feature: Purchase the order from ecommerce

Background: User landed to login page
    Given User landing to logged ecommerce
    When User input username "standard_user" and password "secret_sauce"
    Then User redirect to homepage

Scenario: Buyer create new order
    When Buyer add product "Sauce Labs Bike Light" to cart
    And Buyer redirect to cart page
    Then Verify product "Sauce Labs Bike Light" is on cart
    When Buyer redirect to checkout page "Checkout: Your Information"
    And Buyer input first name "Trisni", last name "Yuliana", and postal code "40111"
    Then Verify on overview page "Checkout: Overview" the product "Sauce Labs Bike Light" is available
    And Buyer click button finish
    And On complete page "Checkout: Complete!" Buyer will see the message "Thank you for your order!" is displayed