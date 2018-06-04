package sample.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class MessageReceiveHistory extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Message message;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User receiver;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }


}
