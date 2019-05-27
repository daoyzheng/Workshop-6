package Controllers;

import java.awt.*;

public class Validator {

    //is text
    public static boolean isTfText(javafx.scene.control.TextField tf) {

        Boolean result = false;

        if (tf.getText() != "") {
            //return error msg
            result = true;
        }

        return result;
    }

    //is email
    public boolean isTfEmail(TextField tf) {
        Boolean result = false;

        String strInput = tf.getText();

        if (strInput.matches(".@.\\..")){
            //return error msg
            result = true;
        }
        return result;
    }


    //is phone number


    //is postal code


    //is positive int


    //is positive double

    //
}
