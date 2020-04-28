import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    private ArrayList<Ball> listOfBalls;
    private int ballRadius = 20;

    private Listener listener;

    private boolean showingTimer = false;

    public Panel() {
        listOfBalls = new ArrayList<>();
        setBackground(Color.BLACK);

        listener = new Listener(this);

        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(listener);

        mainLoop();
    }

    public void mainLoop() {
        Thread thread = new Thread() {
            public void run() {

                long previousTime = System.currentTimeMillis();
                long currentTime = previousTime;

                while (true) {
                    currentTime = System.currentTimeMillis();

                    updateGame(currentTime - previousTime);
                    repaint();

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                    }
                    previousTime = currentTime;

                }
            }
        };
        thread.start();


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //dzieki temu kulki beda okragle, a nie kanciaste

        for (Ball k : listOfBalls) {

            g2.setColor(k.color);
            g2.fillOval((int)k.getPosition().getX() - k.getRadius(), (int)k.getPosition().getY() - k.getRadius() , k.getRadius()*2, k.getRadius()*2);

            if(showingTimer){
                g2.setColor(Color.white);
                g2.drawString(String.valueOf(k.getElapsedSeconds()),(int)k.getPosition().getX() - k.getRadius(),(int)k.getPosition().getY() - k.getRadius());

                g2.drawString(String.valueOf(k.getVelocity().getX())+" " +String.valueOf(k.getVelocity().getY()),(int)k.getPosition().getX() + k.getRadius(),(int)k.getPosition().getY() + k.getRadius());
            }


        }
        //licznik kul

        g2.setColor(Color.YELLOW);
        g2.drawString(Integer.toString(listOfBalls.size()), 40, 40);

    }

    public ArrayList<Ball> getListOfBalls() {
        return listOfBalls;
    }

    public int getBallRadius() {
        return ballRadius;
    }

    public void insertionSort(ArrayList<Ball> a) {
        for (int p = 1; p < listOfBalls.size(); p++) {
            Ball tmp = a.get(p);
            int j = p;

            for (; j > 0 && tmp.compareTo(a.get(j - 1)) < 0; j--)
                a.set(j,a.get(j-1));

            a.set(j,tmp);
        }
    }

    public void checkCollisions() {
        insertionSort(listOfBalls);


        for (int i = 0; i < listOfBalls.size(); i++) {

            if (listOfBalls.get(i).getPosition().getX() - listOfBalls.get(i).getRadius() < 0) {
                listOfBalls.get(i).getPosition().setX(listOfBalls.get(i).getRadius()); // Place ball against edge
                listOfBalls.get(i).getVelocity().setX(-(listOfBalls.get(i).getVelocity().getX())*0.8f); // Reverse direction and account for friction
            } else if (listOfBalls.get(i).getPosition().getX() + listOfBalls.get(i).getRadius() > getWidth()) // Right Wall
            {
                listOfBalls.get(i).getPosition().setX(getWidth() - listOfBalls.get(i).getRadius());        // Place ball against edge
                listOfBalls.get(i).getVelocity().setX(-(listOfBalls.get(i).getVelocity().getX())*0.8f); // Reverse direction and account for friction
            }

            if (listOfBalls.get(i).getPosition().getY() - listOfBalls.get(i).getRadius() < 0)                // Top Wall
            {
                listOfBalls.get(i).getPosition().setY(listOfBalls.get(i).getRadius());                // Place ball against edge
                listOfBalls.get(i).getVelocity().setY(-(listOfBalls.get(i).getVelocity().getY())*0.8f); // Reverse direction and account for friction
            } else if (listOfBalls.get(i).getPosition().getY() + listOfBalls.get(i).getRadius() > getHeight()) // Bottom Wall
            {
                listOfBalls.get(i).getPosition().setY(getHeight() - listOfBalls.get(i).getRadius());        // Place ball against edge
                listOfBalls.get(i).getVelocity().setY(-(listOfBalls.get(i).getVelocity().getY())*0.8f);    // Reverse direction and account for friction
            }




            for (int j = i + 1; j < listOfBalls.size(); j++) {
                if ((listOfBalls.get(i).getPosition().getX() + listOfBalls.get(i).getRadius()) < (listOfBalls.get(j).getPosition().getX() - listOfBalls.get(j).getRadius()))
                    break;

                if ((listOfBalls.get(i).getPosition().getY() + listOfBalls.get(i).getRadius()) < (listOfBalls.get(j).getPosition().getY() - listOfBalls.get(j).getRadius()) ||
                        (listOfBalls.get(j).getPosition().getY() + listOfBalls.get(j).getRadius()) < (listOfBalls.get(i).getPosition().getY() - listOfBalls.get(i).getRadius()))
                    continue;

                listOfBalls.get(i).resolveCollision(listOfBalls.get(j));

            }
        }


    }
    

    public void updateGame(float elapsedSeconds) {


        for (int i = 0; i < listOfBalls.size(); i++) {
            listOfBalls.get(i).updateElapsedSeconds(elapsedSeconds);
            if(listOfBalls.get(i).getElapsedSeconds() > 5){
                listOfBalls.get(i).slowDown();

            }
                listOfBalls.get(i).getPosition().setX(listOfBalls.get(i).getPosition().getX() + (listOfBalls.get(i).getVelocity().getX() * (elapsedSeconds)));
                listOfBalls.get(i).getPosition().setY(listOfBalls.get(i).getPosition().getY() + (listOfBalls.get(i).getVelocity().getY() * (elapsedSeconds)));





        }
        checkCollisions();


    }

    public void setShowingTimer(boolean showingTimer) {
        this.showingTimer = showingTimer;
    }

    public boolean isShowingTimer() {
        return showingTimer;
    }
}