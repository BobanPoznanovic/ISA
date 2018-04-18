package isa.project.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SeatMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9072012896914689870L;

	private boolean[][] freeSeats;
	
	private SeatType[][] seatTypes;
	
	public SeatMap(int rows, int cols) {
		super();
		this.freeSeats = new boolean[rows][cols];
		this.seatTypes = new SeatType[rows][cols];
	}

	public boolean[][] getFreeSeats() {
		return freeSeats;
	}

	public void setFreeSeats(boolean[][] freeSeats) {
		this.freeSeats = freeSeats;
	}

	public SeatType[][] getSeatTypes() {
		return seatTypes;
	}

	public void setSeatTypes(SeatType[][] seatTypes) {
		this.seatTypes = seatTypes;
	}
	
	public static byte[] convertToBytes(SeatMap seatMap) throws IOException {
	    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
	         ObjectOutput out = new ObjectOutputStream(bos)) {
	        out.writeObject(seatMap);
	        return bos.toByteArray();
	    } 
	}

	public static Object convertToSeatMap(byte[] bytes) throws IOException, ClassNotFoundException {
	    try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	         ObjectInput in = new ObjectInputStream(bis)) {
	        return in.readObject();
	    } 
	}
	
}
