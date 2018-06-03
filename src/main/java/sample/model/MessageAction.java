package sample.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageAction {
    @JsonProperty("salute")
    SALUTE,
    @JsonProperty("subscribe")
    SUBSCRIBE,
    @JsonProperty("report")
    REPORT;

}
