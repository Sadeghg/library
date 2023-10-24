package io.mars.book_store.customer.model.entity;

import io.mars.book_store.base.BaseEntity;
import io.mars.book_store.customer.model.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "national_code", name = "customer_national_unique_key")
        })
public class Customer extends BaseEntity {

    private String name;
    private String lastName;
    private LocalDate birthDate;

    @Column(name = "national_code")
    @Length(min = 10, max = 10, message = "Length of NationalCode must be exactly 10")
    private String nationalCode;

    private LocalDate membershipDate;
    private String fatherName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Range(min = 0, max = 2)
    private int checkoutCount;

    private int customerScore;

    public boolean incrementCheckout(){
        if (checkoutCount +1 > 2) return false;
        ++ checkoutCount;
        return true;
    }

    public boolean decrementCheckout(){
        if (checkoutCount -1 < 0) return false;
        -- checkoutCount;
        return true;
    }

    public void addToScore(int score){
        customerScore += score;
    }
}
