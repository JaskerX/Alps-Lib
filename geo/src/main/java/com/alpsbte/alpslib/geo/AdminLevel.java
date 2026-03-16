package com.alpsbte.alpslib.geo;

/**
 * Implementations represent either an OSM administrative level, a Nominatim address key or both for accessing parts of data, e.g. the country or city of a location
 * @see <a href="https://wiki.openstreetmap.org/wiki/Tag:boundary%3Dadministrative">OSM administrative levels</a>
 * @see <a href="https://nominatim.org/release-docs/develop/api/Reverse/">Nominatim documentation</a>
 */
public interface AdminLevel {

    /**
     * AdminLevel to provide access to country data. This is the only admin level that is the same for all countries.
     */
    AdminLevel COUNTRY = AdminLevel.of(2, "country");

    /**
     * Get a simple AdminLevel without having to implement the interface manually
     * @param level The numeric admin level if this AdminLevel is used for a {@link com.alpsbte.alpslib.geo.rgc.RgcGeoLocation}. null otherwise
     * @param nominatimKey The key for the corresponding data to get from the Nominatim responses "address" JSON object if this AdminLevel is used for a {@link com.alpsbte.alpslib.geo.nominatim.NominatimGeoLocation}. null otherwise
     * @return The AdminLevel with level and nominatimKey
     */
    static AdminLevel of(Integer level, String nominatimKey) {
        return new AdminLevel() {
            @Override
            public Integer getLevel() {
                return level;
            }

            @Override
            public String getNominatimKey() {
                return nominatimKey;
            }
        };
    }

    /**
     * The numeric admin level
     * @return The numeric admin level or null if it is not set
     * @see <a href="https://wiki.openstreetmap.org/wiki/Tag:boundary%3Dadministrative">OSM administrative levels</a>
     */
    Integer getLevel();

    /**
     * The key for the corresponding data to get from the Nominatim responses "address" JSON object
     * @return The key for the corresponding data to get from the Nominatim responses "address" JSON object
     * @see <a href="https://nominatim.org/release-docs/develop/api/Reverse/">Nominatim documentation</a>
     */
    String getNominatimKey();

}
