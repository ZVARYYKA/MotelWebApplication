package com.zvaryyka.motelwebapplication.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {


    public String createRandomString(int stringSize) {
        int leftLimit = 48; // цифра '0'
        int rightLimit = 122; // буква 'z'

        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringSize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


    }
}
