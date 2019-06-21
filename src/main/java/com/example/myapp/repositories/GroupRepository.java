// Created by Andrew Stam
package com.example.myapp.repositories;

import java.util.List;

import com.example.myapp.models.Group;
import com.example.myapp.models.WatchItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends CrudRepository<Group, Long> {
    // Use JPA to find all groups
    @Query(value = "select * from movie_group", nativeQuery = true)
    public List<Group> findAllGroups();

    // Use JPA to find a group by its id, return that group
    @Query(value = "select * from movie_group where id=:id", nativeQuery = true)
    public Group findGroupById(@Param("id") Long id);

    // Use JPA to find the group's leader's ID
    @Query(value = "select leader_id from movie_group where id=:id", nativeQuery = true)
    public Long findGroupLeaderId(@Param("id") Long id);

    // Use JPA to find the group's members' IDs
    @Query(value = "select member_ids from group_member_ids where group_id=:id", nativeQuery = true)
    public List<Long> findGroupMemberIds(@Param("id") Long id);

    // Use JPA to find the group's WatchItems
    @Query(value = "select * from group_watch_items where group_id=:id", nativeQuery = true)
    public List<WatchItem> findGroupWatchItems(@Param("id") Long id);

    /*// Use JPA to find all groups of a movie by its id, return those groups (stars only)
    @Query(value = "select stars from group where movie_id=:id", nativeQuery = true)
    public List<Integer> findStarGroupsByMovieId(@Param("id") String id);

    // Use JPA to find all groups of a user by their id, return those groups (text only)
    @Query(value = "select group_text from group where user_id=:id", nativeQuery = true)
    public List<String> findWrittenGroupsByUserId(@Param("id") Long id);

    // Use JPA to find all groups of a user by their id, return those groups (stars only)
    @Query(value = "select stars from group where user_id=:id", nativeQuery = true)
    public List<Integer> findStarGroupsByUserId(@Param("id") Long id);

    // Use JPA to find all groups of a user by their id, return those groups (ids only)
    @Query(value = "select id from group where user_id=:id", nativeQuery = true)
    public List<Long> findGroupIdsByUserId(@Param("id") Long id);

    // Use JPA to find the group of a movie by a user by their ids, return that group (text only)
    @Query(value = "select group_text from group where user_id=:uid and movie_id=:mid", nativeQuery = true)
    public String findWrittenGroupForMovieByUserId(@Param("uid") Long uid, @Param("mid") String mid);

    // Use JPA to find the group of a movie by a user by their ids, return that group (text only)
    @Query(value = "select stars from group where user_id=:uid and movie_id=:mid", nativeQuery = true)
    public Integer findStarsForMovieByUserId(@Param("uid") Long uid, @Param("mid") String mid);

    // Use JPA to find the list of movies a user has written groups for
    @Query(value = "select movie_id from group where user_id=:uid", nativeQuery = true)
    public List<String> findUserMovieGroups(@Param("uid") Long uid);

    // Use JPA to add a group of a movie by a user by their ids, storing the text and star rating
    @Modifying
    @Transactional
    @Query(value = "insert into group (user_id, movie_id, group_text, stars) values (:uid, :mid, :txt, :stz)", nativeQuery = true)
    public void createGroup(@Param("uid") Long uid, @Param("mid") String mid, @Param("txt") String text, @Param("stz") Integer stars);

    // Use JPA to update a star rating of a movie by a user by their ids
    @Modifying
    @Transactional
    @Query(value = "update group set stars=:stz where user_id=:uid and movie_id=:mid", nativeQuery = true)
    public void updateStars(@Param("uid") Long uid, @Param("mid") String mid, @Param("stz") Integer stars);

    // Use JPA to update group text of a movie by a user by their ids
    @Modifying
    @Transactional
    @Query(value = "update group set group_text=:txt where user_id=:uid and movie_id=:mid", nativeQuery = true)
    public void updateText(@Param("uid") Long uid, @Param("mid") String mid, @Param("txt") String text);

    // Use JPA to delete a group of a movie by a user by their ids
    @Modifying
    @Transactional
    @Query(value = "delete from group where user_id=:uid and mid=:mid", nativeQuery = true)
    public void deleteGroup(@Param("uid") Long uid, @Param("mid") String mid);*/
}
