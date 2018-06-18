package sample.com.organizerapp.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;



/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class User extends RealmObject {



    @SerializedName("user_id")
    @PrimaryKey
    private int userID;
    @SerializedName("email")
    private String emailAddress;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("bday")
    private String birthday;
    @SerializedName("birth_place")
    private String birthPlace;
    @SerializedName("contact")
    private String cpNumber;


    @SerializedName("tagss")
    private RealmList<Tags> companyTags;


    @SerializedName("description")
    private String companyDescription;

    @SerializedName("api_token")
    private String apiToken;
    @SerializedName("contact_prefix")
    private String profileContactPrefix;
    @SerializedName("contact_no")
    private String profileContactNo;

    private String gcmToken;
    @SerializedName("user_image")
    private String image;

    public User() {
    }

    public String getProfileContactPrefix() {
        return profileContactPrefix;
    }

    public void setProfileContactPrefix(String profileContactPrefix) {
        this.profileContactPrefix = profileContactPrefix;
    }

    public String getProfileContactNo() {
        return profileContactNo;
    }

    public void setProfileContactNo(String profileContactNo) {
        this.profileContactNo = profileContactNo;
    }



    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCpNumber() {
        return cpNumber;
    }

    public void setCpNumber(String cpNumber) {
        this.cpNumber = cpNumber;
    }

    public RealmList<Tags> getCompanyTags() {
        return companyTags;
    }

    public void setCompanyTags(RealmList<Tags> companyTags) {
        this.companyTags = companyTags;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFullContact() {
        return profileContactPrefix + " " + profileContactNo;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

}
