package rally;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Rally<string> implements ActionListener, KeyListener {

    public Renderer renderer;

    public SaveLoad saveLoad;

    public Rectangle car;

    public static Rally rally;

    public Random rand;

    public int posX ,speed = 1, score, minSpeed = 1;

    public boolean gameOver = false;

    public ArrayList<Rectangle> line, bonus;

    public String plik = "wynik.txt" ;
    public String plikImie = "imie.txt" ;

    public int wynik = saveLoad.odczytajPlik(plik);
    public String imieOdczyt = saveLoad.odczytajPlikString(plikImie);

    private static String imie;

    public final int WIDTH = 500, HEIGHT = 600;

    public Rally() {
        renderer = new Renderer();

        rand = new Random();

        Timer timer = new Timer(20,(ActionListener) this);

        JFrame jframe = new JFrame();

        jframe.add(renderer);
        jframe.setSize( WIDTH+100, HEIGHT);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setTitle("Rally");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addKeyListener(this);

        line = new ArrayList<Rectangle>();
        bonus = new ArrayList<Rectangle>();

        car = new Rectangle(WIDTH/2 -10,HEIGHT-60,20,20);

        timer.start();
        addAllLine();

        
    }

    public void addLine() {
        boolean rnd ;
        int rndInt;
            rnd = rand.nextBoolean();
            rndInt = rand.nextInt(10);
            if(rnd) {
                if(posX>=30) {
                    posX -= 30;
                }
            }else{
                if(posX<=WIDTH-180) {
                    posX += 30;
                }
            }
            line.add(new Rectangle(0, 2, posX, 30));
            line.add(new Rectangle(posX+200, 2, WIDTH-(posX+200), 30));

            if(rndInt == 5) {
                bonus.add(new Rectangle(posX + 100, 2, 5, 5));

            }
    }

    public void addAllLine() {
        posX = 150;
        for (int i = HEIGHT; i >= 30; i -= 30) {
            line.add(new Rectangle(0, i, posX, 30));
            line.add(new Rectangle(posX+150, i, WIDTH-(posX+150), 30));
        }
    }

    public void printLine(Graphics g,Rectangle line){
        g.setColor(Color.white);
        g.fillRect(line.x, line.y, line.width, line.height);
    }

    public void printBonus(Graphics g,Rectangle line){
        g.setColor(Color.green);
        g.fillRect(line.x, line.y, line.width, line.height);
    }

    public void left(){
        if(car.x > 0) {
            car.x -= 10;
        }
    }

    public void right(){
        if(car.x < WIDTH - car.width) {
            car.x += 10;
        }
    }

    public static void main(String[] args) {
        rally = new Rally();
    }

    public void repaint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0,0,WIDTH,HEIGHT);


        for (Rectangle line : line){
            printLine(g, line);
        }

        for (Rectangle bonus : bonus){
            printBonus(g, bonus);
        }

        g.setColor((Color.BLACK));
        g.fillRect(car.x, car.y, car.width, car.height);

        g.setColor(Color.red);
        g.setFont(new Font("Arial",1,48));
        if(gameOver && score<wynik) {
            g.drawString("Game Over !", WIDTH / 2 - 130, HEIGHT / 2 - 70);
        } else if(gameOver && score>wynik){
                g.drawString("High Score !!! !",WIDTH/2-130,HEIGHT/2-70);
            }


        g.setColor(Color.red);
        g.setFont(new Font("Arial",1,30));
        if(gameOver){
            g.drawString("Press space to start !",WIDTH/2-150,HEIGHT/2);
        }

        g.setColor(Color.blue);
        g.setFont(new Font("Arial",1,16));
        g.drawString("Score :",WIDTH+10,20);
        g.drawString(String.valueOf(score),WIDTH+30,50);

        g.setColor(Color.blue);
        g.setFont(new Font("Arial",1,16));
        g.drawString("Speed :",WIDTH+10,120);
        g.drawString(String.valueOf(speed),WIDTH+30,150);

        g.setColor(Color.blue);
        g.setFont(new Font("Arial",1,16));
        g.drawString("High Score !",WIDTH,220);
        g.drawString(String.valueOf(wynik),WIDTH+30,250);
        g.drawString(imieOdczyt,WIDTH+10,280);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver) {
            for (int i = 0; i < line.size(); i++) {
                Rectangle Line = line.get(i);
                Line.y += speed;
            }
            for (int i = 0; i < bonus.size(); i++) {
                Rectangle Bonus = bonus.get(i);
                Bonus.y += speed;
            }

            for (Rectangle Line : line) {
                if (Line.intersects(car)) {
                    gameOver = true;
                } else {
                    //gameOver = false;
                }
            }

            for (int i=0; i < bonus.size(); i++){
                Rectangle Bonus = bonus.get(i);
                if (Bonus.intersects(car)){
                    bonus.remove(Bonus);
                    score+=50;
                }
            }

            for (int i = 0; i < line.size(); i += 2) {
                Rectangle Line = line.get(i);
                Rectangle Liine = line.get(i + 1);
                if (Line.y >= HEIGHT) {
                    line.remove(Line);
                    line.remove(Liine);
                    addLine();
                    score = score + 1;
                    if(score%30 == 0 ){
                        minSpeed++;
                        if(minSpeed>=speed){
                            speed = minSpeed;
                        }
                    }
                }
            }

            renderer.repaint();
        }else if (gameOver) {
            if(score>wynik) {
                if(score > wynik ) {
                    imie = JOptionPane.showInputDialog("podaj imie : ");
                    saveLoad.zapiszPlik(plik, score);
                    saveLoad.zapiszPlikString(plikImie,imie);
                    score = 0;
                }


            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right();
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            line.clear();
            bonus.clear();
            car = new Rectangle(WIDTH/2 -10,HEIGHT-60,20,20);
            addAllLine();
            speed = 1;
            minSpeed = 1;
            score = 0;
            wynik = saveLoad.odczytajPlik(plik);
            imieOdczyt = saveLoad.odczytajPlikString(plikImie);
            gameOver = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            speed++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(speed>minSpeed) {
                speed--;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
