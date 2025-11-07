package com.tpopdsunomas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Utility class for geocoding and reverse geocoding using OpenStreetMap's Nominatim API.
 */
public class Geolocation {
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Converts a postal code or address into latitude and longitude coordinates.
     *
     * @param address postal code or street address
     * @return an array {latitude, longitude}
     * @throws Exception if an error occurs during API call or parsing
     */
    public static double[] geocodeAddress(String address) throws Exception {
        String url = "https://nominatim.openstreetmap.org/search?q="
                + java.net.URLEncoder.encode(address, "UTF-8")
                + "&format=json&limit=1";

        Request request = new Request.Builder()
                .url(url)
                // ⚠️ Nominatim requires a valid identifying User-Agent
                .header("User-Agent", "JavaGeolocationApp/1.0 (your_email@example.com)")
                .build();
        Thread.sleep(1000);
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Geocoding failed: " + response.code() + " - " + response.message());
            }

            String body = response.body().string();

            // --- Check if the response is HTML instead of JSON ---
            if (body.trim().startsWith("<")) {
                throw new RuntimeException("Geocoding API returned HTML (possible rate limit or invalid request). Body:\n" + body);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(body);

            if (root.isEmpty()) {
                throw new RuntimeException("No results found for address: " + address);
            }

            double lat = root.get(0).path("lat").asDouble();
            double lon = root.get(0).path("lon").asDouble();
            return new double[]{lat, lon};
        }
    }

    /**
     * Reverse geocodes a latitude and longitude into a human-readable address.
     *
     * @param lat latitude
     * @param lon longitude
     * @return the address as a string, or "Unknown address" if not found
     * @throws Exception if a network or API error occurs
     */
    public static String reverseGeocode(double lat, double lon) throws Exception {
        String url = String.format("https://nominatim.openstreetmap.org/reverse?lat=%f&lon=%f&format=json", lat, lon);

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "JavaGeolocationApp/1.0 (your_email@example.com)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Reverse geocoding failed: " + response.code() + " - " + response.message());
            }

            String body = response.body().string();

            // --- Check for HTML responses (rate limit, etc.) ---
            if (body.trim().startsWith("<")) {
                throw new RuntimeException("Reverse geocoding API returned HTML. Body:\n" + body);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(body);
            return root.path("display_name").asText("Unknown address");
        }
    }

    public static boolean estanCerca(double lat1, double lon1, double lat2, double lon2, double maxDistanceKm) {
    // Radius of the Earth in kilometers
    final double EARTH_RADIUS_KM = 6371.0;

    // Convert degrees to radians
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double rLat1 = Math.toRadians(lat1);
    double rLat2 = Math.toRadians(lat2);

    // Haversine formula
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(rLat1) * Math.cos(rLat2)
            * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    double distance = EARTH_RADIUS_KM * c;

    return distance <= maxDistanceKm;
}

}
