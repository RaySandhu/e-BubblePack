public static String convertToTime(int digits) {
    if (digits < 0 || digits > 2400) {
        return "Invalid input";
    }
    int hours, minutes;
    if (digits < 1000) {
        hours = digits / 100;
        minutes = digits % 100;
    } else {
        hours = digits / 100;
        minutes = digits % 100;
    }
    return String.format("%02d:%02d", hours, minutes);
}
