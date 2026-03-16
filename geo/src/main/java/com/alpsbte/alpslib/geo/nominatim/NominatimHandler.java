package com.alpsbte.alpslib.geo.nominatim;


import com.alpsbte.alpslib.geo.logger.LoggerAdapter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides access to retrieving location data from Nominatim (OSM).
 * @see <a href="https://nominatim.org/release-docs/develop/api/Reverse/">Nominatim documentation</a>
 */
public class NominatimHandler {

    private final LoggerAdapter logger;
    private final String userAgent;
    private final int zoom;

    /**
     * Create a NominatimHandler object with a default zoom of 10
     * @param logger The logger to use
     * @param userAgent Your application's User Agent for the Nominatim http request.
     */
    public NominatimHandler(LoggerAdapter logger, String userAgent) {
        this(logger, userAgent, 10);
    }

    /**
     * Create a NominatimHandler object
     * @param logger The logger to use
     * @param userAgent Your application's User Agent for the Nominatim http request.
     * @param zoom The zoom for the returned data's detail
     */
    public NominatimHandler(LoggerAdapter logger, String userAgent, int zoom) {
        this.logger = logger;
        this.userAgent = userAgent;
        this.zoom = zoom;
    }

    /**
     * Get location data for coordinates
     * @param latitude The locations latitude
     * @param longitude The locations longitude
     * @return The location data or null if there were errors
     */
    public NominatimGeoLocation locationFromCoordinates(float latitude, float longitude) {
        try {
            JSONObject response;

            URL url = URI.create("https://nominatim.openstreetmap.org/reverse?lat=" + latitude + "&lon=" + longitude + "&format=json&zoom=" + this.zoom).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", this.userAgent);
            con.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            reader.lines().forEach(builder::append);
            response = new JSONObject(builder.toString());
            con.disconnect();

            if (!response.has("address")) {
                this.logger.warn("Invalid nominatim response: %s".formatted(response.toString()));
                return null;
            }

            JSONObject address = response.getJSONObject("address");

            Map<String, String> locValues = new HashMap<>();
            for (String key : address.keySet()) {
                locValues.put(key, address.getString(key));
            }

            return new NominatimGeoLocation(latitude, longitude, locValues);

        } catch (IOException e) {
            this.logger.error("Error nominatim", e);
            return null;
        }
    }

}
