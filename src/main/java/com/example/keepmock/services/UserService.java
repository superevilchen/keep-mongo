package com.example.keepmock.services;

import com.example.keepmock.beans.User;
import com.example.keepmock.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void addUser(User user) throws CustomException;
    void updateUser(String userID, User user) throws CustomException;
    void deleteUser(String userID) throws CustomException;
    User getUserDetails(String userID) throws CustomException;

}
