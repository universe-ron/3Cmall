package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Integer trackID;

    @ManyToOne
    @JoinColumn(name = "spec_id", referencedColumnName = "spec_id")
    private ProductSpec productSpec;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;
}
