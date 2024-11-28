# 4156FinalProject_ctrlZHeroes-app

# Client Application for Donation Management Service

This is the GitHub repository for the service portion of the Team Project associated with COMS 4156 Advanced Software Engineering. Our group, ctrlZHeroes, comprises the following members: Yanxi Chen, Qirui Ruan, Xinchen Zhang, Songwen Zhao, and Charlie Shen.

---

## **Overview**

The **Donation Management Client Application** simplifies the process of managing donations and resources. It is designed to interact seamlessly with the **Donation Management Service**, which is deployed at `https://ase-team.ue.r.appspot.com`.

This application provides:
- A web-based interface for donors and shelter managers to create, retrieve, and manage donations.
- Backend services that connect to the service API for efficient handling of resources.
- A structured and easy-to-use platform for tracking items and distributing them where needed.

---

## **Target Audience**

This application is specifically designed to assist:
1. **Homeless**:
  - To streamline the management of incoming donations and resource distribution.
  - To improve transparency and efficiency in fulfilling requests for essential supplies.

2. **Community Organizations**:
  - To enable better tracking of available and dispatched resources.
  - To provide a user-friendly interface for managing donations and inventory.

3. **Donors**:
  - To simplify the donation process with forms and easy tracking of their contributions.

---

## **Features**

1. **Create Donations**:
  - Donors can quickly log their contributions (e.g., food, medical supplies, clothing) into the system via a user-friendly form.
  - Each item is categorized with metadata like expiration dates, donor information, and type.

2. **Retrieve Items**:
  - Shelter staff and managers can search for specific items using their IDs or view a list of available resources.
  - A clean, card-like layout displays item details clearly.

3. **View Available Items**:
  - Check a list of all available items by resource ID.

4. **View Dispatched Items**:
  - Track dispatched resources to ensure accountability and transparency.

5. **Authentication**:
- Secure login and registration powered by Firebase.

---

## How the App and Service Work Together

The web application interacts with the service as follows:

  - The frontend handles user input, displaying data and collecting information such as resource requests.
  - The backend service processes the requests, updates the database, and provides responses to the frontend.
  - Requests such as retrieving resources, submitting donations, or checking dispatch status are processed via RESTful APIs provided by the service.



---

## **Prerequisites**
Before setting up the project, ensure you have the following installed:
1. **Java 17** or higher.
2. **Apache Maven 3.6** or higher.
3. **Git** for cloning the repository.
4. A browser (e.g., Chrome, Firefox) for accessing the web interface.

---

## **Setup Instructions**

### **1. Clone the Repository**
Clone the project to your local machine:
```bash
git clone https://github.com/your-repo/ClientApplication.git
cd ClientApplication
```

### **2. Build the Project**
Clone the project to your local machine:
```bash
mvn clean install
```
### **3. Run the Application**
Start the Spring Boot application:
```bash
mvn spring-boot:run
```
Once the application is running, access the client at:
```bash
http://localhost:8080/
```

### **4. Test the Application**
#### API Testing
You can test the APIs directly using tools like **Postman** or **curl**.

- **Example API for retrieving an item**:
  ```bash
  curl -X GET "http://localhost:8080/client/retrieveItem?resourceId=123&itemId=456"
  ```
- **Example API for creating a donation:**:
  ```bash
  curl -X POST "http://localhost:8080/client/donation" \
  -d "resourceId=123" \
  -d "itemType=Food" \
  -d "quantity=5" \
  -d "expirationDate=2024-12-31" \
  -d "donorId=JohnDoe"
  ```
#### End-to-end test
In order to properly perform end-to-end tests, please follow the list and compare the results of the actions performed to the expected results provided.

The following consists of a series of tests you should perform to ensure everything is working properly. Please complete the following checklist **IN ORDER** to ensure the results are as they are meant to be.

**Please ensure no other applications are running that use up ports on your system.**

---

1. **Run the Application Server**

    - Start the application server, ensuring it's accessible at `http://localhost:8000` or your deployed URL.
    - **Expected Result:** The server runs without errors and is ready to accept connections.

2. **Navigate to the Registration Page**

    - Open your web browser and go to `http://localhost:8000/client/registerPage`.
    - **Expected Result:** The registration page displays, titled "Register", with fields for Email and Password.

3. **Register a New User**

    - Fill out the registration form with the following information:
        - **Email:** `testuser@example.com`
        - **Password:** `TestPassword123`
    - Click the **Register** button.
    - **Expected Result:** A success message appears, and you are redirected to the login page titled "Login".

4. **Attempt to Register with Invalid Data**

    - Try registering with an invalid email format or leave the password field empty.
    - **Expected Result:** An error message indicates that registration failed due to invalid input.

5. **Navigate to the Login Page**

    - Go to `http://localhost:8000/client/loginPage` if not already there.
    - **Expected Result:** The login page displays with fields for Email and Password.

6. **Login with Correct Credentials**

    - Fill out the login form using:
        - **Email:** `testuser@example.com`
        - **Password:** `TestPassword123`
    - Click the **Login** button.
    - **Expected Result:** A success message appears, and you are redirected to the main dashboard displaying "Welcome, testuser@example.com!".

7. **Login with Incorrect Credentials**

    - Attempt to log in with an incorrect password.
    - **Expected Result:** An error message indicates invalid credentials.

8. **Verify Main Dashboard Access**

    - Ensure you are on the main dashboard at `http://localhost:8000/client/`.
    - **Expected Result:** The dashboard displays navigation options like "Create a Request" and "Create a Donation".

9. **Create a Donation**

    - Click **Go to Create Donation** to navigate to `http://localhost:8000/client/createDonationPage`.
    - Fill out the form:
        - **Resource ID:** `resource001`
        - **Item Type:** `Medical Supplies`
        - **Quantity:** `50`
        - **Expiration Date:** Select a future date.
        - **Donor ID:** `donor001`
    - Click the **Submit** button.
    - **Expected Result:** A confirmation message indicates the donation was successfully created.

10. **Create a Request**

    - Click **Go to Create Request** to navigate to `http://localhost:8000/client/createRequestPage`.
    - Fill out the form:
        - **Item IDs:** `item001,item002`
        - **Priority Level:** `High`
        - **Requester Info:** `Hospital A`
    - Click the **Submit** button.
    - **Expected Result:** A confirmation message indicates the request was successfully created.

11. **Retrieve an Item**

    - Click **Go to Retrieve Item** to navigate to `http://localhost:8000/client/retrieveItemPage`.
    - Fill out the form:
        - **Resource ID:** `exampleID`
        - **Item ID:** `exampleID`
    - Click the **Retrieve Item** button.
    - **Expected Result:** The item's details are displayed on the page.

12. **Check Available Items**

    - Click **Check Items** under **Check Available Items** to navigate to `http://localhost:8000/client/retrieveAvailableItemsPage`.
    - Enter **Resource ID:** `exampleID`.
    - Click the **Retrieve** button.
    - **Expected Result:** A list of available items related to `exampleID` is displayed.

13. **Check Dispatched Items**

    - Click **View Dispatched Items** under **Check Dispatched Items** to navigate to `http://localhost:8000/client/retrieveDispatchedItemsPage`.
    - Enter **Resource ID:** `exampleID`.
    - Click the **Retrieve** button.
    - **Expected Result:** A list of dispatched items related to `exampleID` is displayed.

14. **Test Logout Functionality**

    - Click the **Logout** link/button in the header.
    - **Expected Result:** You are logged out and redirected to the login page. The user email is no longer displayed.

15. **Verify Access Control**

    - Without logging in, try to access `http://localhost:8000/client/`.
    - **Expected Result:** You are redirected to the login page, indicating that authentication is required.

16. **Test Form Validation**

    - Attempt to submit any form with empty fields or invalid data.
    - **Expected Result:** Error messages prompt you to fill in required fields or correct invalid input.

End of Testing

---



## **Endpoints and Features**

### **Client Features**
- **Main Dashboard**: Provides navigation to all major functionalities.
- **Create Donation Page** : Allows users to create a new donation by filling out a form.
- **Retrieve Item Page** : Provides a UI to fetch details of an item by its ID and resource ID.
- **Available Items Page**: Displays all available items based on a given resource ID.
- **Dispatched Items Page**: Tracks dispatched resources for transparency.


### **Service API Endpoints**
The client communicates with the **Donation Management Service** at `https://ase-team.ue.r.appspot.com`. Below are the service endpoints used:

| Endpoint                 | Method | Description                         |
|--------------------------|--------|-------------------------------------|
| `/createDonation`        | POST   | Creates a new donation.             |
| `/retrieveAvailableItems` | GET    | Retrieves all available items.      |
| `/retrieveItem`          | GET    | Retrieves details of a specific item.|
| `/createRequest`         | POST | 	Creates a new request specifying item IDs, priority level, and requester information.             |
| `/retrieveDispatchedItems`                         |    GET  |        Fetches all dispatched items related to a given resource ID.                                                                                            |


### **Client-Side API Interaction**
The client application uses the following API endpoints via the `ClientService` class:

1. **Create Donation**:
  - **Purpose**: Creates a new donation entry.
  - **Parameters**:
    - `resourceId`
    - `itemType`
    - `quantity`
    - `expirationDate`
    - `donorId`

2. **Retrieve Item**:
  - **Path**: `/client/retrieveItem`
  - **Parameters**:
    - `resourceId`
    - `itemId`

3. **createRequest**:
  - **Purpose**: Creates a new request for specific items.
  - **Parameters**:
    - `resourceId`

4. **Retrieve Available Items**:
  - **Purpose**: Fetches a list of all available items for a given resource ID.
  - **Parameters**:
    - `resourceId`

5. **RetrieveDispatchedItems**:
- **Purpose**: Retrieves a list of dispatched items based on the resource ID.
- **Parameters**:
    - `resourceId`
---
## Deployment
you can find the deployment of our app: https://ase-app-443104.ue.r.appspot.com/

---
## **Tools Used 🛠️**

- **Maven**: Build management for the service.
- **Spring Boot**: Backend API framework.
- **Google Cloud Platform (GCP)**: Cloud hosting for service and app.
- **JUnit**: Unit testing for the service.
- **Checkstyle**: Ensures adherence to Java coding standards.  




