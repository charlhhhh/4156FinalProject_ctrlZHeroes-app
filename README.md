# 4156FinalProject_ctrlZHeroes-app

## Login and Registration Feature
The application now includes a login and registration system implemented using Firebase Authentication. This feature allows users to:

-Register with their email and password.
-Login with registered credentials.
-Securely communicate with the backend using Firebase ID tokens.

## Firebase Project Information
The login and registration functionality is powered by Firebase. Below are the details for accessing and managing the Firebase project:

Firebase Project Name:
ctrlzheroes-app

Firebase Console URL:
[Firebase Console - ctrlzheroes-app](https://console.firebase.google.com/project/ctrlzheroes-app/overview)

Firebase Configuration:
The Firebase configuration details used in the frontend (firebase.js) are:

```
const firebaseConfig = {
    apiKey: "AIzaSyBu2UdrASHov-J2seUynL7HqynLs4PNPBc",
    authDomain: "ctrlzheroes-app.firebaseapp.com",
    projectId: "ctrlzheroes-app",
    storageBucket: "ctrlzheroes-app.appspot.com",
    messagingSenderId: "533252141666",
    appId: "1:533252141666:web:65c5426bf78d560131d73b",
    measurementId: "G-SEK9ZE20W1"
};
```

## Frontend Setup
### Files Added/Updated:
-login.html: The main page for user login and registration.
-login.js: Contains logic for handling login and registration processes.
-firebase.js: Firebase configuration and initialization file.
-authCheck.js: Ensures only authenticated users can access protected pages.
-dashboard.html: Example of a protected page for logged-in users.

### Features:
Login:
Users can log in using their email and password.
On successful login, they are redirected to a protected dashboard (dashboard.html).

Registration:
Users can register with a valid email and password.
Password validation ensures security (minimum 8 characters, one uppercase letter, one number).
On successful registration, users are notified.

Authentication Check:
Protected pages (e.g., dashboard.html) use authCheck.js to ensure only authenticated users can access them.
Unauthenticated users are redirected to the login page (login.html).

## Backend Integration
### Updated Files:
ClientController.java: Contains endpoints for user-related actions.
Application.java
add all four files from front/end branch
FirebaseAuthUtil.java added 

## Firebase Setup
To develop or test this feature, you need access to the Firebase project:

1. Firebase Authentication:

Email/Password authentication must be enabled in the Firebase Console under Authentication > Sign-in Methods.

2. Firebase Admin SDK:

The backend requires the serviceAccountKey.json file to initialize Firebase Admin SDK.
Download the file from Firebase Console > Project Settings > Service Accounts > Generate New Private Key.
Place the file in the backend under src/main/resources/serviceAccountKey.json.

3. Firebase Realtime Database or Firestore (Optional):

If additional user data needs to be stored (e.g., roles, profiles), enable Firestore or Realtime Database in the Firebase Console.

## Development and Testing Instructions (Failed)
### Frontend Testing:
Open login.html in a browser or deploy the public/ folder using Firebase Hosting:
```
firebase deploy
```
Test login and registration flows.
Verify successful navigation to dashboard.html after login.
### Backend Testing:

Start the Spring Boot backend:
```
mvn spring-boot:run
```
Use curl or Postman to test secured endpoints (e.g., /client/donation).
### Expected Behavior:

Registered users should be visible in the Firebase Console under Authentication > Users.
Login should succeed for valid credentials and fail for invalid ones.
Protected pages should redirect unauthenticated users to login.html.
