package com.tpopdsunomas.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * The Localizador class connects to the OpenStreetMap Overpass API to find nearby
 * sports facilities (e.g., soccer fields, basketball courts, etc.) within a given radius.
 * If the address is not available in the OSM data, it uses the Geolocation class to
 * perform a reverse geocoding lookup and get a readable address.
 */
public class Localizador {
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Finds nearby sports fields using OpenStreetMap's Overpass API.
     * If a field has no address, it uses Geolocation.reverseGeocode() to get one.
     *
     * @param lat      latitude coordinate of the search center
     * @param lon      longitude coordinate of the search center
     * @param distance search radius in meters (e.g., 2000 for 2 km)
     * @return a list of strings containing the sport type and address for each nearby field
     * @throws Exception if there is a network or API error
     */
    public static List<String> findSportsFields(double lat, double lon, int distance) throws Exception {
        List<String> results = new ArrayList<>();

        // --- Step 1: Build the Overpass API query ---
        String query = String.format("""
            [out:json];
            (
              node["leisure"="pitch"](around:%d,%f,%f);
              way["leisure"="pitch"](around:%d,%f,%f);
            );
            out center;
        """, distance, lat, lon, distance, lat, lon);

        // --- Step 2: Construct the Overpass API request URL ---
        String url = "https://overpass-api.de/api/interpreter?data=" + java.net.URLEncoder.encode(query, "UTF-8");

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "JavaLocalizador/1.0 (your_email@example.com)")
                .build();

        // --- Step 3: Execute the HTTP request and process the response ---
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Error in Overpass API request: " + response);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body().string());
            JsonNode elements = root.path("elements");

            // --- Step 4: Iterate through each sports field found ---
            for (JsonNode element : elements) {
                JsonNode tags = element.path("tags");

                String sport = tags.path("sport").asText("Unknown sport");
                String address = tags.path("addr:street").asText("");
                String houseNumber = tags.path("addr:housenumber").asText("");
                String city = tags.path("addr:city").asText("");

                // --- Get the coordinates for reverse geocoding if needed ---
                double elemLat;
                double elemLon;

                // For "node" elements: lat/lon are directly available
                if (element.has("lat") && element.has("lon")) {
                    elemLat = element.get("lat").asDouble();
                    elemLon = element.get("lon").asDouble();
                }
                // For "way" elements: center.lat/lon provides approximate coordinates
                else if (element.has("center")) {
                    elemLat = element.get("center").get("lat").asDouble();
                    elemLon = element.get("center").get("lon").asDouble();
                } else {
                    // Skip if coordinates are missing
                    continue;
                }

                // --- Step 5: Build readable address ---
                String fullAddress;
                if (!address.isEmpty()) {
                    fullAddress = address;
                    if (!houseNumber.isEmpty()) fullAddress += " " + houseNumber;
                    if (!city.isEmpty()) fullAddress += ", " + city;
                } else {
                    // --- Step 6: Use Geolocation.reverseGeocode if no address in OSM tags ---
                    try {
                        fullAddress = Geolocation.reverseGeocode(elemLat, elemLon);
                        Thread.sleep(1100);
                    } catch (Exception e) {
                        fullAddress = "Unknown address";
                    }
                }

                // --- Step 7: Add result entry as "Sport - Address" ---
                results.add(sport + " - " + fullAddress);
            }
        }

        return results;
    }
}
