package app.com.ChinChen.payload.response;

public class MessageResponse {
  private String message;
  private int status;

  public MessageResponse(int status, String message) {
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
