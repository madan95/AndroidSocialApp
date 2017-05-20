package model;

public class Conversation {
	private int convId;
	private long otherId;
	private String secondName;
	
	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Conversation(int convId, long otherId, String secondName){
		this.convId = convId;
		this.otherId = otherId;
		this.secondName = secondName;
	}

	public int getConvId() {
		return convId;
	}

	public void setConvId(int convId) {
		this.convId = convId;
	}

	public long getOtherId() {
		return otherId;
	}

	public void setOtherId(long otherId) {
		this.otherId = otherId;
	}
	
	
}
