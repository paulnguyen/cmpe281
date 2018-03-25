package hello;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.util.Assert;

@Entity
public class Customer extends AbstractEntity {

    private String firstName ; 
    private String lastName ;

    @Column(unique = true)
    private String email ;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    private Set<Address> addresses = new HashSet<Address>();

    public Customer(String firstname, String lastname, String email ) {
        Assert.hasText(firstname) ;
        Assert.hasText(lastname) ;
        this.firstName = firstname ;
        this.lastName = lastname ;
        this.email = email ;
    }

    protected Customer() {}

    public String getFirstName() {
        return this.firstName ;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname ;
    }

    public String getLastName() {
        return this.lastName ;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname ;
    }

    public String getEmail() {
        return this.email ;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void add(Address address) {
        Assert.notNull(address);
        this.addresses.add(address);
    }

    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }
    
    @Override
    public String toString() {
        return String.format(
                "Customer [id=%d, First Name='%s', Last Name='%s', Email='%s']",
                getId(), firstName, lastName, email );
    }

}
