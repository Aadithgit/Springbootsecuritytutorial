package net.springbootex.springbootbackend.model;

import jakarta.persistence.*;



import java.io.Serializable;

@Entity
@Table(name = "employees")





public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email_id")
    private String emailid;



    public Employee( String firstname, String lastname, String emailid) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.emailid = emailid;
    }

    public Employee() {
        super();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}


