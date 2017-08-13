import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

public class JSONParser {

	String key;
	String coach;
	String seatNumber;
	String bookingId;

	public List<Seat> parseTrainData(String trainData) throws ParseException {
		JSONObject trainReservationData = new JSONObject(trainData);
		JSONObject seats = (JSONObject) trainReservationData.get("seats");
		Iterator<String> iterator = seats.keys();
		List<Seat> trainSeats = new ArrayList<Seat>();
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			coach = (String) ((JSONObject) seats.get(key)).get("coach");
			seatNumber = (String) ((JSONObject) seats.get(key)).get("seat_number");
			bookingId = (String) ((JSONObject) seats.get(key)).get("booking_reference");
			trainSeats.add(new Seat(coach, Integer.parseInt(seatNumber), bookingId));
		}
		return trainSeats;
	}

	public JSONObject parseReservation(Reservation reservation) throws ParseException {
		JSONObject reservationObject = new JSONObject();
		JSONArray reservedSeats = new JSONArray();
		for (Seat seat: reservation.seats) {
			reservedSeats.put(seat.toString());
		}
		reservationObject.put("train_id", reservation.trainId);
		reservationObject.put("seats", reservedSeats);
		reservationObject.put("booking_reference", reservation.bookingId);
		return reservationObject;
	}

}
