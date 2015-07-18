package miroshnychenko.mykola.popularmovies.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDateHelper {

    public static String parseReleaseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format(sdf, date);

    }

    public static String format(SimpleDateFormat sdf, String date) {

        String thisdate = date;
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
        return df.format(parsedDate);
    }

}
