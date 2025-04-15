package com.example.opencvandfirebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class ResidentialChargesHelper {

    public interface OnChargesCalculatedListener {
        void onChargesCalculated(double total);
    }

    // Method to fetch the latest charges and calculate the residential bill
    public static void fetchLatestResidentialCharges(String recordText, String lastMonth, String connectionId, OnChargesCalculatedListener listener) {
        new Thread(() -> {
            try {
                // Step 1: Fetch the latest charges from the `residential_charges` table
                String fetchChargesEndpoint = "/rest/v1/residential_charges?select=monthly_basic,additional_per_m3,initial_m3&order=created_at.desc&limit=1";
                Response fetchChargesResponse = DatabaseConnector.performGetRequest(fetchChargesEndpoint);

                if (!fetchChargesResponse.isSuccessful()) {
                    System.out.println("Error fetching latest charges.");
                    return;
                }

                String chargesResponseData = fetchChargesResponse.body().string();
                JSONArray chargesArray = new JSONArray(chargesResponseData);

                if (chargesArray.length() == 0) {
                    System.out.println("No charges data found.");
                    return;
                }

                // Parse the latest residential charges
                JSONObject chargesObject = chargesArray.getJSONObject(0);
                double monthlyBasic = chargesObject.getDouble("monthly_basic");
                double additionalPerM3 = chargesObject.getDouble("additional_per_m3");
                int initialM3 = chargesObject.getInt("initial_m3");

                // Step 2: Calculate the usage difference
                int currentMonthUsage = calculateUsage(recordText);
                int lastMonthUsage = calculateUsage(lastMonth);
                int usageDifference = currentMonthUsage - lastMonthUsage;

                // Step 3: Calculate total charges
                double totalCharges = calculateTotalCharges(usageDifference, monthlyBasic, additionalPerM3, initialM3);

                // Step 4: Insert the total charges into the database
                updateTotalCharges(totalCharges, connectionId);

                // Step 5: Notify the listener (e.g., the Reading class) of the calculated total
                listener.onChargesCalculated(totalCharges);

            } catch (IOException | JSONException e) {
                System.out.println("Error calculating charges: " + e.getMessage());
            }
        }).start();
    }

    // Helper method to calculate the usage from the concatenated reading string
    private static int calculateUsage(String reading) {
        try {
            // Convert the reading string (e.g., "12345678") to an integer
            return Integer.parseInt(reading);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing reading: " + e.getMessage());
            return 0;  // Return 0 in case of error
        }
    }

    // Method to calculate the total charges based on the usage and rates
    private static double calculateTotalCharges(int usageDifference, double monthlyBasic, double additionalPerM3, int initialM3) {
        double totalCharges = monthlyBasic;

        // Check if usage exceeds the initial cubic meters (m3) and add additional charges if necessary
        if (usageDifference > initialM3) {
            int extraUsage = usageDifference - initialM3;
            totalCharges += extraUsage * additionalPerM3;
        }

        return totalCharges;
    }

    // Method to insert the total charges into the monthly_bill table
    private static void updateTotalCharges(double totalCharges, String connectionId) {
        new Thread(() -> {
            try {
                String updateJson = "{\"payable\": " + (int) totalCharges + "}"; // Cast to int for int8
                String updateEndpoint = "/rest/v1/monthly_bill?connection_id=eq." + connectionId;

                System.out.println("Updating JSON: " + updateJson); // Debug log

                Response updateResponse = DatabaseConnector.performPatchRequest(updateEndpoint, updateJson);

                if (updateResponse.isSuccessful()) {
                    System.out.println("Total charges updated successfully.");
                } else {
                    // Log the error response for better debugging
                    System.out.println("Error updating total charges: " + updateResponse.message());
                    String errorResponse = updateResponse.body().string(); // Get the error body
                    System.out.println("Error response body: " + errorResponse); // Log the error body
                }
            } catch (IOException e) {
                System.out.println("Failed to update total charges. Error: " + e.getMessage());
            }
        }).start();
    }

}
