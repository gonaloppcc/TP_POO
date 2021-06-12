package View;

import Model.Match.MatchRegister;
import Model.Match.PlayerField;
import Model.Match.PlayersField;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersFieldView {
    public static void printPlayerFields(List<PlayerField> list){
        System.out.println("Choose one player by number:\n" +
                CheckGameView.myPrint(list.stream().map(PlayerField::stringToChoose).collect(Collectors.toList()),1));

    }
}
