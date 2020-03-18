package de.marcel.supermarsejump.classes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character {

	Image wAnimationR[] = new Image[4];
	Image wAnimationL[] = new Image[4];

	static Boolean sprung = false, fallen = false, links = false;

	static int my_pos = 400; // Variable f�r die Y-Pos. von Mario
	int mx_pos = 900 / 3; // Variable f�r die X-Pos. von Mario

	int bodenh = 400;
	static int sprungmax = 150;
	static int sprungh = my_pos - sprungmax;

	private int speed = 0;
	private int animation = 0;
	private int zaehler = 0;

	public void initAnimationen() {

		// Animation nach rechts wird geladen
		for (int i = 0; i < 3; i++) {
			BufferedImage bImage1 = null;
			try {
				bImage1 = ImageIO.read(new File("src/main/resources/mario" + (i + 1) + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wAnimationR[i] = bImage1;
		}

		// Animation nach links wird geladen
		for (int m = 0; m < 3; m++) {
			BufferedImage bImage2 = null;
			try {
				bImage2 = ImageIO.read(new File("src/main/resources/mario" + (m + 4) + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wAnimationL[m] = bImage2;
		}
	}

	public void starteAnimation() {

		zaehler++;

		if (zaehler >= 10) {
			zaehler = 0;
			animation++;

			if (animation >= 3) {
				animation = 0;
			}
		}
	}

	public void setzeSpeed(int s) {
		speed = s;
	}

	public void berechneGeschwindigkeit() {

		MapLoader.lx_pos = MapLoader.lx_pos + speed;
	}

	public void testeBewegung() {

		// pr�fe Postion und ob Sprung oder Fallen aktiv
		testeSpringen();
		bestimmePosition();
		testeFallen();
	}

	private void testeSpringen() {

		if (sprung == true) {

			my_pos -= 2;

			if (my_pos < sprungh) {

				fallen = true;
				sprung = false;
			}

		}
	}

	private void testeFallen() {

		if (fallen == true) {

			my_pos += 7;

			if (my_pos > 630) {

				pruefeSpielende();

				fallen = false;

			}
		}
	}

	private void bestimmePosition() {

		int mapLayout[][] = null;
		mapLayout = Main.derMapLoader.gibmapLayout();

		int lx = MapLoader.lx_pos + 320; // X-Pos Mario f�r Abfrage
		int ly = my_pos + 60; // Y-Pos Mario f�r Abfrage

		if (sprung == false) {

			if (ly / 50 + 1 < 12) {

				// Abfrage ob kein Block da ist
				if (mapLayout[lx / 50][ly / 50 + 1] == 0) {
					fallen = true;

					// Abfrage ob Spikes unter dir
				} else if (mapLayout[lx / 50][ly / 50 + 1] == 2) {

					pruefeSpielende();

					// Ein Block ist unter der Figur
				} else {

					fallen = false;
				}
			}

			else {
				fallen = true;
			}
		}
	}

	private void pruefeSpielende() {

		Boolean keineLeben = Main.dieLeben.pruefeLeben();

		if (keineLeben == true) {

			Menu.gameover = true;
		} else {

			Main.dieLeben.reduziereLeben();
			my_pos = 393;
			MapLoader.lx_pos = 0;
			Main.derTimer.starteTimer();
		}

	}

	public void paintAnimation(Graphics g) {

		// Animation f�r "nach links laufen"
		if (links == true)
			g.drawImage(wAnimationL[animation], mx_pos, my_pos, null);

		// Animation f�r "nach rechts laufen"
		else
			g.drawImage(wAnimationR[animation], mx_pos, my_pos, null);

	}
}
