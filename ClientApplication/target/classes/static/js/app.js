import { initializeApp } from "https://www.gstatic.com/firebasejs/9.20.0/firebase-app.js";
import { getAuth } from "https://www.gstatic.com/firebasejs/9.20.0/firebase-auth.js";

const firebaseConfig = {
  apiKey: "AIzaSyBu2UdrASHov-J2seUynL7HqynLs4PNPBc",
  authDomain: "ctrlzheroes-app.firebaseapp.com",
  projectId: "ctrlzheroes-app",
  storageBucket: "ctrlzheroes-app.firebasestorage.app",
  messagingSenderId: "533252141666",
  appId: "1:533252141666:web:65c5426bf78d560131d73b",
  measurementId: "G-SEK9ZE20W1"
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

window.createDonation = async function () {
  const token = await ensureLoggedIn();
  if (!token) return;

  const resourceId = document.getElementById("resourceId").value;
  const itemType = document.getElementById("itemType").value;
  const quantity = document.getElementById("quantity").value;
  const expirationDate = document.getElementById("expirationDate").value;
  const donorId = document.getElementById("donorId").value;

  try {
    const response = await fetch("/client/donation", {
      method: "POST",
      headers: {
        "Authorization": token,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ resourceId, itemType, quantity, expirationDate, donorId }),
    });

    if (response.ok) {
      const result = await response.text();
      document.getElementById("result").innerText = result;
    } else {
      throw new Error("Unauthorized or failed to create donation.");
    }
  } catch (error) {
    console.error(error.message);
    alert("Error: " + error.message);
  }
};



window.retrieveAvailableItems = async function () {
  const token = await ensureLoggedIn(false); // Silent check

  const resourceId = document.getElementById("resourceId").value;

  try {
    const response = await fetch(`/client/retrieveAvailableItems?resourceId=${resourceId}`, {
      headers: token ? { "Authorization": token } : {},
    });

    if (!response.ok) throw new Error("Failed to retrieve available items.");

    const data = await response.text();
    document.getElementById("availableItemsResult").innerHTML = `<pre>${data}</pre>`;
  } catch (error) {
    console.error(error);
    alert("Error retrieving available items.");
  }
};


window.retrieveItem = async function () {
  // Attempt to retrieve token if logged in
  const token = await ensureLoggedIn(false); // Silent check

  // Get values from input fields
  const resourceId = document.getElementById("resourceId").value;
  const itemId = document.getElementById("itemId").value;

  // Validate inputs
  if (!resourceId || !itemId) {
    document.getElementById("result").innerHTML =
        '<div class="error-message">Please provide both Resource ID and Item ID.</div>';
    return;
  }

  try {
    // Fetch data from the backend
    const response = await fetch(`/client/retrieveItem?resourceId=${resourceId}&itemId=${itemId}`, {
      headers: token ? { "Authorization": token } : {},
    });

    // Handle HTTP errors
    if (!response.ok) {
      if (response.status === 404) {
        document.getElementById("result").innerHTML =
            '<div class="error-message">Item not found. Please check the ID and try again.</div>';
      } else if (response.status === 401) {
        document.getElementById("result").innerHTML =
            '<div class="error-message">Unauthorized access. Please log in.</div>';
      } else {
        document.getElementById("result").innerHTML =
            `<div class="error-message">Item not found. Please check the ID and try again.</div>`;
      }
      return;
    }

    // Parse JSON data
    const data = await response.json();

    if(data.message){
        document.getElementById("result").innerHTML = `
        <div class="error-message">
          <strong>Error:</strong> ${data.message || "An error occurred while retrieving the item."}
          <br>
          <strong>Item ID:</strong> ${itemId || "Unknown"}
        </div>`;
    }
    else{
      // Display item details
      renderItemCard(data);
    }
  } catch (error) {
    // Handle unexpected errors
    console.error("Error fetching item:", error);
    document.getElementById("result").innerHTML =
        '<div class="error-message">An error occurred while retrieving the item. Please try again later.</div>';
  }
};


window.createRequest = async function () {
  try {
    // Ensure user is logged in and retrieve token
    const token = await ensureLoggedIn();
    if (!token) {
      alert("Please log in to create a request.");
      return;
    }

    // Retrieve input fields for user-provided data
    const itemIds = document.getElementById("itemIds").value.split(",").map(id => id.trim());
    const itemQuantities = document.getElementById("itemQuantities").value.split(",").map(qty => parseInt(qty.trim(), 10));
    const requesterInfo = document.getElementById("requesterInfo").value;
    const resourceId = document.getElementById("resourceId").value;

    // Validate user inputs
    if (!itemIds.length || !itemQuantities.length || itemIds.length !== itemQuantities.length || !requesterInfo || !resourceId) {
      alert("Please provide valid Item IDs, quantities, Requester Info, and Resource ID. Ensure IDs and quantities match.");
      return;
    }

    // Check if all quantities are valid numbers
    if (itemQuantities.some(qty => isNaN(qty) || qty <= 0)) {
      alert("Please provide valid quantities (positive integers) for all items.");
      return;
    }

    // Auto-generate request details
    const requestId = `REQ-${Date.now()}-${Math.random().toString(36).substr(2, 5).toUpperCase()}`;
    const status = "Pending"; // Default status
    const priorityLevel = determinePriorityLevel(itemIds); // Determine priority based on item types

    // Prepare API call
    const response = await fetch("/client/createRequest", {
      method: "POST",
      headers: {
        "Authorization": token,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        requestId,
        itemIds,
        itemQuantities,
        status,
        priorityLevel,
        requesterInfo,
        resourceId,
      }),
    });

    // Handle API response
    if (response.ok) {
      const result = await response.json(); // Assuming the server responds with JSON
      document.getElementById("createRequestResult").innerText = `Request created successfully:\n${JSON.stringify(result, null, 2)}`;
    } else {
      // Handle potential error responses
      const error = await response.json();
      throw new Error(error.message || "Failed to create request.");
    }
  } catch (error) {
    // General error handling
    console.error("Error creating request:", error.message);
    document.getElementById("createRequestResult").innerText = `Error: ${error.message}`;
    alert("Error: " + error.message);
  }
};

// Helper function to determine priority level based on item types
function determinePriorityLevel(itemIds) {
  // Example logic for priority determination based on item types
  if (itemIds.some(id => id.toLowerCase().includes("medicine"))) return "High";
  if (itemIds.some(id => id.toLowerCase().includes("food"))) return "Medium";
  return "Low"; // Default priority
}



window.retrieveDispatchedItems = async function () {
  const token = await ensureLoggedIn(false); // Silent check

  const resourceId = document.getElementById("resourceId").value;

  try {
    const response = await fetch(`/client/retrieveDispatchedItems?resourceId=${resourceId}`, {
      headers: token ? { "Authorization": token } : {},
    });

    if (!response.ok) throw new Error("Failed to retrieve dispatched items.");

    const data = await response.text();
    document.getElementById("dispatchedItemsResult").innerHTML = `<pre>${data}</pre>`;
  } catch (error) {
    console.error(error);
    alert("Error retrieving dispatched items.");
  }
};

function renderItemCard(item) {
  const resultDiv = document.getElementById("result");
  resultDiv.innerHTML = `
        <h3>Item Details</h3>
        <p><span>ID:</span> ${item.itemId}</p>
        <p><span>Type:</span> ${item.itemType}</p>
        <p><span>Quantity:</span> ${item.quantity}</p>
        <p><span>Expiration:</span> ${item.expirationDate}</p>
        <p><span>Status:</span> ${item.status}</p>
        <p><span>Donor ID:</span> ${item.donorId}</p>
    `;
}

async function ensureLoggedIn(showAlert = true) {
  const user = auth.currentUser;
  if (!user) {
    if (showAlert) {
      alert("Please log in to perform this action.");
    }
    return null;
  }
  return user.getIdToken();
}

window.addEventListener("DOMContentLoaded", async () => {
  const user = auth.currentUser;

  if (user) {
    const userInfoDiv = document.createElement("div");
    userInfoDiv.id = "userInfo";
    userInfoDiv.innerHTML = `<p>Welcome, ${user.email}! <a href="#" id="logoutLink">Logout</a></p>`;
    document.body.insertBefore(userInfoDiv, document.querySelector("nav"));

    document.getElementById("logoutLink").addEventListener("click", async () => {
      await auth.signOut();
      alert("Logged out successfully!");
      window.location.reload();
    });
  } else {
    console.log("User is not logged in.");
  }
});



