package com.sdcProject.busReservationSystem.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeFormatter {

    /**
     * Formats a 24-hour time string to 12-hour format with AM/PM
     * @param time24 Time in 24-hour format (HH:mm:ss or HH:mm)
     * @return Time in 12-hour format (h:mm AM/PM) or "N/A" if invalid
     */
    public static String formatTimeTo12Hr(String time24) {
        if (time24 == null || time24.trim().isEmpty()) {
            return "N/A";
        }

        try {
            // Handle LocalTime objects converted to string
            if (time24 instanceof String) {
                String timeStr = time24.toString().trim();

                // Handle cases where time doesn't contain ":"
                if (!timeStr.contains(":")) {
                    return handleNumericTimeFormat(timeStr);
                }

                // Parse the time string
                LocalTime localTime = parseTimeString(timeStr);

                // Format to 12-hour with AM/PM
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                return localTime.format(formatter);
            }

            return "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * Formats a LocalTime object to 12-hour format with AM/PM
     * @param localTime LocalTime object
     * @return Time in 12-hour format (h:mm AM/PM) or "N/A" if null
     */
    public static String formatTimeTo12Hr(LocalTime localTime) {
        if (localTime == null) {
            return "N/A";
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            return localTime.format(formatter);
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * Calculates arrival time based on departure time and duration in minutes
     * @param departureTime Departure time (HH:mm:ss or HH:mm or LocalTime)
     * @param durationMinutes Duration in minutes
     * @return Arrival time in HH:mm format or "N/A" if invalid
     */
    public static String calculateArrivalTime(Object departureTime, Integer durationMinutes) {
        if (departureTime == null || durationMinutes == null) {
            return "N/A";
        }

        try {
            LocalTime departure;

            if (departureTime instanceof LocalTime) {
                departure = (LocalTime) departureTime;
            } else if (departureTime instanceof String) {
                departure = parseTimeString((String) departureTime);
            } else {
                return "N/A";
            }

            // Add duration in minutes
            LocalTime arrival = departure.plusMinutes(durationMinutes);

            // Format to HH:mm
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return arrival.format(formatter);
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * Calculates arrival time and returns it in 12-hour format
     * @param departureTime Departure time (HH:mm:ss or HH:mm or LocalTime)
     * @param durationMinutes Duration in minutes
     * @return Arrival time in 12-hour format (h:mm AM/PM) or "N/A" if invalid
     */
    public static String calculateArrivalTime12Hr(Object departureTime, Integer durationMinutes) {
        if (departureTime == null || durationMinutes == null) {
            return "N/A";
        }

        try {
            LocalTime departure;

            if (departureTime instanceof LocalTime) {
                departure = (LocalTime) departureTime;
            } else if (departureTime instanceof String) {
                departure = parseTimeString((String) departureTime);
            } else {
                return "N/A";
            }

            // Add duration in minutes
            LocalTime arrival = departure.plusMinutes(durationMinutes);

            // Format to 12-hour with AM/PM
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            return arrival.format(formatter);
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * Helper method to parse time strings in various formats
     * @param timeStr Time string (HH:mm:ss or HH:mm)
     * @return LocalTime object
     */
    private static LocalTime parseTimeString(String timeStr) throws DateTimeParseException {
        timeStr = timeStr.trim();

        // Try parsing with seconds first (HH:mm:ss)
        try {
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            // Try parsing without seconds (HH:mm)
            try {
                return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e2) {
                // Try with single digit hours (H:mm)
                return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("H:mm"));
            }
        }
    }

    /**
     * Handles numeric time formats like "93000" or "0930"
     * @param timeStr Numeric time string
     * @return Time in 12-hour format or "N/A"
     */
    private static String handleNumericTimeFormat(String timeStr) {
        if (timeStr.length() < 3) {
            return "N/A";
        }

        try {
            // Extract hours and minutes
            int hours = Integer.parseInt(timeStr.substring(0, timeStr.length() - 2));
            int minutes = Integer.parseInt(timeStr.substring(timeStr.length() - 2));

            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                return "N/A";
            }

            LocalTime localTime = LocalTime.of(hours, minutes);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            return localTime.format(formatter);
        } catch (Exception e) {
            return "N/A";
        }
    }
}