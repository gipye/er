package com.izi.er.user.hospital;

import com.izi.er.user.User;
import com.izi.er.user.hospital.department.HospitalDepartment;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table
public class HospitalUser implements Cloneable {
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

    @Column(nullable=false)
    private int numberOfBed;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="hospital")
    private List<HospitalDepartment> departments;

    @Override
    public HospitalUser clone() {
        try {
            HospitalUser clone = (HospitalUser) super.clone();
            clone.user = user.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
