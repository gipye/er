package com.izi.er.user.hospital.department;

import com.izi.er.user.hospital.HospitalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HospitalDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private HospitalUser hospital;
    @Column(nullable=false, length=10)
    private String name;
    @Column(nullable=false)
    private boolean isRunning;
}
