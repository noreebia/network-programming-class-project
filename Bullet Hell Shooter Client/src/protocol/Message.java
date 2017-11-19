package protocol;

import java.io.Serializable;

public class Message implements Serializable{
	
	private String contents;
	private Object data;
	
	public Message(String contents, Object data) {
		this.contents = contents;
		this.data = data;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	public String getContents() {
		return contents;
	}
	
	public Object getData() {
		return data;
	}
}
