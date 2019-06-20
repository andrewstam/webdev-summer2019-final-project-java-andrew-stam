// Created by Andrew Stam
package com.example.myapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinTable(name = "user",
            joinColumns={@JoinColumn(name="leader_id", referencedColumnName="id")}
    )
    private Long leaderId;

    @JsonIgnore
    /*@JoinTable(name = "user",
            joinColumns={@JoinColumn(name="member_ids", referencedColumnName="id")}
    )*/
    @ElementCollection
    private List<Long> memberIds;

    @JsonIgnore
    @JoinTable(name = "watchitem",
            joinColumns={@JoinColumn(name="watch_items", referencedColumnName="id")}
    )
    @OneToMany
    private List<WatchItem> watchItems;

    public Group(Long id, Long leaderId, List<Long> memberIds, List<WatchItem> watchItems) {
        this.id = id;
        this.leaderId = leaderId;
        this.memberIds = memberIds;
        this.watchItems = watchItems;
    }

    public Group() {
        this.id = null;
        this.leaderId = null;
        this.memberIds = null;
        this.watchItems = null;
    }

    public Long getGroupId() {
        return id;
    }

    public void setGroupId(Long id) {
        this.id = id;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }

    public List<WatchItem> getWatchItems() {
        return watchItems;
    }

    public void setWatchItems(List<WatchItem> watchItems) {
        this.watchItems = watchItems;
    }
}
