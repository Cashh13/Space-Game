import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Obstacle{


    private Timer t;
    ImageIcon star;
    JLabel label;
    Rectangle s;
    int speed;


    public Obstacle(int x, int y, int width, int height) {



        ImageIcon star1 = new ImageIcon("star.png");
        Image star2 = star1.getImage();
        Image star3 = star2.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        star = new ImageIcon(star3);

        label = new JLabel(star);
        label.setBounds(x, y, width, height);
        s = label.getBounds();


//        label.setIcon(star);

        t = new Timer(10, e -> moveDown(5)); //speed of stars
        t.start();


    }

    public void moveDown(int speed) {
        label.setLocation(label.getX(), label.getY() + speed);
        s=label.getBounds();
    }

    public JLabel getLabel() {

        return label;
    }

    public Rectangle getBounds() {
        return label.getBounds();
    }
    public void removeAll() {

        this.removeAll();
    }


}

