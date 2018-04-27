package controller;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Delegat;

public class Controller {

    /**
     *  Checks if email is in the right format.
     * @param email - String that represent email.
     * @return returns int =1 if the String matches pattern,
     * =0 if it don't.
     */
    public static int parseEmail(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            return 1;
        }else{
            return 0;
        }
    }



    /**
     * Checks if email and password are in the right format.
     * @param email - String that represent email.
     * @return int =1 - if email and password are in the right format,
     * =2 if only email is in the right format,
     * =3 if only password is in the right format.
     */
    public static int parse (String email){
        int emailOk = parseEmail(email);

        return emailOk==1 ? 1 : 2;
    }

    /**
     * Based on the parameters this method can notify via class Toast if entered parameter is not in the right format,
     * if it is, then it checks in database if a Delegat with that username exists,
     * if it exist it returns object of class Delegat, if not then it returns null.
     * If entered parameter is not in the right format it notify the user via Toast and returns null.
     * @param email - String that represent email.
     * @param context - Context in which this method is called.
     * @return - Delegat or null.
     */
    public static Delegat login(String email, Context context){

        switch (parse(email)) {
            case 1:
                try {
                    Delegat delegat =  new Delegat().execute(email).get();
                    return delegat;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                Toast.makeText(context, "Neispravan format email-a!", Toast.LENGTH_LONG).show();
                return null;
        }
        return null;
    }

}
