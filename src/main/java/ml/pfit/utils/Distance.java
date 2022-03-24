package ml.pfit.utils;

public class Distance {


    /**
     * Calculate distance between two points in latitude and longitude
     * Based on: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
     * @return Distance in KM (rounded)
     */
    public static Integer euclidean(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);
        return new Double(Math.sqrt(distance)/1000).intValue();
    }
}
