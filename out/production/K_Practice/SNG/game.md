```java
package src;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

enum Direction {UP, DOWN, LEFT, RIGHT}

// Game class
public class SnakeGame extends JFrame implements KeyListener, ActionListener{

    private Timer timer;
    private Snake snake;
    private Apple apple;
    private Direction currentDirection = Direction.RIGHT;
    private boolean isRunning = false;

    public SnakeGame() {
        setTitle("=== Snake Game ===");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        snake = new Snake();
        apple = new Apple();
        timer = new Timer(100, this);
        timer.start();
        isRunning = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isRunning) {
            snake.move(currentDirection);
            if (snake.eatApple(apple)) {
                apple.respawn();
            }
            if (snake.isGameOver()) {
                isRunning = false;
                JOptionPane.showMessageDialog(this, "Game Over!");
            }
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        snake.draw(g);
        apple.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                if(currentDirection != Direction.DOWN) {
                    currentDirection = Direction.UP;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection != Direction.UP) {
                    currentDirection = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection != Direction.RIGHT) {
                    currentDirection = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection != Direction.LEFT) {
                    currentDirection = Direction.RIGHT;
                }
                break;
        }
    }

    @Override    public void keyReleased(KeyEvent e) {}
    @Override    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SnakeGame sgmae = new SnakeGame();
    }
}


// Snake class

class Snake {
    private ArrayList<Point> body;
    private int width = 10;
    private int height = 10;

    public Snake() {
        body = new ArrayList<>();
        body.add(new Point(200, 200));
    }

    public void move(Direction direction){
        Point head = body.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case UP : newHead.y -= height; break;
            case DOWN : newHead.y += height; break;
            case LEFT : newHead.x -= width; break;
            case RIGHT : newHead.x += width; break;
        }

        body.add(0, newHead);
        body.remove(body.size() -1);
    }

    public boolean eatApple(Apple apple) {
        Point head = body.get(0);
        if(head.equals(apple.getPosition())) {
            body.add(new Point(-10, -10));
            return true;
        }
        return false;
    }

    public boolean isGameOver(){
        Point head = body.get(0);
        for (int i=1; i<body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return head.x < 0 || head.x >= 400 || head.y < 0 || head.y >= 400;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x, p.y, width, height);
        }
    }
}


// Apple class

class Apple {
    private Point position;
    private Random random;

    public Apple() {
        random = new Random();
        respawn();
    }

    public void respawn() {
        int x = random.nextInt(40) * 10;
        int y = random.nextInt(40) * 10;
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(position.x, position.y, 10, 10);
    }
}
```