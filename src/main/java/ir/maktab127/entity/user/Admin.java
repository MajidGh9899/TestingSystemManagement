package ir.maktab127.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter

public class Admin extends User {

      public Admin() {
        super();
    }

    public Admin(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);

    }



}