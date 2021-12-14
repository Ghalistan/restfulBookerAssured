import static helper.Comparator.isTheSameBooking;
import static helper.Comparator.isTheSameName;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import classes.BookerName;
import classes.Booking;
import classes.BookingDetails;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;

public class restfulBookerTest {
    static HashMap<String, String> adminCred = new HashMap<>();

    @BeforeAll
    static void setup() {
        baseURI = "https://restful-booker.herokuapp.com";
        enableLoggingOfRequestAndResponseIfValidationFails();
        adminCred.put("username", "admin");
        adminCred.put("password", "password123");
    }

    @Test
    void createToken() {
        Response resp = given()
                .header("Content-Type", "application/json")
                .body(adminCred)
                .when()
                .post("/auth");

        resp.then().assertThat().statusCode(200).body("token", notNullValue());
    }

    @Test
    void getBookingId() {
        given().get("/booking").then().assertThat().statusCode(200);
    }

    @Test
    void getBooking() {
        given().get(String.format("/booking/%d", 1)).then().assertThat().statusCode(200);
    }

    @Test
    void createBooking() {
        given()
                .header("Content-Type", "application/json")
                .body(new Booking().booking)
                .when()
                .post("/booking")
                .then()
                .assertThat().statusCode(200);
    }

    @Nested
    class editingBookingDetails {
        static String token;
        Booking booked;

        @BeforeAll
        static void adminCred() {
            token = given()
                    .header("Content-Type", "application/json")
                    .body(adminCred)
                    .when()
                    .post("/auth")
                    .path("token");
        }

        @BeforeEach
        void createNewBooking() {
            booked = new Booking();

            Response resp = given()
                    .header("Content-Type", "application/json")
                    .body(booked.booking)
                    .when()
                    .post("/booking");

            resp.then().assertThat().statusCode(200);
            booked = resp.as(Booking.class);
        }

        @Test
        void updateBooking() {
            Booking newBook = new Booking();

            Response resp = given()
                    .header("Content-Type", "application/json")
                    .header("Cookie", String.format("token=%s", token))
                    .body(newBook.booking)
                    .when()
                    .put(String.format("/booking/%d", booked.bookingid));

            resp
                    .then()
                    .assertThat().statusCode(200);

            Booking updatedBook = new Booking();
            updatedBook.booking = resp.as(BookingDetails.class);
            Assertions.assertTrue(isTheSameBooking(newBook.booking, updatedBook.booking));
        }

        @Test
        void partialUpdateBooking() {
            BookerName newName = new BookerName(
                    Faker.instance().name().firstName(),
                    Faker.instance().name().lastName()
            );

            Response resp = given()
                    .header("Content-Type", "application/json")
                    .header("Cookie", String.format("token=%s", token))
                    .body(newName)
                    .when()
                    .patch(String.format("/booking/%d", booked.bookingid));

            resp
                    .then()
                    .assertThat().statusCode(200);

            BookingDetails updatedBooking = resp.as(BookingDetails.class);

            Assertions.assertTrue(
                    isTheSameName(
                            newName,
                            new BookerName(updatedBooking.firstname, updatedBooking.lastname)
                    )
            );
        }

        @Test
        void deleteBooking() {
            given()
                    .header("Content-Type", "application/json")
                    .header("Cookie", String.format("token=%s", token))
                    .when()
                    .delete(String.format("/booking/%d", booked.bookingid))
                    .then()
                    .assertThat().statusCode(201);
        }
    }

    @Test
    void healthCheck() {
        given()
                .get("/ping")
                .then()
                .assertThat().statusCode(201);
    }
}
