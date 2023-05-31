public class ContactModel {
    private String contactName;
    private String phoneNumber;
    private String emailAddress;
    private String organization;

    public ContactModel(String contactName, String phoneNumber, String emailAddress, String organization) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.organization = organization;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getOrganization() {
        return organization;
    }
}
