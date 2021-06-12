package View;

import Model.Match.MatchRegister;
import Model.Match.PlayerField;
import Model.Match.PlayersField;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersFieldView {
    public static void printPlayerFields(List<PlayerField> list){
        System.out.println("Those are the games in between\n" +
                CheckGameView.myPrint(list.stream().map(PlayerField::toString).collect(Collectors.toList()),1));

    }
}
