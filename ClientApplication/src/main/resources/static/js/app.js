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

async function retrieveItems() {
  const resourceId = document.getElementById("resourceId").value;
  const response = await fetch(`/client/availableItems?resourceId=${resourceId}`);
  const result = await response.text();
  document.getElementById("result").innerText = result;
}

async function dispatchItems() {
  const resourceId = document.getElementById("resourceId").value;
  const response = await fetch(`/client/dispatch?resourceId=${resourceId}`, { method: 'PATCH' });
  const result = await response.text();
  document.getElementById("result").innerText = result;
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


