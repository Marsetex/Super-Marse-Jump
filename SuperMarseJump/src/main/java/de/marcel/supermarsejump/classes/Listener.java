package de.marcel.supermarsejump.classes;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Listener implements KeyListener, MouseListener, MouseMotionListener {

    int                   mouseX   = 0, mouseY = 0;
    int                   my       = Character.my_pos;

    private final boolean tasten[] = new boolean[6];

    public Listener() {

        for (int i = 0; i < tasten.length; i++) {
            tasten[i] = false;
        }
    }

    // -------------------------------------- KeyListener -----------------------------------------------------

    public void druckTaste(int taste, boolean pressed) {

        switch (taste) {

        case KeyEvent.VK_LEFT:
            tasten[0] = pressed;
            break;
        case KeyEvent.VK_RIGHT:
            tasten[1] = pressed;
            break;
        case KeyEvent.VK_UP:
            tasten[2] = pressed;
            break;
        case KeyEvent.VK_DOWN:
            tasten[3] = pressed;
            break;
        case KeyEvent.VK_SPACE:
            tasten[4] = pressed;
            break;
        case KeyEvent.VK_ESCAPE:
            tasten[5] = pressed;
            break;
        }
    }

    public boolean isPressed(int taste) {

        return tasten[taste];
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        druckTaste(ke.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {

        druckTaste(ke.getKeyCode(), false);
    }

    public void jump() {

        if (Character.sprung == false && my >= 400) {

            Character.sprung = true;
            Character.sprungh = Character.my_pos - Character.sprungmax;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        // TODO Auto-generated method stub

    }

    // -------------------------------------- MouseListener -----------------------------------------------------

    @Override
    public void mousePressed(MouseEvent me) {

        mouseX = me.getX();
        mouseY = me.getY();

        Main.dasMenu.testeMenuPunktgeklickt(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    // ------------------------------ MouseMotionListener ------------------------------

    @Override
    public void mouseMoved(MouseEvent me) {

        mouseX = me.getX();
        mouseY = me.getY();

        Main.dasMenu.testeRollOver(mouseX, mouseY);
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
}
