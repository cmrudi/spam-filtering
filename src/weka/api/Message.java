package weka.api;

public class Message {
	String message;
	MessageStatus messageStatus;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public MessageStatus getMessageStatus() {
		return messageStatus;
	}
	
	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

}
