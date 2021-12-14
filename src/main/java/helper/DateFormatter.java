package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String changeDateFormat(Date date) {
        return sdf.format(date);
    }
}
