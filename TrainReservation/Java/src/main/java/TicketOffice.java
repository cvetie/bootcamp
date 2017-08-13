import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class TicketOffice {
	String bookingIdURL = "http://localhost:8082/booking_reference";
	String trainDataURL = "http://localhost:8081/data_for_train/express_2000";
	String reserveSeatsURL = "http://localhost:8081/reserve";

	HTTPConnection connection = new HTTPConnection();

	String bookingId = "";
	List<Seat> bookedSeats = new ArrayList<Seat>();

	String reservationSuccessRespond;

	Train train;

	public Reservation makeReservation(ReservationRequest request) throws Exception {
		train = new Train(acquireTrainData());
		setReservationParameters(request.seatCount);
		Reservation reservation = new Reservation(request.trainId, bookedSeats, bookingId);
		reservationSuccessRespond = sendReservation(reservation);
		System.out.println("reservationSuccessRespond " + reservationSuccessRespond);
		return reservation;
	}

	private void setReservationParameters(int seatCount) throws Exception {
		if (isRequestedSeatCountAvailable(seatCount)) {
			bookingId = acquireUniqueBookingId();
			bookedSeats = bookSeats(seatCount);
		}
	}

	public List<Seat> bookSeats(int seatCount) {
		String coachWithMoreSeats = train.calculateCoachWithMoreAvailability();
		if (coachWithMoreSeats.equals("A")) {
			return bookSeatsInCoach(seatCount, train.coachAEmptySeats);
		} else if (coachWithMoreSeats.equals("B")) {
			return bookSeatsInCoach(seatCount, train.coachBEmptySeats);
		} else
			return null;
	}

	public List<Seat> bookSeatsInCoach(int seatCount, List<Seat> coachSeats) {
		for (Seat seat : coachSeats) {
			if (bookedSeats.size() < seatCount) {
				bookedSeats.add(seat);
			}
		}
		return bookedSeats;
	}

	private boolean isRequestedSeatCountAvailable(int seatCount) {
		return train.isRequestedSeatCountAvailable(seatCount);
	}

	public String acquireTrainData() throws Exception {
		return connection.sendGet(trainDataURL);
	}

	public String acquireUniqueBookingId() throws Exception {
		return connection.sendGet(bookingIdURL);
	}

	public String sendReservation(Reservation reservation) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject reservationObject = parser.parseReservation(reservation);
		return connection.sendPost(reservationObject, reserveSeatsURL);
	}

}