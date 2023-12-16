package org.example.Validations;

import org.example.db.LocalDataBaseInteraction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidForWBF implements Validatable {
    private static List<String> streets = new ArrayList<>();

    static {
        streets.add("kfc");
        streets.add("беговая");
        streets.add("политехническая");
        streets.add("миротворцева");
        streets.add("большая садовая");
        streets.add("2-я садовая");
    }

    private String[] dataFromText;

    private Pattern timePattern = Pattern.compile("[0-9][0-9][:][0-9][0-9]");
    private ValidForBlocked vfb = new ValidForBlocked();

    @Override
    public boolean isValid(String text) {
        this.dataFromText = text.split(", ");

        if (!streets.contains(dataFromText[0].toLowerCase()))
            return false;

        Matcher timeMatch = timePattern.matcher(dataFromText[1]);

        if (!timeMatch.find())
            return false;

        if (!vfb.isValid(dataFromText[5]))
            return false;

        return true;
    }

    private String[] getMessageData() {
        return this.dataFromText;
    }

    //действие, если isValid()
}
