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

    // Use JPA to create a group
    @Modifying
    @Transactional
    @Query(value = "insert into movie_group (leader_id) values (:uid)", nativeQuery = true)
    public void createGroup(@Param("uid") Long uid);

    // Use JPA to remove a group
    @Modifying
    @Transactional
    @Query(value = "delete from movie_group where group_id=:gid", nativeQuery = true)
    public void deleteGroup(@Param("gid") Long gid);

    // Use JPA to find the group's members' IDs
    @Query(value = "select user_id from user_groups where groups_id=:id", nativeQuery = true)
    public List<Long> findGroupMemberIds(@Param("id") Long id);

    // Use JPA to add a user to a group
    @Modifying
    @Transactional
    @Query(value = "insert into user_groups (group_id, user_id) values (:gid, :uid)", nativeQuery = true)
    public void addUser(@Param("gid") Long gid, @Param("uid") Long uid);

    // Use JPA to remove a user from a group
    @Modifying
    @Transactional
    @Query(value = "delete from user_groups where group_id=:gid and user_id=:uid", nativeQuery = true)
    public void deleteUser(@Param("gid") Long gid, @Param("uid") Long uid);

    // Use JPA to find the group's WatchItems
    @Query(value = "select * from watchitem where group_id=:id", nativeQuery = true)
    public List<WatchItem> findGroupWatchItems(@Param("id") Long id);

    // Use JPA to add a watch item
    @Modifying
    @Transactional
    @Query(value = "insert into watchitem (group_id, movie_id, watch_date) values (:gid, :mid, :dte)", nativeQuery = true)
    public void addWatchItem(@Param("gid") Long gid, @Param("mid") String mid, @Param("dte") String date);

    // Use JPA to remove a watch item
    @Modifying
    @Transactional
    @Query(value = "delete from watchitem where group_id=:gid and movie_id=:mid and watch_date=:dte", nativeQuery = true)
    public void deleteWatchItem(@Param("gid") Long gid, @Param("mid") String mid, @Param("dte") String date);

    // Use JPA to update a watch item's date
    @Modifying
    @Transactional
    @Query(value = "update watchitem set watch_date=:dte where group_id=:gid and movie_id=:mid", nativeQuery = true)
    public void updateWatchItemDate(@Param("gid") Long gid, @Param("mid") String mid, @Param("dte") String date);

    // Use JPA to find the group's Movie Ids
    @Query(value = "select movie_id from watchitem where group_id=:id", nativeQuery = true)
    public List<String> findGroupMovieIds(@Param("id") Long id);

    // Use JPA to find the group's dates
    @Query(value = "select watch_date from watchitem where group_id=:id", nativeQuery = true)
    public List<String> findGroupDates(@Param("id") Long id);

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

    // Use JPA to update a comment of an item by their ids
    @Modifying
    @Transactional
    @Query(value = "update comment set text=:txt where user_id=:uid and watch_item_id=:wid", nativeQuery = true)
    public void updateItemComment(@Param("txt") String text, @Param("uid") Long uid, @Param("wid") Long wid);

    // Use JPA to find a groups's watch item's comment text by its id, return that list
    @Query(value = "select text from comment where watch_item_id=:wid", nativeQuery = true)
    public List<String> findItemText(@Param("wid") Long wid);

    // Use JPA to find a groups's watch item's users who wrote comments by its id, return that list
    @Query(value = "select user_id from comment where watch_item_id=:wid", nativeQuery = true)
    public List<Long> findItemUser(@Param("wid") Long wid);
}
