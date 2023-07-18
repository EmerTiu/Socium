Feature: Feature for fetching information from grid filter row table
 Scenario: Fetch grid filter row values given a valid ID
  #Valid ID are IDs that are present in the fetch grid filter row values
  #If the input is an invalid id the test will return an "ID not found" error
  Given a valid ID
  And a broswer is open
  And I am on the ASP demo page
  When I sort the ID in ascending order 
  And the user searches for the ID
  Then the row for the corresponding ID should be visible
  And the entry details is printed in the console
