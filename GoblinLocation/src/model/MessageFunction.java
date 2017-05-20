package model;

public class MessageFunction {
private String recevierName;
private long recevierId;
private long senderId;

public MessageFunction(String name, long receiver){
	this.recevierName = name;
	this.recevierId = receiver;
}

public String getRecevierName() {
	return recevierName;
}

public void setRecevierName(String recevierName) {
	this.recevierName = recevierName;
}

public long getRecevierId() {
	return recevierId;
}

public void setRecevierId(long recevierId) {
	this.recevierId = recevierId;
}

public long getSenderId() {
	return senderId;
}

public void setSenderId(long senderId) {
	this.senderId = senderId;
}

}
