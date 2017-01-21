
package firstapplication.common ;

import java.io.Serializable;

public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private String line1;

    private String line2;

    private String zipCode;

    private String city;

    private String country;

    public Address() {
    }

    public Address(String line1, String line2, String zipCode, String city, String country) {
        super();
        this.line1 = line1;
        this.line2 = line2;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
