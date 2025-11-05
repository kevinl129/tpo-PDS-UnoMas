package com.tpopdsunomas.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Localizador {
    private static final OkHttpClient client = new OkHttpClient();

public static void findSportsFields(double lat, double lon) throws Exception {
    // --- Step 1: Define an Overpass API query ---
    // The query looks for OSM elements (nodes + ways) tagged with leisure=pitch
    // within a radius of 2000 meters (2 km) around the given coordinates.
    String query = String.format("""
        [out:json];
        (
          node["leisure"="pitch"](around:2000,%f,%f);
          way["leisure"="pitch"](around:2000,%f,%f);
        );
        out center;
        """, lat, lon, lat, lon);

    // --- Step 2: Prepare the HTTP request ---
    // We're using OkHttp to send the query to the Overpass API endpoint.
    Request request = new Request.Builder()
            .url("https://overpass-api.de/api/interpreter")
            .post(okhttp3.RequestBody.create(
                    query,
                    okhttp3.MediaType.parse("application/x-www-form-urlencoded")))
            .header("User-Agent", "JavaGeocodeProject/1.0 (your_email@example.com)")
            .build();

    // --- Step 3: Execute the HTTP request and process the response ---
    try (Response response = client.newCall(request).execute()) {
        String json = response.body().string();

        // Parse the JSON response using Jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode elements = root.get("elements");

        // --- Step 4: Loop through each element (sports field) ---
        for (JsonNode el : elements) {
            // Read name, defaulting to "Unnamed field" if missing
            String name = el.path("tags").path("name").asText("Unnamed field");

            // Read type of sport (soccer, basketball, tennis, etc.)
            String sport = el.path("tags").path("sport").asText("Unknown sport");

            // Build a readable address if available
            String street = el.path("tags").path("addr:street").asText("");
            String housenumber = el.path("tags").path("addr:housenumber").asText("");
            String city = el.path("tags").path("addr:city").asText("");

            // Combine street + house number and append city if present
            String address = String.join(" ", street, housenumber).trim();
            if (!city.isEmpty()) {
                address = address.isEmpty() ? city : address + ", " + city;
            }

            // Extract coordinates (some are in 'lat/lon', some under 'center')
            double latResult = el.has("lat") ? el.get("lat").asDouble()
                    : el.path("center").path("lat").asDouble();
            double lonResult = el.has("lon") ? el.get("lon").asDouble()
                    : el.path("center").path("lon").asDouble();

            // --- Step 5: Print formatted output ---
            System.out.printf("⚽ %s — %s — %s [%.6f, %.6f]%n",
                    name,
                    sport,
                    address.isEmpty() ? "No address" : address,
                    latResult,
                    lonResult);
        }
    }
}
}
