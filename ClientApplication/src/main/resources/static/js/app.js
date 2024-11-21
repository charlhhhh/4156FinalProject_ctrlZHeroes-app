async function createDonation() {
  const resourceId = document.getElementById("resourceId").value;
  const itemType = document.getElementById("itemType").value;
  const quantity = document.getElementById("quantity").value;
  const expirationDate = document.getElementById("expirationDate").value;
  const donorId = document.getElementById("donorId").value;

  const response = await fetch(`/client/donation?resourceId=${resourceId}&itemType=${itemType}&quantity=${quantity}&expirationDate=${expirationDate}&donorId=${donorId}`, {
    method: 'POST',
  });
  const result = await response.text();
  document.getElementById("result").innerText = result;
}

async function retrieveAvailableItems() {
  const resourceId = document.getElementById("resourceId").value;

  fetch(`/client/retrieveAvailableItems?resourceId=${resourceId}`)
  .then((response) => {
    if (!response.ok) throw new Error("Failed to retrieve available items.");
    return response.text();
  })
  .then((data) => {
    const resultDiv = document.getElementById("availableItemsResult");
    resultDiv.innerHTML = `<pre>${data}</pre>`;
  })
  .catch((error) => {
    console.error(error);
    alert("Error retrieving available items.");
  });
}


function retrieveItem() {
  const resourceId = document.getElementById("resourceId").value;
  const itemId = document.getElementById("itemId").value;

  if (!resourceId || !itemId) {
    document.getElementById("result").innerText = "Please provide both Resource ID and Item ID.";
    return;
  }

  const url = `/client/retrieveItem?resourceId=${resourceId}&itemId=${itemId}`;

  fetch(url)
  .then(response => {
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.text();
  })
  .then(data => {
    document.getElementById("result").innerText = data;
  })
  .catch(error => {
    console.error("Error fetching item:", error);
    document.getElementById("result").innerText = "Failed to retrieve the item. Please try again.";
  });
}

function createRequest() {
  const requestId = document.getElementById("requestId").value;
  const itemIds = document.getElementById("itemIds").value.split(",");
  const status = document.getElementById("status").value;
  const priorityLevel = document.getElementById("priorityLevel").value;
  const requesterInfo = document.getElementById("requesterInfo").value;

  fetch(`/client/createRequest`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ requestId, itemIds, status, priorityLevel, requesterInfo }),
  })
  .then((response) => {
    if (!response.ok) throw new Error("Failed to create request.");
    return response.text();
  })
  .then((data) => {
    const resultDiv = document.getElementById("createRequestResult");
    resultDiv.innerHTML = `<pre>${data}</pre>`;
  })
  .catch((error) => {
    console.error(error);
    alert("Error creating request.");
  });
}

function retrieveDispatchedItems() {
  const resourceId = document.getElementById("resourceId").value;

  fetch(`/client/dispatchedItems?resourceId=${resourceId}`)
  .then((response) => {
    if (!response.ok) {
      // Handle HTTP errors (e.g., 404, 500)
      return response.text().then((errorMessage) => {
        throw new Error(errorMessage || "Error retrieving dispatched items.");
      });
    }
    return response.text();
  })
  .then((data) => {
    const resultDiv = document.getElementById("dispatchedItemsResult");
    resultDiv.innerHTML = `<pre>${data}</pre>`;
  })
  .catch((error) => {
    // Display error message to the user
    const resultDiv = document.getElementById("dispatchedItemsResult");
    resultDiv.innerHTML = `<div class="error-message">Error: ${error.message}</div>`;
  });
}

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


