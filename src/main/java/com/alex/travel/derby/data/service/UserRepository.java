package com.alex.travel.derby.data.service;

import com.alex.travel.derby.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface UserRepository extends JpaRepository<User, Long> {
    User getByUserName(String userName);

}
