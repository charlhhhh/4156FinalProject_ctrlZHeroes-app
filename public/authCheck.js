import { auth } from "./firebase.js";

// Check if the user is authenticated
auth.onAuthStateChanged((user) => {
    if (!user) {
        // Redirect to login page if user is not authenticated
        window.location.href = "/login.html";
    }
});
auth.onAuthStateChanged((user) => {
    if (user) {
        document.getElementById("welcomeMessage").innerText = `Welcome, ${user.email}`;
    }
});

