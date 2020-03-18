package de.marcel.supermarsejump.classes;

import java.awt.Graphics;

public class Timer {

    int counter;

    public Timer() {

    }

    public void starteTimer() {

        counter = 3000;
    }

    public void reduziereTimer() {

        counter--;

        Timerabgelaufen();
    }

    public void Timerabgelaufen() {

        if (counter <= 0) {

            if (Menu.gewonnen == false) {

                Boolean keineLeben = Main.dieLeben.pruefeLeben();

                if (keineLeben == true) {

                    counter = 0;
                    Menu.gameover = true;

                } else {

                    Main.dieLeben.reduziereLeben();
                    MapLoader.lx_pos = 0;
                    starteTimer();
                }
            } else {

                counter = 0;
            }
        }

    }

    public int gibTimer() {

        return counter;
    }

    public void paintTimer(Graphics g) {

        g.drawString(String.valueOf("Time: " + counter), 770, 50);
    }
}
