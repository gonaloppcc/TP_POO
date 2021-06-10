package Model.Match;
//Coisas para fazer, importante
/*
Nos getter e setter meter clones, se não é apenas o endereço que vai e pode dar problemas
Meter os enum a funcionar para ele poder usar aqui os enum, caso contrário é muito chato
 */

import java.util.List;
import java.util.stream.Collectors;

public class PlayersField {
    private List<PlayerField> playersPlaying;
    private List<PlayerField> playersBench;
    private Integer[] strategy;



    private static final double radius = 5;

    // Construtores
    public PlayersField(List<PlayerField> playersPlaying, List<PlayerField> playersBench) {
        this.playersPlaying = playersPlaying;
        this.playersBench  = playersBench;
    }

    public List<PlayerField> getPlayersCloseToTheBall(Point ballPosition) {
        return this.playersPlaying.stream().
                filter(player -> player.distance(ballPosition) > radius).
                map(PlayerField::clone).
                collect(Collectors.toList());
    }

    public void setBenched(List<PlayerField> benched) {
        this.playersBench = benched;
    }

    public List<PlayerField> getPlayersPlaying() {
        return playersPlaying;
    }

    public void setPlayersPlaying(List<PlayerField> playersPlaying) {
        this.playersPlaying = playersPlaying;
    }

    public List<PlayerField> getPlayersBench() {
        return playersBench;
    }

    public void setPlayersBench(List<PlayerField> playersBench) {
        this.playersBench = playersBench;
    }

    //Função que faz um substituição
    public void replace(PlayerField in, PlayerField out) {
        if (!playersBench.contains(in) || !playersPlaying.contains(out)) return;
        // ^ Talvez retorna um valor de retorno para dizer se a substituição correu bem

        // Vai do banco para o campo
        playersBench.remove(in);
        playersPlaying.add(in);

        // Do campo vai para o banco
        playersPlaying.remove(out);
        playersBench.add(out);
    }

    public void replace(PlayerField in, PlayerField out, Position inPosition, boolean lateral) {
        this.replace(in, out);

        // Colocar uma nova mainPosition e meter a lateral
        in.setMainPosition(inPosition);
        in.setLateral(lateral);
    }

    // Function that moves players in the field
    public void movePlayers(PlayerField startP, Point pos_ball) {




    }

}
