package helper;

import classes.BookerName;
import classes.BookingDetails;
import org.jetbrains.annotations.NotNull;

public class Comparator {
    public static boolean isTheSameBooking(@NotNull BookingDetails currentBooker, @NotNull BookingDetails newBooker) {
        return currentBooker.firstname.equals(newBooker.firstname) &&
                currentBooker.lastname.equals(newBooker.lastname) &&
                currentBooker.totalprice.equals(newBooker.totalprice) &&
                currentBooker.bookingdates.checkin.equals(newBooker.bookingdates.checkin) &&
                currentBooker.bookingdates.checkout.equals(newBooker.bookingdates.checkout) &&
                currentBooker.additionalneeds.equals(newBooker.additionalneeds);
    }

    public static boolean isTheSameName(@NotNull BookerName currentBooker, @NotNull BookerName newBooker) {
        return currentBooker.firstname.equals(newBooker.firstname) &&
                currentBooker.lastname.equals(newBooker.lastname);
    }
}
