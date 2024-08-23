import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class GamePanel extends JFrame implements KeyListener{

    JLabel label;
    JButton start;
    JLabel scoreLabel;
    JLabel highScoreLabel;
    //JLabel bg = new JLabel(new ImageIcon("space.png"));
    private int speed = 350;
    JLabel gameMode;




    Random random = new Random();
    private boolean w;
    private boolean d;
    private boolean s;
    private boolean a;
    private boolean running = false;
    private int score;
    private int playAgain;
    private int highScore;

    int gitTest;

    private Timer timer;
    private Timer timer2;
    private Timer timer3;
    ImageIcon rocket;
    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;

    private ButtonGroup modeGroup;

    private List<Obstacle> obstacles = new ArrayList<>();






    GamePanel(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLayout(null);
        //bg.setLayout(new BorderLayout());
        this.addKeyListener(this);
        start = new JButton("Start");
        start.setBackground(Color.white);
        this.setResizable(false);
        modeGroup = new ButtonGroup();
        gameMode = new JLabel("Game Mode");


        easyButton = new JRadioButton("Easy");
        mediumButton = new JRadioButton("Medium");
        hardButton = new JRadioButton("Hard");

        // Set bounds (position and size)
        easyButton.setBounds(360, 260, 80, 25);
        mediumButton.setBounds(360, 280, 80, 25);
        hardButton.setBounds(360, 300, 80, 25);



        gameMode.setForeground(Color.WHITE);
        gameMode.setFont(new Font("Arial", Font.BOLD, 20));

        gameMode.setBounds(345,200,250,50);

        this.add(easyButton);
        this.add(mediumButton);
        this.add(hardButton);
        this.add(gameMode);

        modeGroup.add(easyButton);
        modeGroup.add(mediumButton);
        modeGroup.add(hardButton);

        mediumButton.setSelected(true);



        //setContentPane(bg);
        int y = this.getHeight()/2 - 25;
        int x = this.getWidth() /2 - 75;

        start.setFont(new Font("Arial", Font.BOLD, 20));
        start.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        start.setFocusPainted(false);


        start.setBounds(x,y,150,50);

        start.setForeground(Color.BLACK);

        this.add(start);



        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setBounds(10, 10, 200, 30);
        this.add(scoreLabel);


        highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        highScoreLabel.setBounds(625, 10, 200, 30);
        this.add(highScoreLabel);
        GamePanel.this.repaint();


        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (easyButton.isSelected()) {

                    speed = 1000;
                    gameMode.setBounds(365,200,250,50);
                    gameMode.setText("Easy");

                } else if (mediumButton.isSelected()) {

                    speed = 350;
                    gameMode.setBounds(365,200,250,50);
                    gameMode.setText("Medium");

                } else if (hardButton.isSelected()) {

                    speed = 200;
                    gameMode.setBounds(365,200,250,50);
                    gameMode.setText("Hard");


                }


                if (timer2 != null) {
                    timer2.stop();
                }


                timer2 = new Timer(speed, new ActionListener() { //speed
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Obstacle o = createObstacle();
                        obstacles.add(o);
                        GamePanel.this.add(o.getLabel());
                        GamePanel.this.repaint();
                    }
                });





            }

        };
        easyButton.addActionListener(actionListener);
        mediumButton.addActionListener(actionListener);
        hardButton.addActionListener(actionListener);

        this.add(easyButton);
        this.add(mediumButton);
        this.add(hardButton);


        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                GamePanel.this.remove(start);
                GamePanel.this.remove(gameMode);
                GamePanel.this.remove(easyButton);
                GamePanel.this.remove(mediumButton);
                GamePanel.this.remove(hardButton);
                GamePanel.this.repaint();
                timer2.start();
                timer3.start();
                GamePanel.this.requestFocusInWindow();
            }
        });










        label = new JLabel();
        label.setBounds(350, 500, 100, 175);

        ImageIcon rocket1 = new ImageIcon("rocket.png");
        Image rocket2 = rocket1.getImage();
        Image rocket3 = rocket2.getScaledInstance(100, 175, Image.SCALE_SMOOTH);
        rocket = new ImageIcon(rocket3);

        getContentPane().setBackground(Color.BLACK);



        label.setIcon(rocket);


        this.add(label);
        this.setVisible(true);




        timer = new Timer(1, new ActionListener() { //delay for movement
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
                inFrame();
                crash();


            }
        });

        timer.start();


        timer2 = new Timer(speed, new ActionListener() {  //how often stars drop
            @Override
            public void actionPerformed(ActionEvent e) {

                Obstacle o = createObstacle();
                obstacles.add(o);
                GamePanel.this.add(o.getLabel());
                GamePanel.this.repaint();


            }
        });



        timer3 = new Timer(1000, new ActionListener() { //score
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
                if (score > highScore) {
                    highScore++;
                }

                highScoreLabel.setText("High Score: " + highScore);
                scoreLabel.setText("Score: " + score);
                GamePanel.this.repaint();

            }
        });




    }

    private void crash() {
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle o = iterator.next();
            o.moveDown(5);
            if (o.getBounds().intersects(label.getBounds())) {
                stopGame();
                return;
            }
            if (o.getBounds().getY() > this.getHeight()) {
                this.remove(o.getLabel());
                iterator.remove();
            }
        }
    }





    public Obstacle createObstacle(){
        return new Obstacle(getrandX(), -10, 40, 40);
    }


    private void stopGame() {
        label.setLocation(350, 500);
        timer.stop();
        timer2.stop();
        timer3.stop();
        playAgain = JOptionPane.showConfirmDialog(this, "Game Over! Score: " + score + ". Press Ok to play again (:", "Game Over", JOptionPane.OK_CANCEL_OPTION);
        restartGame();
    }



    private void restartGame() {
        if (playAgain == JOptionPane.OK_OPTION) {
            score = 0;

            if (score > highScore)
            {
                highScore = score;
            }

            highScoreLabel.setText("High Score: " + highScore);
            scoreLabel.setText("Score: " + score);



            for (Obstacle o : obstacles) {
                this.remove(o.getLabel());
            }
            obstacles.clear();
            w = false;
            d = false;
            a = false;
            s = false;

            this.repaint();

            timer.start();
            timer2.start();
            timer3.start();
        } else {
            System.exit(0);
        }
    }


    public int getrandX(){
        return random.nextInt(801);
    }

//    public int getrandY(){
//        return random.nextInt(-10);
//    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {

            case 'w':

                w = true;
                break;
            case 'a':

                a = true;
                break;
            case 's':

                s = true;
                break;
            case 'd':

                d = true;
                break;
        }
        move();
        inFrame();


    }




    public void inFrame(){
        int x = label.getX();
        int y = label.getY();


        if (x < 0) {
            x=0;
        }
        else if (x +label.getWidth() > this.getWidth()) {
            x = this.getWidth() - label.getWidth();
        }

        if (y < 0) {
            y=0;
        }
        else if (y +label.getHeight() > this.getHeight()) {
            y = this.getHeight() - label.getHeight();
        }

        label.setLocation(x, y);




    }

    public void move() {


        if (w && d) {
            label.setLocation(label.getX() + 7, label.getY() - 7);
        } else if (a && w) {
            label.setLocation(label.getX() - 7, label.getY() - 7);
        } else if (s && a) {
            label.setLocation(label.getX() - 7, label.getY() + 7);
        } else if (s && d) {
            label.setLocation(label.getX() + 7, label.getY() + 7);
        } else {

            if (w) {
                label.setLocation(label.getX(), label.getY() - 7); // Moving up
            }
            if (a) {
                label.setLocation(label.getX() - 7, label.getY()); // Moving left
            }
            if (s) {
                label.setLocation(label.getX(), label.getY() + 7); // Moving down
            }
            if (d) {
                label.setLocation(label.getX() + 7, label.getY()); // Moving right
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyChar()) {

            case 'w':
                w=false;
                break;
            case 'a':
                a=false;
                break;
            case 's':
                s=false;
                break;
            case 'd':
                d=false;
                break;
        }
        move();
        inFrame();

    }





}