package sample.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dozer.Mapping;
import sample.model.MessageAction;


public class MessageDto {
    @Mapping("text")
    private String message;
    private boolean secret;
    @Mapping("user.name")
    private String name;
    @Mapping("time")
    private String epoch;
    private String hash;
    @Mapping("room.name")
    private String room;
    private MessageAction action = MessageAction.MESSAGE;

    @JsonProperty("text")
    public String getMessage() {
        return message;
    }
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }
    @JsonProperty("author")
    public String getName() {
        return name;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getHash() {
        return hash;
    }
    @JsonProperty
    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public MessageAction getAction() {
        return action;
    }

    public void setAction(MessageAction action) {
        this.action = action;
    }
}
