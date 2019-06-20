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
    private Long groupId;

    @JoinTable(name = "user",
            joinColumns={@JoinColumn(name="leader_id", referencedColumnName="id")}
    )
    private Long leaderId;

    @JsonIgnore
    @JoinTable(name = "user",
            joinColumns={@JoinColumn(name="members_id", referencedColumnName="id")}
    )
    @ElementCollection
    private List<Long> memberIds;

    @JsonIgnore
    @JoinTable(name = "watchitem",
            joinColumns={@JoinColumn(name="watch_items", referencedColumnName="item_id")}
    )
    @OneToMany
    private List<WatchItem> watchItems;

    public Group(Long groupId, Long leaderId, List<Long> memberIds, List<WatchItem> watchItems) {
        this.groupId = groupId;
        this.leaderId = leaderId;
        this.memberIds = memberIds;
        this.watchItems = watchItems;
    }

    public Group() {
        this.groupId = null;
        this.leaderId = null;
        this.memberIds = null;
        this.watchItems = null;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
