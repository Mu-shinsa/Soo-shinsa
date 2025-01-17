package com.Soo_Shinsa.utils;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtils {

    public static User getUser(UserDetails userDetails) {
        return ((UserDetailsImp) userDetails).getUser();
    }
}
