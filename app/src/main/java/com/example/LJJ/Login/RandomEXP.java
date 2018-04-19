package com.example.LJJ.Login;

import java.util.Random;

/**
 * Created by Isuk on 2018/3/31.
 */

public class RandomEXP {
    static String nextchar() {
        String result = "";
        for (int i = 0; i < 3; i++) {
            Random r = new Random();
            String unicode = (Math.abs(r.nextInt()%20000)+(14 << 8) + (4 << 12)) + "";
            System.out.println(unicode);
            char c = (char) Integer.parseInt(unicode, 10);
            result += c;
        }
        return result;

    }
}
