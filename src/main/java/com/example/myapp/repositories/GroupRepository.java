// Created by Andrew Stam
package com.example.myapp.repositories;

import java.util.List;

import com.example.myapp.models.Comment;
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
    @Query(value = "select user_id from user_groups where groups_id=:id", nativeQuery = true)
    public List<Long> findGroupMemberIds(@Param("id") Long id);

    // Use JPA to find the group's WatchItems
    @Query(value = "select * from watchitem where group_id=:id", nativeQuery = true)
    public List<WatchItem> findGroupWatchItems(@Param("id") Long id);

    // Use JPA to find the group's Movie Ids
    @Query(value = "select movie_id from watchitem where group_id=:id", nativeQuery = true)
    public List<String> findGroupMovieIds(@Param("id") Long id);

    // Use JPA to find the group's dates
    @Query(value = "select watch_date from watchitem where group_id=:id", nativeQuery = true)
    public List<String> findGroupDates(@Param("id") Long id);

    // Use JPA to find a groups's users by their id, return that list
    @Query(value = "select user_id from user_groups where groups_id=:id", nativeQuery = true)
    public List<Long> findGroupUsers(@Param("id") Long id);

    // Use JPA to add a user to a groups by their id
    @Modifying
    @Transactional
    @Query(value = "insert into user_groups (user_id, groups_id) values (:uid, :gid)", nativeQuery = true)
    public void addGroupUser(@Param("uid") Long uid, @Param("gid") Long gid);

    // Use JPA to remove a user from a groups by their id
    @Modifying
    @Transactional
    @Query(value = "delete from user_groups where user_id=:uid and groups_id=:gid", nativeQuery = true)
    public void deleteGroupUser(@Param("uid") Long uid, @Param("gid") Long gid);

    // Use JPA to find a groups's watch item's attending members by its id, return that list
    @Query(value = "select attending_member_ids from watch_item_attending_member_ids where watch_item_id=:wid", nativeQuery = true)
    public List<Long> findAttendingMembers(@Param("wid") Long wid);

    // Use JPA to find a groups's watch item's comments by its id, return that list
    @Query(value = "select * from comment where watch_item_id=:wid", nativeQuery = true)
    public List<Comment> findItemComments(@Param("wid") Long wid);

    // Use JPA to add a comment to groups's watch item
    @Modifying
    @Transactional
    @Query(value = "insert into comment (text, user_id, watch_item_id) values (:txt, :uid, :wid)", nativeQuery = true)
    public void addItemComment(@Param("txt") String text, @Param("uid") Long uid, @Param("wid") Long wid);

    // Use JPA to delete a comment of an item by their ids
    @Modifying
    @Transactional
    @Query(value = "delete from comment where user_id=:uid and watch_item_id=:wid", nativeQuery = true)
    public void deleteItemComment(@Param("uid") Long uid, @Param("wid") Long wid);

    // Use JPA to find a groups's watch item's comment text by its id, return that list
    @Query(value = "select text from comment where watch_item_id=:wid", nativeQuery = true)
    public List<String> findItemText(@Param("wid") Long wid);

    // Use JPA to find a groups's watch item's users who wrote comments by its id, return that list
    @Query(value = "select user_id from comment where watch_item_id=:wid", nativeQuery = true)
    public List<Long> findItemUser(@Param("wid") Long wid);

    /*
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
