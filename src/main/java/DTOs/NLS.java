package DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NLS {
    private String nlsTime;
    private String nlsPrice;
    private String nlsShareVolume;

    @JsonProperty("nlsTime")
    public String getNlsTime() {
        return nlsTime;
    }

    @JsonProperty("nlsTime")
    public void setNlsTime(String nlsTime) {
        this.nlsTime = nlsTime;
    }

    @JsonProperty("nlsPrice")
    public String getNlsPrice() {
        return nlsPrice;
    }

    @JsonProperty("nlsPrice")
    public void setNlsPrice(String nlsPrice) {
        this.nlsPrice = nlsPrice;
    }

    @JsonProperty("nlsShareVolume")
    public String getNlsShareVolume() {
        return nlsShareVolume;
    }

    @JsonProperty("nlsShareVolume")
    public void setNlsShareVolume(String nlsShareVolume) {
        this.nlsShareVolume = nlsShareVolume;
    }
}
