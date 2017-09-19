package com.example.algamoney.api.Security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradordeSenha {

    public static void main (String [] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
        //System.out.println("" + ${AAAAA_S});
    }
}
