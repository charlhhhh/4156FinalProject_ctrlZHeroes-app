// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
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
const auth = getAuth(app);

export { auth, signInWithEmailAndPassword, createUserWithEmailAndPassword };