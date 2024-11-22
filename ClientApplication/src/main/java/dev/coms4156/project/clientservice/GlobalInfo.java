package dev.coms4156.project.clientservice;

/**
 * GlobalInfo holds constants and shared configuration for the client application.
 */
public class GlobalInfo {

  // Base URL of the backend service
  public static final String BASE_URL = "https://ase-team.ue.r.appspot.com";

  // API Endpoints
  public static final String CREATE_DONATION = "/createDonation";
  public static final String RETRIEVE_AVAILABLE_ITEMS = "/retrieveAvailableItems";
  public static final String CREATE_REQUEST= "/createRequest";
  public static final String RETRIEVE_DISPATCHED_ITEMS = "/retrieveDispatchedItems";

  private GlobalInfo() {
    // Prevent instantiation
  }
}
