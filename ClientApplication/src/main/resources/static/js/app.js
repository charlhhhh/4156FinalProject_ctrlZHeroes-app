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
  const token = await ensureLoggedIn(false); // Silent check

  const resourceId = document.getElementById("resourceId").value;
  const itemId = document.getElementById("itemId").value;

  if (!resourceId || !itemId) {
    document.getElementById("result").innerText = "Please provide both Resource ID and Item ID.";
    return;
  }

  try {
    const response = await fetch(`/client/retrieveItem?resourceId=${resourceId}&itemId=${itemId}`, {
      headers: token ? { "Authorization": token } : {},
    });

    if (!response.ok) throw new Error("Failed to retrieve the item.");

    const data = await response.text();
    document.getElementById("result").innerText = data;
  } catch (error) {
    console.error("Error fetching item:", error);
    alert("Failed to retrieve the item. Please try again.");
  }
};

window.createRequest = async function () {
  const token = await ensureLoggedIn();
  if (!token) return;

  const requestId = document.getElementById("requestId").value;
  const itemIds = document.getElementById("itemIds").value.split(",");
  const status = document.getElementById("status").value;
  const priorityLevel = document.getElementById("priorityLevel").value;
  const requesterInfo = document.getElementById("requesterInfo").value;

  try {
    const response = await fetch("/client/createRequest", {
      method: "POST",
      headers: {
        "Authorization": token,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ requestId, itemIds, status, priorityLevel, requesterInfo }),
    });

    if (response.ok) {
      const result = await response.text();
      document.getElementById("createRequestResult").innerText = result;
    } else {
      throw new Error("Unauthorized or failed to create request.");
    }
  } catch (error) {
    console.error(error.message);
    alert("Error: " + error.message);
  }
};


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
        <p><span>ID:</span> ${item.id}</p>
        <p><span>Type:</span> ${item.type}</p>
        <p><span>Quantity:</span> ${item.quantity}</p>
        <p><span>Expiration:</span> ${item.expiration}</p>
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



