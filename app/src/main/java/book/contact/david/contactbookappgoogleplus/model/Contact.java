package book.contact.david.contactbookappgoogleplus.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class Contact implements Parcelable {

    private int id;
    private String firstName;
    private String lastName;
    private String userId;

    public Contact() {
        super();
    }

    private Contact(Parcel in) {
        super();
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.userId = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", first name=" + firstName + ", last name=" + lastName + ", user id=" + userId + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getFirstName());
        parcel.writeString(getLastName());
        parcel.writeString(getUserId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        if (id != other.id)
            return false;

        return true;
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
