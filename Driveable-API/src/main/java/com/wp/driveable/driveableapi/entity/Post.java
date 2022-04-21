package com.wp.driveable.driveableapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private List<Byte[]> images;
    @ManyToOne
    private User creator;
    private int horsepower;
    private String manufacturingYear;
    private int price;
    private Boolean isNew;
    private String carType;
    private String color;
    @ManyToOne
    private Car car;
}
