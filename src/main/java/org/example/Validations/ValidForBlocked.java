package org.example.Validations;

import org.example.db.LocalDataBaseInteraction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidForBlocked implements Validatable{

    @Override
    public boolean isValid(String text) {

        //проверяем, действительно ли текст сообщения соответствует
        //маске: LIKE ('[А-Я][0-9][0-9][0-9][А-Я][А-Я][0-9][0-9]')
        //OR LIKE ('[А-Я][0-9][0-9][0-9][А-Я][А-Я][0-9][0-9][0-9]')

        if (text.length() == 8) {
            Pattern numberPlatePattern = Pattern.compile("[А-Я][0-9][0-9][0-9][А-Я][А-Я][0-9][0-9]");
            Matcher checkPattern = numberPlatePattern.matcher(text.toUpperCase());

            if (checkPattern.find()) return true;
        }

        if (text.length() == 9) {
            Pattern numberPlatePattern = Pattern.compile("[А-Я][0-9][0-9][0-9][А-Я][А-Я][0-9][0-9][0-9]");
            Matcher checkPattern = numberPlatePattern.matcher(text.toUpperCase());


            if (checkPattern.find()) return true;
        }

        return false;
    }

}
