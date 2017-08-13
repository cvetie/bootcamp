import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TicketOfficeTest {

	TicketOffice ticketOffice = new TicketOffice();
	ReservationRequest request = new ReservationRequest(null, 0);

	@Test
	public void acquireUniqueBookingIdShouldReturnNonEmptyString() throws Exception {
		assertThat(ticketOffice.acquireUniqueBookingId(), is(not("")));
	}

	@Test
	public void acquireTrainDataShouldReturnNonEmptyString() throws Exception {
		assertThat(ticketOffice.acquireTrainData(), is(not("")));
	}

	@Test
	public void reservationTrainIdShouldEqualReservationRequestTrainID() throws Exception {
		request = new ReservationRequest("express_2000", 2);
		assertThat(ticketOffice.makeReservation(request).trainId, is(equalTo(request.trainId)));
	}

	@Test
	public void validTrainReservationShouldHaveNonEmptyBookingId() throws Exception {
		request = new ReservationRequest("express_2000", 2);
		assertThat(ticketOffice.makeReservation(request).bookingId, is(not("")));
	}

	@Test
	public void ifReservedSeatsOver70PercentTrainReservationShouldHaveEmptyBookingId() throws Exception {
		request = new ReservationRequest("express_2000", 70);
		assertThat(ticketOffice.makeReservation(request).bookingId, is(""));
	}

//	@Test
//	public void testIfPostRequestIsSuccesful() throws Exception {
//		HTTPConnection connection = new HTTPConnection();
//		JSONParser parser = new JSONParser();
//		Seat seat1 = new Seat("A", 4);
//		Seat seat2 = new Seat("A", 5);
//		List<Seat> seats = new ArrayList<Seat>();
//		seats.add(seat1);
//		seats.add(seat2);
//		Reservation reservation = new Reservation("express_2000", seats, "75bcd16");
//		JSONObject object = parser.parseReservation(reservation);
//		System.out.println("Object " + object.toString());
//		assertThat(connection.sendPost(object, "http://localhost:8081/reserve"), is(not("")));
//	}

	@Test
	public void if4SeatsRequestedShouldReturnAListOf4() throws Exception {
		request = new ReservationRequest("express_2000", 4);
		ticketOffice.makeReservation(request);
		assertThat(ticketOffice.bookedSeats.size(), is((4)));
	}

	@Test
	public void if16SeatsRequestedShouldReturn0() throws Exception {
		request = new ReservationRequest("express_2000", 16);
		ticketOffice.makeReservation(request);
		assertThat(ticketOffice.bookedSeats.size(), is((0)));
	}

	@Test
	public void if12SeatsRequestedshouldReturn0() throws Exception {
		request = new ReservationRequest("express_2000", 12);
		ticketOffice.makeReservation(request);
		assertThat(ticketOffice.bookedSeats.size(), is((0)));
	}
//	
//	@Test
//	public void if6SeatsRequestedShouldReturnAListOf6() throws Exception {
//		request = new ReservationRequest("express_2000", 6);
//		ticketOffice.makeReservation(request);
//		assertThat(ticketOffice.bookedSeats.size(), is((6)));
//	}
		
//	@Test
//	public void ifTwoCoachesShouldReturnCoachWithMoreEmptySeats() throws Exception {
//		request = new ReservationRequest("express_2000", 6);
//		ticketOffice.makeReservation(request);
//		assertThat(ticketOffice.calculateCoachWithMoreAvailability(), is(("B")));
//	}


	@Test
	public void if8SeatsRequestedButNotEnoughAvailableSeatsInTheSameCoachShouldReturn0() throws Exception {
		request = new ReservationRequest("express_2000", 8);
		ticketOffice.makeReservation(request);
		assertThat(ticketOffice.bookedSeats.size(), is((0)));
	}

	@Test
	public void if10SeatsRequestedButNotEnoughAvailableSeatsInTheSameCoachShouldReturn0() throws Exception {
		request = new ReservationRequest("express_2000", 10);
		ticketOffice.makeReservation(request);
		assertThat(ticketOffice.bookedSeats.size(), is((0)));
	}
	
	@Test
	public void testIfReservationSuccessfullySent() throws Exception {
		request = new ReservationRequest("express_2000", 2);
		ticketOffice.makeReservation(request);
		assertThat(ticketOffice.reservationSuccessRespond, is(not("")));
	}



	

}
