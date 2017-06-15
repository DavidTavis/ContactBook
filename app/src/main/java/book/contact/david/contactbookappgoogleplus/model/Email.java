package book.contact.david.contactbookappgoogleplus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class Email implements Parcelable{
    private int id;
    private String email;
    private String contactId;

    private Contact contact;

    public Email() {
        super();
    }

    private Email(Parcel in) {
        super();
        this.id = in.readInt();
        this.email = in.readString();
        this.contactId = in.readString();

        this.contact = in.readParcelable(Contact.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "Email [id=" + id + ", email=" + email + ", contact id="
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
        Email other = (Email) obj;
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

    public static final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
        public Email createFromParcel(Parcel in) {
            return new Email(in);
        }

        public Email[] newArray(int size) {
            return new Email[size];
        }
    };

}
