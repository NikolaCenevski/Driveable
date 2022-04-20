package com.wp.driveable.driveableapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reason")
public class Reason {

    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Id
    private Long id;
    private String description;
    @OneToOne
    private User user;
}