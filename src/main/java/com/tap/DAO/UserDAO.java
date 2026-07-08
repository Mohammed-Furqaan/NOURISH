package com.tap.DAO;

import java.util.List;
import com.tap.model.User;

public interface UserDAO {

    // Insert a new user
    int addUser(User user);

    // Find user by ID
    User getUserById(int userId);

    // Find user by email
    User getUserByEmail(String email);

    // Get all users
    List<User> getAllUsers();

    // Update user details
    int updateUser(User user);

    // Delete user
    int deleteUser(int userId);
    
    User loginUser(String email, String password);
    
    int getUserCount();
}