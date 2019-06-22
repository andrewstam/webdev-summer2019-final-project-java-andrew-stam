// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.Group;
import com.example.myapp.models.RoleType;
import com.example.myapp.models.User;
import com.example.myapp.repositories.GroupRepository;
import com.example.myapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GroupController {

    // Get data from the database
    @Autowired
    private GroupRepository repository;

    // Get user data from the database
    @Autowired
    private UserRepository userRepository;

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
    @PutMapping("/api/groups/{uid}")
    public void createGroup(@PathVariable("uid") Long uid, @RequestBody String name) {
        // Remove double quotes
        if (name.contains("\"")) {
            // Will always be at beginning and end
            name = name.substring(1, name.length() - 1);
        }
        repository.createGroup(uid, name);
        // Add the leader to the user_groups table too, don't have group id yet so search for it
        for (Group g : repository.findAllGroups()) {
            if (g.getLeaderId().equals(uid) && g.getName().equals(name)) {
                repository.addUser(g.getId(), uid);
            }
        }
    }

    // Delete a group by its id
    @DeleteMapping("/api/groups")
    public void deleteGroup(@RequestBody Long gid) {
        repository.deleteGroup(gid);
    }

    // Change the group's name, if the group exists
    @PostMapping("/api/groups/{gid}")
    public void editGroupName(@PathVariable("gid") Long gid, @RequestBody String name) {
        // Remove double quotes
        if (name.contains("\"")) {
            // Will always be at beginning and end
            name = name.substring(1, name.length() - 1);
        }
        if (repository.findGroupById(gid) != null) {
            repository.editGroupName(gid, name);
        }
    }

    // Find all group member IDs for the group with the given ID
    @GetMapping("/api/groups/{gid}/members")
    public List<Long> findGroupMemberIds(@PathVariable("gid") Long groupId) {
        return repository.findGroupMemberIds(groupId);
    }

    // Add a user to the given group, validate that user is of Member type (unless the group's leader)
    // Returns if success
    @PostMapping("/api/groups/{gid}/add")
    public boolean addUser(@PathVariable("gid") Long gid, @RequestBody Long uid) {
        if (gid == null || uid == null) {
            return false;
        }
        Group group = repository.findGroupById(gid);
        User user = userRepository.findUserById(uid);
        // Verify group exists and user isn't already in the group
        if (group != null && user != null && !repository.findGroupMemberIds(gid).contains(uid)) {
            // Either of type member or are the group's leader
            if (user.getRole() != RoleType.GroupLeader
                || group.getLeaderId().equals(uid)) {
                repository.addUser(gid, uid);
                return true;
            }
        }
        return false;
    }

    // Remove a user from a group
    @DeleteMapping("/api/groups/{gid}")
    public void deleteUser(@PathVariable("gid") Long gid, @RequestBody Long uid) {
        repository.deleteUser(gid, uid);
    }

    // Find the group's WatchItems as Strings
    @GetMapping("/api/groups/{gid}/watch")
    public List<String> findGroupWatchItems(@PathVariable("gid") Long id) {
        return repository.findGroupWatchItems(id);
    }

    // Add a watch item
    @PutMapping("/api/groups/{gid}/watch/{mid}")
    public void addWatchItem(@PathVariable("gid") Long gid, @PathVariable("mid") String mid, @RequestBody String date) {
        // Remove double quotes
        if (date.contains("\"")) {
            // Will always be at beginning and end
            date = date.substring(1, date.length() - 1);
        }
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
    @GetMapping("/api/groups/{wid}/attending")
    public List<Long> findAttendingMembers(@PathVariable("wid") Long wid) {
        return repository.findAttendingMembers(wid);
    }

    // Add a user to a groups's watch item's attending members list
    @PutMapping("/api/groups/{wid}/attending")
    public void addAttendingMember(@PathVariable("wid") Long wid, @RequestBody Long userId) {
        repository.addAttendingMember(wid, userId);
    }

    // Remove a user from a groups's watch item's attending members list
    @DeleteMapping("/api/groups/{wid}/attending")
    public void removeAttendingMember(@PathVariable("wid") Long wid, @RequestBody Long userId) {
        repository.removeAttendingMember(wid, userId);
    }

    // Find a groups's watch item's comments by its id, return that list as strings
    @GetMapping("/api/groups/{wid}/comments")
    public List<String> findItemComments(@PathVariable("wid") Long wid) {
        return repository.findItemComments(wid);
    }

    // Add a comment to groups's watch item, return the new list
    @PutMapping("/api/groups/{wid}/comment/{uid}")
    public List<String> addItemComment(@RequestBody String text, @PathVariable("uid") Long uid, @PathVariable("wid") Long wid) {
        // Remove double quotes
        if (text.contains("\"")) {
            // Will always be at beginning and end
            text = text.substring(1, text.length() - 1);
        }
        repository.addItemComment(text, uid, wid);
        return repository.findItemComments(wid);
    }

    // Delete a comment of an item by their ids
    @DeleteMapping("/api/groups/{wid}/comment/{uid}")
    public void deleteItemComment(@PathVariable("uid") Long uid, @PathVariable("wid") Long wid, @RequestBody Long cid) {
        repository.deleteItemComment(uid, wid, cid);
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
