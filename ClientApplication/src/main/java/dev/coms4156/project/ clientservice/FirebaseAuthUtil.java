package dev.coms4156.project.clientservice;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseAuthUtil {

    /**
     * Verifies the Firebase ID token and retrieves the user ID.
     *
     * @param idToken The Firebase ID token received from the client.
     * @return The UID (user ID) of the authenticated user.
     * @throws Exception If the token is invalid or verification fails.
     */
    public static String verifyToken(String idToken) throws Exception {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid(); // Extract the UID of the authenticated user.
    }
}
