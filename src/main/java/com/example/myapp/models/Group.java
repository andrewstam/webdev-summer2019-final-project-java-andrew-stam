// Created by Andrew Stam
package com.example.myapp.models;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    private Long leaderId;

    private List<Long> memberIds;

    private List<WatchItem> watchItems;
}
