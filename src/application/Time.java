package application;
import java.util.*;

public static String convertToTime(int digits, boolean showLocalTime) {
    // This can either take 4 digits as input and convert it into time or show local time.
    if (digits < 0 || digits > 2400) {
        return "Invalid input";
    }
    int hours = digits / 100;
    int minutes = digits % 100;
    return String.format("%02d:%02d", hours, minutes);
}
