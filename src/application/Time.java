package application;
import java.util.*;

public static String convertToTime(int digits, boolean showLocalTime) {
    // This can either take 4 digits as input and convert it into time or show local time.
    if (digits < 0 || digits > 2400) {
        return "Invalid input";
    }
    int hours = digits / 100;
    int minutes = digits % 100;
    if (showLocalTime) {
        Calendar calendar = Calendar.getInstance();
        hours += calendar.get(Calendar.HOUR_OF_DAY);
        minutes += calendar.get(Calendar.MINUTE);
        if (minutes >= 60) {
            hours += 1;
            minutes -= 60;
        }
        if (hours >= 24) {
            hours -= 24;
        }
    }
    return String.format("%02d:%02d", hours, minutes);
}
