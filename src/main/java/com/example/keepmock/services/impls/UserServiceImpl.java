package com.example.keepmock.services.impls;

import com.example.keepmock.beans.Task;
import com.example.keepmock.beans.User;
import com.example.keepmock.exceptions.CustomException;
import com.example.keepmock.repos.UserRepository;
import com.example.keepmock.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.keepmock.exceptions.ExceptionState.ALREADY_EXISTS;
import static com.example.keepmock.exceptions.ExceptionState.NOT_FOUND;
import static com.example.keepmock.utils.ValidationUtils.validate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user) throws CustomException {

        if (user.getId() != null){
            validate(!userRepository.existsById(user.getId()), () -> new CustomException(ALREADY_EXISTS));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    @Transactional(value = "MONGO_TRANSACTION_MANAGER", propagation = Propagation.REQUIRED)
    public void updateUser(String userID, User user) throws CustomException {

            User fromDB = userRepository.findById(userID).orElseThrow(() -> new CustomException(NOT_FOUND));

            fromDB.setPassword(user.getPassword());
    }

    @Override
    public void deleteUser(String userID) throws CustomException {

        validate(userRepository.existsById(userID), () -> new CustomException(NOT_FOUND));

        userRepository.deleteById(userID);
    }

    @Override
    public User getUserDetails(String userID) throws CustomException {

        return userRepository.findById(userID).orElseThrow(() -> new CustomException(NOT_FOUND));
    }
}
