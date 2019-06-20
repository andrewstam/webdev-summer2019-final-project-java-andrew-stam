// Created by Andrew Stam
package com.example.myapp.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "group")
public class WatchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String movieId;

    private Date watchDate;

    private List<Comment> comments;

    private List<Long> attendingMemberIds;
}
