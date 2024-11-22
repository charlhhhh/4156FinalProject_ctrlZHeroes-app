import app from "./firebase-config.js"; // Firebase configuration
import { getAuth, signInWithEmailAndPassword, onAuthStateChanged } from "https://www.gstatic.com/firebasejs/9.20.0/firebase-auth.js";

// Initialize Firebase Auth
const auth = getAuth(app);

// Function to check login status
export function checkAuth() {
  return new Promise((resolve, reject) => {
    onAuthStateChanged(auth, (user) => {
      if (user) {
        resolve(user);
      } else {
        reject("User is not logged in.");
      }
    });
  });
}

// Function to prompt login if user is not authenticated
export async function requireLogin(action) {
  try {
    const user = await checkAuth();
    console.log("User is logged in:", user.email);
    action(); // Proceed with the action
  } catch (error) {
    alert("You must be logged in to perform this action.");
    window.location.href = "/client/loginPage"; // Redirect to login page
  }
}

// Function to get the Firebase ID Token of the currently logged-in user
async function getToken() {
  const currentUser = auth.currentUser;
  if (!currentUser) {
    throw new Error("User is not logged in.");
  }
  return await currentUser.getIdToken();
}

// Function to handle user login
async function loginUser(email, password) {
  try {
    // Sign in the user
    const userCredential = await auth.signInWithEmailAndPassword(email, password);
    console.log("User logged in:", userCredential.user);

    // Optionally fetch the Firebase ID Token
    const idToken = await userCredential.user.getIdToken();
    console.log("Firebase ID Token:", idToken);

    // Display feedback
    alert("Login successful!");

    // Redirect to the homepage
    window.location.href = "/client/";
  } catch (error) {
    console.error("Error logging in:", error.message);
    alert("Login failed: " + error.message);
  }
}


// Function to create a donation
async function createDonation() {
  const resourceId = document.getElementById("resourceId").value;
  const itemType = document.getElementById("itemType").value;
  const quantity = document.getElementById("quantity").value;
  const expirationDate = document.getElementById("expirationDate").value;
  const donorId = document.getElementById("donorId").value;

  try {
    const token = await getAuth().currentUser.getIdToken();

    const response = await fetch(`/client/donation?resourceId=${resourceId}&itemType=${itemType}&quantity=${quantity}&expirationDate=${expirationDate}&donorId=${donorId}`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    const result = await response.text();
    document.getElementById("result").innerText = result;
  } catch (error) {
    console.error("Error creating donation:", error);
    alert("Failed to create donation.");
  }
}

// Function to retrieve available items
async function retrieveAvailableItems() {
  const resourceId = document.getElementById("resourceId").value;

  try {
    const token = await getToken();

    const response = await fetch(`/client/retrieveAvailableItems?resourceId=${resourceId}`, {
      headers: {
        Authorization: token, // Add token to header
      },
    });

    if (!response.ok) throw new Error("Failed to retrieve available items.");

    const data = await response.text();
    const resultDiv = document.getElementById("availableItemsResult");
    resultDiv.innerHTML = `<pre>${data}</pre>`;
  } catch (error) {
    console.error("Error retrieving available items:", error);
    alert("Error retrieving available items.");
  }
}

// Function to retrieve a specific item
async function retrieveItem() {
  const resourceId = document.getElementById("resourceId").value;
  const itemId = document.getElementById("itemId").value;

  if (!resourceId || !itemId) {
    document.getElementById("result").innerText = "Please provide both Resource ID and Item ID.";
    return;
  }

  try {
    const token = await getToken();

    const response = await fetch(`/client/retrieveItem?resourceId=${resourceId}&itemId=${itemId}`, {
      headers: {
        Authorization: token, // Add token to header
      },
    });

    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

    const data = await response.text();
    document.getElementById("result").innerText = data;
  } catch (error) {
    console.error("Error fetching item:", error);
    document.getElementById("result").innerText = "Failed to retrieve the item. Please try again.";
  }
}

// Function to create a request
async function createRequest() {
  const requestId = document.getElementById("requestId").value;
  const itemIds = document.getElementById("itemIds").value.split(",");
  const status = document.getElementById("status").value;
  const priorityLevel = document.getElementById("priorityLevel").value;
  const requesterInfo = document.getElementById("requesterInfo").value;

  try {
    const token = await getToken();

    const response = await fetch(`/client/createRequest`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: token, // Add token to header
      },
      body: JSON.stringify({ requestId, itemIds, status, priorityLevel, requesterInfo }),
    });

    if (!response.ok) throw new Error("Failed to create request.");

    const data = await response.text();
    const resultDiv = document.getElementById("createRequestResult");
    resultDiv.innerHTML = `<pre>${data}</pre>`;
  } catch (error) {
    console.error("Error creating request:", error);
    alert("Error creating request.");
  }
}

// Function to retrieve dispatched items
async function retrieveDispatchedItems() {
  const resourceId = document.getElementById("resourceId").value;

  try {
    const token = await getToken();

    const response = await fetch(`/client/dispatchedItems?resourceId=${resourceId}`, {
      headers: {
        Authorization: token, // Add token to header
      },
    });

    if (!response.ok) {
      const errorMessage = await response.text();
      throw new Error(errorMessage || "Error retrieving dispatched items.");
    }

    const data = await response.text();
    const resultDiv = document.getElementById("dispatchedItemsResult");
    resultDiv.innerHTML = `<pre>${data}</pre>`;
  } catch (error) {
    console.error("Error retrieving dispatched items:", error);
    alert("Error retrieving dispatched items.");
  }
}

// Export login function for use in login HTML
export { loginUser };
