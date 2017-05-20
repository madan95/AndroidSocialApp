package model;


public class MessageData {
	private String message;
	private long senderId;
	private long receiverId;
	private String senderName;
	private String receiverName;
	private String timeSend;
	
	public MessageData(String message, long senderId, long receiverId, String senderName2, String receverName, String time){
		this.message = message;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.senderName = senderName2;
		this.receiverName = receverName;
		this.timeSend = time;
		System.out.println(timeSend);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getTimeSend() {
		return timeSend;
	}
	public void setTimeSend(String timeSend) {
		this.timeSend = timeSend;
	}
	
	
}
