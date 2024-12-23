@Regression
Feature: Login with an user

  @severity=blocker
  Scenario Outline: Verify user able to login
    Given I log in with <username> username
    Then  I check login is correct

    Examples:
      | username    |
      | example.com |