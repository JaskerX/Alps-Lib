package com.alpsbte.alpslib.geo.rgc;

import com.alpsbte.alpslib.geo.logger.LoggerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to retrieve location data offline. Data files, examples, ... can be found on the {@link ReverseGeocoder} creator's GitHub repo.
 * @see <a href="https://github.com/kno10/reversegeocode">GitHub repo</a>
 */
public class RgcHandler {

    private final File locationDataFile;
    private final LoggerAdapter logger;
    private final boolean localizedNames;

    /**
     * Create a RgcHandler object
     * @param locationDataFile The file with the OSM data (smallest available resolution is recommended for highest accuracy)
     * @param logger The logger to use
     * @param localizedNames Whether localized names (column 1) should be used instead of international names (column 2)
     * @see <a href="https://github.com/kno10/reversegeocode">Used GitHub repo with data files and examples</a>
     */
    public RgcHandler(File locationDataFile, LoggerAdapter logger, boolean localizedNames) {
        this.locationDataFile = locationDataFile;
        this.logger = logger;
        this.localizedNames = localizedNames;

        if (!locationDataFile.exists()) {
            this.logger.warn("File '%s' is missing. Reverse geocoding won't work.".formatted(locationDataFile.getAbsolutePath()));
        }
    }

    /**
     * Get location data for coordinates
     * @param latitude The locations latitude
     * @param longitude The locations longitude
     * @return The location data or null if there were errors
     */
    public RgcGeoLocation locationFromCoordinates(float latitude, float longitude) {

        try (ReverseGeocoder rgc = new ReverseGeocoder(this.locationDataFile.getAbsolutePath())) {

            Map<Integer, String> adminLevelsValues = new HashMap<>();

            Pattern pattern = Pattern.compile("\\t?([\\S ]*)");
            for (String s : rgc.lookup(longitude, latitude)) {
                Matcher matcher = pattern.matcher(s);

                Optional<String> nameLocalized = Optional.empty();
                Optional<String> nameEn = Optional.empty();
                Optional<Integer> adminLevel = Optional.empty();

                int count = 0;
                while (matcher.find()) {
                    switch (++count) {
                        case 1 -> nameLocalized = Optional.of(matcher.group(1));
                        case 2 -> nameEn = Optional.of(matcher.group(1));
                        case 7 -> {
                            try {
                                adminLevel = Optional.of(Integer.parseInt(matcher.group(1)));
                            } catch (NumberFormatException e) {
                                adminLevel = Optional.empty();
                            }
                        }
                    }
                }

                if (nameLocalized.isEmpty() || nameEn.isEmpty() || adminLevel.isEmpty()) {
                    this.logger.warn("Incomplete location properties from lookup: %s".formatted(s));
                    continue;
                }

                adminLevelsValues.put(adminLevel.get(), (this.localizedNames ? nameLocalized.get() : nameEn.get()));
            }

            return new RgcGeoLocation(latitude, longitude, adminLevelsValues);

        } catch (IOException e) {
            this.logger.error("An error occurred during rgc.", e);
            return null;
        }
    }

}
