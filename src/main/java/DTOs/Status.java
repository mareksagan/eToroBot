package DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    private int rCode;
    private String bCodeMessage;
    private String developerMessage;

    @JsonProperty("rCode")
    public int getRCode() {
        return rCode;
    }

    @JsonProperty("rCode")
    public void setRCode(int rCode) {
        this.rCode = rCode;
    }

    @JsonProperty("bCodeMessage")
    public String getBCodeMessage() {
        return bCodeMessage;
    }

    @JsonProperty("bCodeMessage")
    public void setBCodeMessage(String bCodeMessage) {
        this.bCodeMessage = bCodeMessage;
    }

    @JsonProperty("developerMessage")
    public String getDeveloperMessage() {
        return developerMessage;
    }

    @JsonProperty("developerMessage")
    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
