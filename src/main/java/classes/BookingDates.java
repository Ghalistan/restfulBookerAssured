package classes;

import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

import static helper.DateFormatter.changeDateFormat;

public final class BookingDates {
    public final String checkin = changeDateFormat(
            Faker.instance().date().past(365, TimeUnit.DAYS)
    );

    public final String checkout = changeDateFormat(
            Faker.instance().date().future(365, TimeUnit.DAYS)
    );
}