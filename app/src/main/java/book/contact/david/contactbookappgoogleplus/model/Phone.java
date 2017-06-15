package book.contact.david.contactbookappgoogleplus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class Phone implements Parcelable {

    private int id;
    private String number;
    private String contactId;

    private Contact contact;

    public Phone() {
        super();
    }

    private Phone(Parcel in) {
        super();
        this.id = in.readInt();
        this.number = in.readString();
        this.contactId = in.readString();

        this.contact = in.readParcelable(Contact.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Phone [id=" + id + ", number=" + number + ", contact id="
                + contactId + ", contact=" + contact + "]";
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
        Phone other = (Phone) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<Phone> CREATOR = new Parcelable.Creator<Phone>() {
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

}
