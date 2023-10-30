package com.izi.er.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table
public class User implements Cloneable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, length=10, unique=true)
    private String username;

    @Column(nullable=false, length=100, unique=false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable=false, length=10)
    private RoleType role;

    @Column(nullable=false)
    @CreationTimestamp
    private Timestamp createDate;

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Getter
    public static enum RoleType {
        ADMIN((byte)0),
        ORDINARY((byte)1),
        AMBULANCE((byte)2),
        HOSPITAL((byte)3);

        private final byte value;
        private RoleType(byte value) {
            this.value = value;
        }
    }
}
