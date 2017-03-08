package de.marcel.supermarsejump.classes;

public class Abfragen {

    public Abfragen() {

    }

    public void Bordererreicht() {

        erreichtAnfang();
        erreichtEnde();
    }

    private void erreichtAnfang() {

        int lx = MapLoader.lx_pos / 50;

        if (lx < 0) {

            MapLoader.lx_pos = 0;
        }

    }

    private void erreichtEnde() {

        int lx = MapLoader.lx_pos / 50;

        if (lx >= 95) {

            Main.derMapLoader.erhoeheMapNr();
        }
    }

    public void Neustart() {

        Main.derTimer.starteTimer();
        Main.dieLeben.setzeLebenaufMax();
        Main.derHighscore.setzeScore();
        Main.derMapLoader.setzeLevel();

        MapLoader.lx_pos = 0;
        Character.my_pos = 393;

        Character.links = false;
        Character.fallen = false;
        Character.sprung = false;

        Main.neustart = false;
        Menu.gameover = false;
        Menu.gewonnen = false;

    }
}
