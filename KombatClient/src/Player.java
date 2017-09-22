
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class Player extends JLabel{
    public int x = 0, y = 0;    
    ImageIcon walkL;
    ImageIcon walkR;
    ImageIcon walkU;
    ImageIcon walkD;
    public void setup(){
        setText("12");
        walkR = new ImageIcon(new ImageIcon(getClass().getResource("walk_r.gif")).getImage().getScaledInstance(88, 127, Image.SCALE_DEFAULT));
        walkL = new ImageIcon(new ImageIcon(getClass().getResource("walk_l.gif")).getImage().getScaledInstance(88, 127, Image.SCALE_DEFAULT));
        setBounds(x, y, 90, 127);
        setIcon(walkR);
    }
    
    public void move(){
        setBounds(x, y, 90, 127);
    }
    
    public void setIconRight(){
        setIcon(walkR);
    }
    
    public void setIconLeft(){
        setIcon(walkL);
    }
}
