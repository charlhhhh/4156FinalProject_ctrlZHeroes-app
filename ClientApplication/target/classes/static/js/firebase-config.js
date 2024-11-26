// Import the functions you need from the SDKs
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyBu2UdrASHov-J2seUynL7HqynLs4PNPBc",
  authDomain: "ctrlzheroes-app.firebaseapp.com",
  projectId: "ctrlzheroes-app",
  storageBucket: "ctrlzheroes-app.firebasestorage.app",
  messagingSenderId: "533252141666",
  appId: "1:533252141666:web:65c5426bf78d560131d73b",
  measurementId: "G-SEK9ZE20W1"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

export default app; // Export the Firebase app instance for use in other scripts
