// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.Comment;
import com.example.myapp.models.Group;
import com.example.myapp.models.WatchItem;
import com.example.myapp.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GroupController {

    // Get data from the database
    @Autowired
    private GroupRepository repository;

    // Find all users stored
    @GetMapping("/api/groups")
    public List<Group> findAllGroups() {
        return repository.findAllGroups();
    }

    // Find group by ID
    @GetMapping("/api/groups/{gid}")
    public Group findGroupById(@PathVariable("gid") Long id) {
        return repository.findGroupById(id);
    }

    // Find a groups's leader ID by the groups's ID
    @GetMapping("/api/groups/{gid}/leader")
    public Long findGroupLeaderId(@PathVariable("gid") Long id) {
        return repository.findGroupLeaderId(id);
    }

    // Create a group for the given leader ID
    @PutMapping("/api/groups")
    public void createGroup(@RequestBody Long uid) {
        repository.createGroup(uid);
    }

    // Delete a group by its id
    @DeleteMapping("/api/groups")
    public void deleteGroup(@RequestBody Long gid) {
        repository.deleteGroup(gid);
    }

    // Find all group member IDs for the group with the given ID
    @GetMapping("/api/groups/{gid}/members")
    public List<Long> findGroupMemberIds(@PathVariable("gid") Long groupId) {
        return repository.findGroupMemberIds(groupId);
    }

    // Add a user to the given group
    @PutMapping("/api/groups/{gid}")
    public void addUser(@PathVariable("gid") Long gid, @RequestBody Long uid) {
        repository.addUser(gid, uid);
    }

    // Remove a user from a group
    @DeleteMapping("/api/groups/{gid}")
    public void deleteUser(@PathVariable("gid") Long gid, @RequestBody Long uid) {
        repository.deleteUser(gid, uid);
    }

    // Find the group's WatchItems
    @GetMapping("/api/groups/{gid}/watch")
    public List<WatchItem> findGroupWatchItems(@PathVariable("gid") Long id) {
        return repository.findGroupWatchItems(id);
    }

    // Add a watch item
    @PostMapping("/api/groups/{gid}/watch/{mid}")
    public void addWatchItem(@PathVariable("gid") Long gid, @PathVariable("mid") String mid, @RequestBody String date) {
        repository.addWatchItem(gid, mid, date);
    }

    // Remove a watch item
    @DeleteMapping("/api/groups/{gid}/watch/{mid}")
    public void deleteWatchItem(@PathVariable("gid") Long gid, @PathVariable("mid") String mid, @RequestBody String date) {
        repository.deleteWatchItem(gid, mid, date);
    }

    // Update a watch item's date
    @PostMapping("/api/groups/{gid}/watch/{mid}/date")
    public void updateWatchItemDate(@PathVariable("gid") Long gid, @PathVariable("mid") String mid, @RequestBody String date) {
        repository.updateWatchItemDate(gid, mid, date);
    }

    // Find the group's Movie Ids
    @GetMapping("/api/groups/{gid}/movies")
    public List<String> findGroupMovieIds(@PathVariable("gid") Long id) {
        return repository.findGroupMovieIds(id);
    }

    // Find the group's dates
    @GetMapping("/api/groups/{gid}/dates")
    public List<String> findGroupDates(@PathVariable("gid") Long id) {
        return repository.findGroupDates(id);
    }

    // Find a groups's watch item's attending members by its id, return that list
    @GetMapping("/api/groups/{wid}/members")
    public List<Long> findAttendingMembers(@PathVariable("wid") Long wid) {
        return repository.findAttendingMembers(wid);
    }

    // Find a groups's watch item's comments by its id, return that list
    @GetMapping("/api/groups/{wid}/comments")
    public List<Comment> findItemComments(@PathVariable("wid") Long wid) {
        return repository.findItemComments(wid);
    }

    // Add a comment to groups's watch item
    @PutMapping("/api/groups/{wid}/comment/{uid}")
    public void addItemComment(@RequestBody String text, @PathVariable("uid") Long uid, @PathVariable("wid") Long wid) {
        repository.addItemComment(text, uid, wid);
    }

    // Delete a comment of an item by their ids
    @DeleteMapping("/api/groups/{wid}/comment/{uid}")
    public void deleteItemComment(@PathVariable("uid") Long uid, @PathVariable("wid") Long wid) {
        repository.deleteItemComment(uid, wid);
    }

    // Update a comment of an item by their ids
    @PostMapping("/api/groups/{wid}/comment/{uid}")
    public void updateItemComment(@RequestBody String text, @PathVariable("uid") Long uid, @PathVariable("wid") Long wid) {
        repository.updateItemComment(text, uid, wid);
    }

    // Find a groups's watch item's comment text by its id, return that list
    @GetMapping("/api/groups/{wid}/text")
    public List<String> findItemText(@PathVariable("wid") Long wid) {
        return repository.findItemText(wid);
    }

    // Find a groups's watch item's users who wrote comments by its id, return that list
    @GetMapping("/api/groups/{wid}/users")
    public List<Long> findItemUser(@PathVariable("wid") Long wid) {
        return repository.findItemUser(wid);
    }
}
