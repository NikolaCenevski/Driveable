package com.wp.driveable.driveableapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ElementCollection
    private List<byte[]> images;
    @ManyToOne
    private User creator;
    private LocalDate date;
    private int horsepower;
    private int mileage;
    private String manufacturingYear;
    private int price;
    private Boolean isNew;
    @ManyToMany
    private List<CarType> carTypes;
    private String color;
    @ManyToOne
    private Car car;
}
