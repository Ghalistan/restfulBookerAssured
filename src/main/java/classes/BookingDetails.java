package classes;

import com.github.javafaker.Faker;

public final class BookingDetails {
    public final String firstname = Faker.instance().name().firstName();
    public final String lastname = Faker.instance().name().lastName();
    public final Integer totalprice = Faker.instance().number().numberBetween(1, 200);
    public boolean depositpaid = Faker.instance().bool().bool();
    public final BookingDates bookingdates;
    public final String additionalneeds = Faker.instance().hacker().noun();

    BookingDetails() {
        bookingdates = new BookingDates();
    }
}
