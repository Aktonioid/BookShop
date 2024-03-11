package com.bookshop.bookshop.infrastucture.services;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.bookshop.bookshop.infrastucture.repository.sql.UserRepo;

public class UserServiceTest {

    @Mock
    UserRepo repo;
    @InjectMocks
    UserService service;

    @BeforeEach
    void setUp()
    {
        repo =  mock(UserRepo.class);
        service = new UserService();
        
    }

    @Test
    void testCreateUser() {

    }

    @Test
    // если в createUser посылается модель с указанным UUID
    void testCreateUserHasUUID_false()
    {

    }
    @Test
    void testDeleteUserById() {

    }

    @Test
    void testGenerateNewPassword() {

    }

    @Test
    void testGetUserByEmail() {

    }

    @Test
    void testGetUserById() {

    }

    @Test
    void testGetUserByUserName() {

    }

    @Test
    void testGetUsersByPage() {

    }

    @Test
    void testUpdateUser() {

    }
}
