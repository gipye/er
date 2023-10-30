package com.izi.er.user.ordinary;

import com.izi.er.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Builder
@Data
@ToString
@Table
public class OrdinaryUser implements Cloneable {
    @Id
    private long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private User user;

    @Column(nullable=false, length=20, unique=false)
    private String name;

    @Column(nullable=false, length=20, unique=true)
    private String telephone;

    @Column(nullable=false, length=30, unique=true)
    private String address;

    @Column(nullable=true)
    private double latitude;

    @Column(nullable=true)
    private double longitude;

    @Override
    public OrdinaryUser clone() {
        try {
            OrdinaryUser clone = (OrdinaryUser) super.clone();
            clone.user = user.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
