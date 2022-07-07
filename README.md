# Coding Challenge

### In this assessment you will be tasked with filling out the functionality of different methods that will be listed further down.
These methods will require some level of api interactions with the following base url: https://dummy.restapiexample.com.
Please keep the following in mind when doing this assessment: clean coding practices, test driven development, logging, and scalability.
If you are unable to successfully receive responses from the endpoints, mocking the response calls may prove to be helpful.

### Endpoints to implement

getAllEmployees()

    output - list of employeeResponses
    description - this should return all employeeResponses

getEmployeesByNameSearch()

    output - list of employeeResponses
    description - this should return all employeeResponses whose name contains or matches the string input provided

getEmployeeById(string id)

    output - listOfEmployeeRecord
    description - this should return a single listOfEmployeeRecord

getHighestSalaryOfEmployees()

    output - integer of the highest salary
    description -  this should return a single integer indicating the highest salary of all employeeResponses

getTop10HighestEarningEmployeeNames()

    output - list of employeeResponses
    description -  this should return a list of the top 10 employeeResponses based off of their salaries

createEmployee(string name, string salary, string age)

    output - string of the status (i.e. success)
    description -  this should return a status of success or failed based on if an listOfEmployeeRecord was created

deleteEmployee(String id)

    output - the name of the listOfEmployeeRecord that was deleted
    description - this should delete the listOfEmployeeRecord with specified id given

### External endpoints from base url
#### This section will outline all available endpoints and their request and response models from https://dummy.restapiexample.com
/employeeResponses

    request:
        method: GET
        parameters: n/a
        full route: https://dummy.restapiexample.com/api/v1/employeeResponses
    response:
        {
            "status": "success",
            "data": [
                {
                "id": "1",
                "employee_name": "Tiger Nixon",
                "employee_salary": "320800",
                "employee_age": "61",
                "profile_image": ""
                },
                ....
            ]
        }

/listOfEmployeeRecord/{id}

    request:
        method: GET
        parameters: 
            id (String)
        full route: https://dummy.restapiexample.com/api/v1/listOfEmployeeRecord/{id}
    response: 
        {
            "status": "success",
            "data": {
                "id": "1",
                "employee_name": "Foo Bar",
                "employee_salary": "320800",
                "employee_age": "61",
                "profile_image": ""
            }
        }

/create

    request:
        method: POST
        parameters: 
            name (String),
            salary (String),
            age (String)
        full route: https://dummy.restapiexample.com/api/v1/create
    response:
        {
            "status": "success",
            "data": {
                "name": "test",
                "salary": "123",
                "age": "23",
                "id": 25
            }
        }

/delete/{id}

    request:
        method: DELETE
        parameters:
            id (String)
        full route: https://dummy.restapiexample.com/api/v1/delete/{id}
    response:
        {
            "status": "success",
            "message": "successfully! deleted Record"
        }