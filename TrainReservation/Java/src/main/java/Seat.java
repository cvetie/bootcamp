
public class Seat {
	public final String coach;
	public final int seatNumber;
	public String bookingId = "";

	public Seat(String coach, int seatNumber) {
		this.coach = coach;
		this.seatNumber = seatNumber;
	}

	public Seat(String coach, int seatNumber, String bookingId) {
		this.coach = coach;
		this.seatNumber = seatNumber;
		this.bookingId = bookingId;
	}

	public boolean equals(Object o) {
		Seat other = (Seat) o;
		return coach == other.coach && seatNumber == other.seatNumber;
	}
	
	public String toString() {
		return Integer.toString(seatNumber) + coach;
	}
}