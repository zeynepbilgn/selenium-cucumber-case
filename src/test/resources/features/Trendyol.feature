Feature: Trendyol Automation

  @trendyol
  Scenario: End-to-end automation testing for basic functions on Trendyol e-commerce site

    Given User go to the trendyol website
    Then Verify that the main page is opened.
    When User attempts to log in with incorrect information
    And User searches for "laptop"
    Then User selects a random product
    And User adds the product to the cart
    And Go to Cart
    Then Verify the product price before and after adding to the cart
    And User increases the quantity in the basket to two
    Then Verify the accuracy of the quantity
    And User deletes all products from the basket
    Then Verify the basket is empty