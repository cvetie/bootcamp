import java.util.ArrayList;
import java.util.List;

public class Train {

	private String trainData;
	public List<Seat> seats = new ArrayList<Seat>();
	public List<Seat> emptySeats;
	List<Seat> coachAEmptySeats = new ArrayList<Seat>();
	List<Seat> coachBEmptySeats = new ArrayList<Seat>();

	public Train(String trainData) throws Exception {
		this.trainData = trainData;
		parseTrainData();
	}

	public void parseTrainData() throws Exception {
		JSONParser parser = new JSONParser();
		seats.addAll(parser.parseTrainData(trainData));
	}

	public int getMaxAvailableSeats() {
		return (int) (seats.size() * 0.7);
	}

	public List<Seat> getEmptySeats() {
		if (emptySeats == null) {
			calculateEmptySeats();
		}
		return emptySeats;
	}

	private void calculateEmptySeats() {
		emptySeats = new ArrayList<Seat>();
		for (Seat seat : seats) {
			if (seat.bookingId == "" || seat.bookingId.isEmpty()) {
				emptySeats.add(seat);
			}
		}
	}

	public int getReservedSeatsCount() {
		return seats.size() - getEmptySeats().size();
	}

	public boolean isRequestedSeatCountAvailable(int seatCount) {
		return (seatCount + getReservedSeatsCount()) <= getMaxAvailableSeats();
	}
	
	public void splitEmptySeatsIntoCoaches() {
		for (Seat seat : emptySeats) {
			if (seat.coach.equals("A")) {
				coachAEmptySeats.add(seat);
			} else if (seat.coach.equals("B")) {
				coachBEmptySeats.add(seat);
			}
		}
	}
	
	public String calculateCoachWithMoreAvailability() {
		splitEmptySeatsIntoCoaches();
		return (coachAEmptySeats.size() > coachBEmptySeats.size() ? "A" : "B");
	}

}
