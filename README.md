# 4156FinalProject_ctrlZHeroes-app

# Client Application for Donation Management Service

This project is a sample **Client Application** that interacts with the **Donation Management Service**, deployed at `https://ase-team.ue.r.appspot.com`. The client allows users to create donations, retrieve items, and dispatch resources using a clean web interface and REST APIs.

---

## **Table of Contents**
- [Overview](#overview)
- [Target Audience](#target-audience)
- [Benefits](#benefits)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
    - [Clone the Repository](#clone-the-repository)
    - [Build the Project](#build-the-project)
    - [Run the Application](#run-the-application)
    - [Test the Application](#test-the-application)
- [Endpoints and Features](#endpoints-and-features)
- [Developing Your Own Client](#developing-your-own-client)

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

## **Features and Benefits**

### **Features**
1. **Create Donations**:
  - Donors can quickly log their contributions (e.g., food, medical supplies, clothing) into the system via a user-friendly form.
  - Each item is categorized with metadata like expiration dates, donor information, and type.

2. **Retrieve Items**:
  - Shelter staff and managers can search for specific items using their IDs or view a list of available resources.
  - A clean, card-like layout displays item details clearly.

3. **Dispatch Resources**:
  - Allows managers to allocate resources to fulfill shelter needs.
  - Status updates ensure transparency in resource tracking.

4. **Web-Based Access**:
  - The app is accessible from any browser, making it convenient for donors and shelter managers to interact with the system.

---

### **Benefits**
1. **Efficiency**:
  - Automates resource tracking and reduces manual errors.
  - Accelerates the donation, retrieval, and dispatch processes.

2. **Transparency**:
  - Clear visibility into the inventory and resource allocation.
  - Accurate reporting of item statuses and donor contributions.

3. **Accessibility**:
  - Enables seamless communication between donors and shelters through an intuitive web interface.
  - Can be accessed from any device with an internet connection.

4. **Cost-Effectiveness**:
  - Reduces administrative costs by providing a centralized, automated system for managing resources.

5. **Enhanced Impact**:
  - Ensures resources are effectively distributed to those who need them most, helping shelters focus on their core mission of assisting the homeless.

By addressing the unique needs of donors, shelters, and community organizations, the Donation Management Client Application brings structure and simplicity to the donation and resource management process.

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

#### Manual Testing
- Navigate to the following pages in your browser:
  - **Home Page**: [http://localhost:8080/](http://localhost:8080/)
  - **Create Donation Page**: [http://localhost:8080/client/create_donation](http://localhost:8080/client/create_donation)
  - **Retrieve Item Page**: [http://localhost:8080/client/retrieveItemPage](http://localhost:8080/client/retrieveItemPage)
- Use the forms provided on these pages to interact with the service.

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
## **Endpoints and Features**

### **Client Features**
- **Home Page** (`/`): Provides a starting point for navigating to other pages.
- **Create Donation Page** (`/client/create_donation`): Allows users to create a new donation by filling out a form.
- **Retrieve Item Page** (`/client/retrieveItemPage`): Provides a UI to fetch details of an item by its ID and resource ID.

### **Service API Endpoints**
The client communicates with the **Donation Management Service** at `https://ase-team.ue.r.appspot.com`. Below are the service endpoints used:

| Endpoint                  | Method | Description                         |
|---------------------------|--------|-------------------------------------|
| `/createDonation`         | POST   | Creates a new donation.             |
| `/retrieveAvailableItems` | GET    | Retrieves all available items.      |
| `/retrieveItem`           | GET    | Retrieves details of a specific item.|
| `/processRequests`        | PATCH  | Dispatches resources.               |

### **Client-Side API Interaction**
The client application uses the following API endpoints via the `ClientService` class:

1. **Create Donation**:
  - **Path**: `/client/donation`
  - **Method**: `POST`
  - **Parameters**:
    - `resourceId`
    - `itemType`
    - `quantity`
    - `expirationDate`
    - `donorId`
  - **Description**: Allows donors to create a new donation entry.

2. **Retrieve Item**:
  - **Path**: `/client/retrieveItem`
  - **Method**: `GET`
  - **Parameters**:
    - `resourceId`
    - `itemId`
  - **Description**: Fetches details about a specific item.

3. **Dispatch Items**:
  - **Path**: `/client/dispatch`
  - **Method**: `PATCH`
  - **Parameters**:
    - `resourceId`
  - **Description**: Marks available items as dispatched for a specific resource.

4. **Retrieve Available Items**:
  - **Path**: `/client/availableItems`
  - **Method**: `GET`
  - **Parameters**:
    - `resourceId`
  - **Description**: Fetches all items marked as available for a specific resource.

## **Developing Your Own Client**

If you'd like to create your own client for the **Donation Management Service**, follow the steps below:

### **1. Understand the Service API**
- The APIs are RESTful and use JSON for responses.
- Refer to the following endpoints for supported operations:
  | Endpoint                  | Method | Description                         |
  |---------------------------|--------|-------------------------------------|
  | `/createDonation`         | POST   | Creates a new donation.             |
  | `/retrieveAvailableItems` | GET    | Retrieves all available items.      |
  | `/retrieveItem`           | GET    | Retrieves details of a specific item.|
  | `/processRequests`        | PATCH  | Dispatches resources.               |

### **2. Include a REST Client Library**
- For Java: Use `RestTemplate` or `WebClient`.
- For JavaScript: Use `fetch` or libraries like `axios`.

### **3. Set the Service Base URL**
The service is deployed at: https://ase-team.ue.r.appspot.com.  
Set this as the base URL for all your API requests.

### **4. Example Java Code for API Calls**
Here is a Java example using `RestTemplate` to interact with the service:

```java
import org.springframework.web.client.RestTemplate;

public class DonationClient {
    private static final String BASE_URL = "https://ase-team.ue.r.appspot.com";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // Example: Retrieve Item
        String resourceId = "123";
        String itemId = "456";
        String url = BASE_URL + "/retrieveItem?resourceId=" + resourceId + "&itemId=" + itemId;

        String response = restTemplate.getForObject(url, String.class);
        System.out.println("Response: " + response);
    }
}
```

### **5. Example JavaScript Code for API Calls**
Here is a JavaScript example using `fetch`:
```javascript
const BASE_URL = "https://ase-team.ue.r.appspot.com";

async function retrieveItem(resourceId, itemId) {
  const url = `${BASE_URL}/retrieveItem?resourceId=${resourceId}&itemId=${itemId}`;

  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    console.log(data);
  } catch (error) {
    console.error("Error retrieving item:", error);
  }
}
```

### **6. Configuration**
Include the necessary dependencies in your project configuration file:  
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



