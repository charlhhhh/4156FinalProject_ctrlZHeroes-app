import { auth, signInWithEmailAndPassword, createUserWithEmailAndPassword } from "./firebase.js";

// Handle login
document.getElementById("loginButton").addEventListener("click", () => {
    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;

    signInWithEmailAndPassword(auth, email, password)
        .then((userCredential) => {
            const user = userCredential.user;
            document.getElementById("status").innerText = "Login successful!";
            console.log("User logged in:", user);

            // Optionally redirect to a protected page
            window.location.href = "/dashboard.html";
        })
        .catch((error) => {
            document.getElementById("status").innerText = "Login failed: " + error.message;
            console.error("Login error:", error);
        });
});

// Handle registration
document.getElementById("registerButton").addEventListener("click", () => {
    const email = document.getElementById("registerEmail").value;
    const password = document.getElementById("registerPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        document.getElementById("status").innerText = "Passwords do not match!";
        return;
    }

    createUserWithEmailAndPassword(auth, email, password)
        .then((userCredential) => {
            const user = userCredential.user;
            document.getElementById("status").innerText = "Registration successful!";
            console.log("User registered:", user);

            // Optionally redirect to the login page or auto-login
        })
        .catch((error) => {
            document.getElementById("status").innerText = "Registration failed: " + error.message;
            console.error("Registration error:", error);
        });
});
