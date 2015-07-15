/**
 * Class for client
 * @author Zafar
 */
public class Client {
    
    private Integer id;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private String city;
    private String zipcode;
    private String email;
    
    //constructor for client class
    public Client(int id, String firstname, String lastname, String phone, 
            String address, String city, String zipcode, String email)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.email = email;
    }
    
    //constructor without id number parameter
    public Client( String firstname, String lastname, String phone, 
            String address, String city, String zipcode, String email)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.email = email;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * String representation of Client information
     */
    public String toString(){
        return String.format("Client [ID = %d, Firstname = %s, Lastname = %s, Phone = %s, Address = %s, City = %s, Zipcode = %s, Email = %s]", 
                id, firstname, lastname, phone, address, city, zipcode, email);
    }
}
