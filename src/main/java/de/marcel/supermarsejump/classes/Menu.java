package de.marcel.supermarsejump.classes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Menu {

	static Boolean menu = true, start = false, highscore = false, steuerung = false, credits = false, gameover = false,
			gewonnen = false;
	static Boolean start_over = false, highscore_over = false, steuerung_over = false, credits_over = false;

	Image rollover[] = new Image[4];
	Image menubilder[] = new Image[6];

	public Menu() {

	}

	public void initMenuBilder() {

		// Einlesen der Menu-Bilder
		for (int i = 0; i < 6; i++) {
			BufferedImage bImage = null;
			try {
				File file = new File("src/main/resources/menubilder" + (i + 1) + ".png");
				System.out.println(file.canRead());
				System.out.println(file.getAbsolutePath());
				bImage = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menubilder[i] = bImage;
		}
	}

	public void initRollover() {

		// Einlesen der Rollover-Bilder
		for (int i = 0; i < 4; i++) {
			BufferedImage bImage = null;
			try {
				bImage = ImageIO.read(new File("src/main/resources/rollover" + (i + 1) + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rollover[i] = bImage;
		}
	}

	public void pressedESC() {

		menu = true;
		start = false;
		highscore = false;
		steuerung = false;
		credits = false;

		Main.neustart = true;
		Main.aktiv = true;
	}

	public void testeMenuPunktgeklickt(int mX, int mY) {

		// Man kann nur auf Menupunkte klicken wenn das Spiel nicht aktiv ist
		if (start == false) {

			// Spiel starten angeklickt
			if (mX >= 267 && mX <= 650 && mY >= 175 && mY <= 232) {

				pressedStart();
			}

			// Highscore angeklickt
			if (mX >= 267 && mX <= 650 && mY >= 251 && mY <= 308) {

				pressedHighscore();
			}

			// Steuerung angeklickt
			if (mX >= 267 && mX <= 650 && mY >= 332 && mY <= 389) {

				pressedSteuerung();
			}

			// Credits angeklickt
			if (mX >= 267 && mX <= 650 && mY >= 410 && mY <= 467) {

				pressedCredits();
			}
		}
	}

	public void pressedStart() {

		menu = false;
		start = true;
	}

	public void pressedHighscore() {

		menu = false;
		highscore = true;
	}

	public void pressedSteuerung() {

		menu = false;
		steuerung = true;
	}

	public void pressedCredits() {

		menu = false;
		credits = true;
	}

	public void testeRollOver(int mX, int mY) {

		// Start:hover
		if (mX >= 267 && mX <= 650 && mY >= 175 && mY <= 232) {

			start_over = true;
		} else {

			start_over = false;
		}

		// Highscore:hover
		if (mX >= 267 && mX <= 650 && mY >= 251 && mY <= 308) {

			highscore_over = true;
		} else {

			highscore_over = false;
		}

		// Steuerung:hover
		if (mX >= 267 && mX <= 650 && mY >= 332 && mY <= 389) {

			steuerung_over = true;
		} else {

			steuerung_over = false;
		}

		// Credits:hover
		if (mX >= 267 && mX <= 650 && mY >= 410 && mY <= 467) {

			credits_over = true;
		} else {

			credits_over = false;
		}
	}

	public void paintMenu(Graphics g, int id) {

		int appSizeX = 900, appSizeY = 600;

		g.drawImage(menubilder[id], 0, 0, appSizeX, appSizeY, null);
	}

	public void paintRollover(Graphics g, int id) {

		int appSizeX = 900, appSizeY = 600;

		g.drawImage(rollover[id], 0, 0, appSizeX, appSizeY, null);
	}
}
