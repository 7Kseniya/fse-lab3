package se.ifmo.lab4.utils;

public class AreaCheck {


    public static boolean check(float x, float y, float R){
        return (x <= 0 && x >= -R && y >= 0 && y <= R) //square
        || (x>=0 && y<=0 && Math.sqrt(x*x+y*y)<=R/2) //circle
        || (x >= -R && x <= 0 && y >= -R/2.0 && y <= 0 && y >= (-x/2.0 - R/2.0)); //triangle
    }

}
