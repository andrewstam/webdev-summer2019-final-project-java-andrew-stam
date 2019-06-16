// Based on code by Jose Annunziato on GitHub at:
// https://github.com/jannunzi/wbdv-su1-19-server-java/blob/
//      7ae618aca3918f1c968ed30b111a4b189b24499e/src/main/java/com/example/
//      wbdvsu119serverjava/repositories/UserRepository.java

package com.example.myapp.repositories;

import java.util.List;

import com.example.myapp.models.Movie;
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
    @Query(value = "select user.following from User user where id=:id")
    public User[] findUserFollowing(@Param("id") Long id);

    // Use JPA to find a user's followers by their id, return that list
    @Query(value = "select user.followers from User user where id=:id")
    public User[] findUserFollowers(@Param("id") Long id);

    // Use JPA to find a user's favorites by their id, return that list
    @Query(value = "select user.favorites from User user where id=:id")
    public Movie[] findUserFavorites(@Param("id") Long id);
}