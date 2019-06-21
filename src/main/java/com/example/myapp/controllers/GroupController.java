// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.Group;
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
    @GetMapping("/api/groups/{gid}")
    public Long findGroupLeaderId(@PathVariable("gid") Long id) {
        return repository.findGroupLeaderId(id);
    }
}
