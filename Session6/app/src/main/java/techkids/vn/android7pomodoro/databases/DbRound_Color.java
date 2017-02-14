package techkids.vn.android7pomodoro.databases;

import java.util.ArrayList;
import java.util.List;

import techkids.vn.android7pomodoro.databases.models.Round_Color;

/**
 * Created by laptopTCC on 2/13/2017.
 */

public class DbRound_Color {
    public static final DbRound_Color instance = new DbRound_Color();

    public List<Round_Color> allColor() {
        //Fake data (dummy data)
        //1: Create array list
        ArrayList<Round_Color> colors = new ArrayList<>();

        //2: Add some tassk and return
        colors.add(new Round_Color("#EF5350"));
        colors.add(new Round_Color("#8E24AA"));
        colors.add(new Round_Color("#3949AB"));
        colors.add(new Round_Color("#26C6DA"));
        colors.add(new Round_Color("#D4E157"));
        colors.add(new Round_Color("#8D8D8D"));
        colors.add(new Round_Color("#34E751"));
        colors.add(new Round_Color("#EF5350"));

        return colors;
    }
}
