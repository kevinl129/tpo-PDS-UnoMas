package com.tpopdsunomas.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Geolocation {
      private static final OkHttpClient client = new OkHttpClient();

    public static double[] geocodeAddress(String address) throws Exception {
        String url = "https://nominatim.openstreetmap.org/search?q=" +
                     java.net.URLEncoder.encode(address, "UTF-8") +
                     "&format=json&limit=1";

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "JavaGeocodeProject/1.0 (your_email@example.com)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode array = mapper.readTree(json);
            if (array.isEmpty()) throw new Exception("No results for address");
            JsonNode location = array.get(0);
            double lat = location.get("lat").asDouble();
            double lon = location.get("lon").asDouble();
            return new double[]{lat, lon};
        }
    }
}
