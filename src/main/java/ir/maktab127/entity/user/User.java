package ir.maktab127.entity.user;

import ir.maktab127.entity.base.BaseEntity;
import ir.maktab127.entity.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = User.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="user_type"  ,discriminatorType = DiscriminatorType.STRING )
@NoArgsConstructor
public class User extends BaseEntity<Long> {
    public static final String TABLE_NAME="users";
    public static final String FIRST_NAME="first_name";
    public static final String LAST_NAME="last_name";
    public static final String USER_NAME="user_name";
    public static final String PASSWORD="password";
    public static final String USER_STATUS="user_status";


    @Column(name = FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = LAST_NAME, nullable = false)
    private String lastName;
    @Column(name = USER_NAME, nullable = false,unique = true)
    private String userName;
    @Column(name = PASSWORD,nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = USER_STATUS,nullable = false)
    private UserStatus status =UserStatus.PENDING;


    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.password = password;
    }

}
