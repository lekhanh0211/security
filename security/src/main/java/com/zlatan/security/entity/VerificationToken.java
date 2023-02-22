package com.zlatan.security.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expireDate;

    //thực hiện lk ánh xạ 1 -1
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFYTOKEN"))
    private User user;
    public VerificationToken(User user,String token){
        super();
        this.token = token;
        this.user = user;
        this.expireDate = calculateExpirationDate(EXPIRATION_TIME);
    }
    public VerificationToken(String token){
        super();
        this.token = token;
        this.expireDate = calculateExpirationDate(EXPIRATION_TIME);
    }
    private Date calculateExpirationDate(int expireDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expireDate);
        return new Date(calendar.getTime().getTime());
    }
}
