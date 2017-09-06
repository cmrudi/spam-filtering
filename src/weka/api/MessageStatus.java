package weka.api;

public enum MessageStatus {
	SPAM("spam"),
	HAM("ham");
	
	private final String text;
	
	private MessageStatus(String status) {
		text = status;
	}
	
	public String getStatus() {
        return text;
    }
	
}
