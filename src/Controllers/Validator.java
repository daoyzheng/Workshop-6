package Controllers;

import java.awt.*;
import java.util.regex.Pattern;

public class Validator {

    //is text
    public static boolean isTfText(javafx.scene.control.TextField tf) {

       Boolean result = true;
       if (tf.getText().equals("")) {
            //return error msg
            result = false;
        }


        return result;
    }

    public static boolean isTfEmail(javafx.scene.control.TextField tf) {

        String strInput = tf.getText().toString();

        Boolean result = Pattern.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", strInput);
        System.out.println(result);
        return result;
    }


    //is phone number
    public static boolean isTfPhoneNumber(javafx.scene.control.TextField tf) {

        String strInput = tf.getText().toString();

        Boolean result = Pattern.matches("\\([0-9]{3}\\)\\ [0-9]{3}\\-[0-9]{4}", strInput);
        System.out.println(result);
        return result;
    }

    //is postal code


    //is positive int


    //is positive double

    //
}
