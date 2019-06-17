// Based on code by Jose Annunziato on GitHub at:
// https://github.com/jannunzi/wbdv-su1-19-server-java/blob/
//      7ae618aca3918f1c968ed30b111a4b189b24499e/src/main/java/com/example/
//      wbdvsu119serverjava/repositories/UserRepository.java

package com.example.myapp.repositories;

import java.util.List;

import com.example.myapp.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    // Use JPA to find a user by their username, return that User
    @Query(value = "select * from user where username=:username", nativeQuery = true)
    public User findUserByUsername(@Param("username") String uname);

    // Use JPA to find a user by their credentials, return that found User
    @Query(value = "select user from User user where user.username=:username and user.password=:password")
    public User findUserByCredentials(@Param("username") String username, @Param("password") String password);

    // Use JPA to find all users
    @Query(value = "select * from user", nativeQuery = true)
    public List<User> findAllUsers();

    // Use JPA to find a user by their id, return that user
    @Query(value = "select * from user where id=:id", nativeQuery = true)
    public User findUserById(@Param("id") Long id);

    // Use JPA to find a user's following by their id, return that list
    @Query(value = "select * from user_following where user_id=:id", nativeQuery = true)
    public List<User> findUserFollowing(@Param("id") Long id);

    // Use JPA to find a user's followers by their id, return that list
    @Query(value = "select * from user_followers where user_id=:id", nativeQuery = true)
    public List<User> findUserFollowers(@Param("id") Long id);

    // Use JPA to find a user's favorites by their id, return that list
    @Query(value = "select * from user_favorites where user_id=:id", nativeQuery = true)
    public List<String> findUserFavorites(@Param("id") Long id);

    // Use JPA to add to a user's following by their id, the user to follow's id, and the array index
    @Query(value = "insert into user_following (user_id, following_id, following_idx) values (:uid, :fid, :idx)", nativeQuery = true)
    public void addUserFollowing(@Param("uid") Long uid, @Param("fid") Long fid, @Param("idx") int idx);

    // Use JPA to add to a user's followers by their id, the user to be following's id, and the array index
    @Query(value = "insert into user_followers (user_id, follower_id, follower_idx) values (:uid, :fid, :idx)", nativeQuery = true)
    public void addUserFollower(@Param("uid") Long uid, @Param("fid") Long fid, @Param("idx") int idx);

    // Use JPA to add to a user's favorites by their id, the favorite ID, and the array index
    @Query(value = "insert into user_favorites (user_id, favorite_id, favorite_idx) values (:uid, :fid, :idx)", nativeQuery = true)
    public void addUserFavorite(@Param("uid") Long uid, @Param("fid") String fid, @Param("idx") int idx);
}