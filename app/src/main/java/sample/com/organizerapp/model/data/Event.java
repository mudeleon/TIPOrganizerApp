package sample.com.organizerapp.model.data;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject {




    @SerializedName("id")
    @PrimaryKey
    private String id;
    @SerializedName("name")
    private String eventName;
    @SerializedName("description")
    private String eventDescription;
    @SerializedName("image")
    private String eventImage;
    @SerializedName("price")
    private String eventPrice;
    @SerializedName("ticket_max")
    private String eventTicketMax;
    @SerializedName("totalLikes")
    private String eventLike;
    @SerializedName("tags")
    private RealmList<Tags> eventTags;

    @SerializedName("location")
    private RealmList<Location> eventLocation;

    @SerializedName("timestamp")
    private RealmList<Timestamp> eventTimestamp;

    @SerializedName("contact")
    private RealmList<Contact> eventContact;


    public Event() {
    }




    public String getEventLike() {
        return eventLike;
    }

    public void setEventLike(String eventLike) {
        this.eventLike = eventLike;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventTicketMax() {
        return eventTicketMax;
    }

    public void setEventTicketMax(String eventTicketMax) {
        this.eventTicketMax = eventTicketMax;
    }

    public RealmList<Tags> getEventTags() {
        return eventTags;
    }

    public void setEventTags(RealmList<Tags> eventTags) {
        this.eventTags = eventTags;
    }

    public RealmList<Location> getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(RealmList<Location> eventLocation) {
        this.eventLocation = eventLocation;
    }

    public RealmList<Timestamp> getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(RealmList<Timestamp> eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public RealmList<Contact> getEventContact() {
        return eventContact;
    }

    public void setEventContact(RealmList<Contact> eventContact) {
        this.eventContact = eventContact;
    }

}



