Feature: API

Scenario: Register using valid data
    When Send request to register with body:
	"""
		{
			"email": "testqa.wrc96@gmail.com",
			"full_name": "Test QA",
			"password": "@dmin123",
			"department": "Technology",
			"phone_number": "085611789000"
		}
	"""
	Then The response code must be 200
	And The response schema should be match with schema "register-schema.json"
	And The Id is not null
	And The email should equal "testqa.wrc96@gmail.com"
	And The full_name should equal "Test QA"
	And The department should equal "Technology"
	And The phone_number should equal "085611789000"

Scenario: Login using valid credentials		
	When Send request to login with body:
    """
        {
            "email": "trisniy097@gmail.com",
            "password": "@dmin123"
        }
    """
    Then The response code must be 200
    And Save the token from the response to the local storage

Scenario: Create Object
    Given Have token on the local storage
    When Send request to create object with body:
    """
    {
        "name": "Lenovo K14",
        "data": {
            "year": 2024,
            "price": 1849.99,
            "cpu_model": "Intel Core i9",
            "hard_disk_size": "1 TB",
            "capacity": "2 cpu",
            "screen_size": "14 inch",
            "color": "black"
        }
    }
    """
    Then The response code must be 200
    And The idObject is not null
    And Save the idObject from the response to the local storage
    And The name from the create response should be "Lenovo K14"
    And The year from the create response should be 2024
    And The price from the create response should be 1849.99
    And The cpu_model from the create response should be "Intel Core i9"
    And The hard_disk_size from the create response should be "1 TB"
    And The capacity from the create response should be "2 cpu"
    And The screen_size from the create response should be "14 inch"
    And The color from the create response should be "black"

Scenario: Get All Object
    Given Have token on the local storage
    When Send request to get all object
    Then The response code must be 200

Scenario: Get Single Object
    Given Have token on the local storage
    When Send request to get single object
    Then The response code must be 200
    And The idObject is not null
    And The name from the single-object response should be "Lenovo K14"
    And The year from the single-object response should be "2024"
    And The price from the single-object response should be "1849.99"
    And The cpu_model from the single-object response should be "Intel Core i9"
    And The hard_disk_size from the single-object response should be "1 TB"
    And The capacity from the single-object response should be 2
    And The screen_size from the single-object response should be 14
    And The color from the single-object response should be "black"

Scenario: List Object By Id
    Given Have token on the local storage
    When Send request to get list object by id
    Then The response code must be 200

Scenario: Update Object
    Given Have token on the local storage
    When Send request to update object with body:
    """
    {
        "name": "Lenovo Yoga",
        "data": {
                "year": "2025",
                "price": "1999.99",
                "cpu_model": "Intel Core i8",
                "hard_disk_size": "2 TB",
                "color": "gray",
                "capacity": "4",
                "screen_size": "12"
            }
    }

    """
    Then The response code must be 200
    And The idObject is not null
    And The name from the update response should be "Lenovo Yoga"
    And The year from the update response should be 2025
    And The price from the update response should be 1999.99
    And The cpu_model from the update response should be "Intel Core i8"
    And The hard_disk_size from the update response should be "2 TB"
    And The capacity from the update response should be "4"
    And The screen_size from the update response should be "12"
    And The color from the update response should be "gray"

Scenario: Partially Update Object
    Given Have token on the local storage
    When Send request to partially update object with body:
    """
    {
        "name": "Lenovo Ideapad",
        "year": "2027",
        "color": "white"
    }

    """
    Then The response code must be 200
    And The idObject is not null
    And The name from the partially update response should be "Lenovo Ideapad"
    And The year from the partially update response should be "2027"
    And The color from the partially update response should be "white"

Scenario: Delete Object
    Given Have token on the local storage
    When Send request to delete object
    Then The response code must be 200

Scenario: Department Object
    Given Have token on the local storage
    When Send request to get all department
    Then The response code must be 200