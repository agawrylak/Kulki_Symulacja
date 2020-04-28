
import java.awt.*;
import java.util.concurrent.TimeUnit;


public class Ball {

    private Vector2d position; //zamieniłem na wektory bo będzie łatwiej przy kolizjach
    private Vector2d velocity;
    private int radius;
    public boolean xc = false, yc = false;
    public Color color;
    private final int MAX_SPEED = 5;
    private float mass = 1000; //w razie czego zostawiam w tej postaci żeby nie pogubić się w kolizjach
    private float elapsedSeconds;


    public Ball(Vector2d position, int radius) {
        this.position = position;
        this.velocity = new Vector2d(0,0);
        this.radius = radius;
        this.elapsedSeconds = 0;


        // składowe RGB
        color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

        double angle = Math.toRadians(Math.random() * 360);
        this.velocity.set((float) (20f/200f * Math.cos(angle)), ((float) (20f/200f * Math.sin(angle))));





        // xspeed = 0? .....


    }

    public void resolveCollision(Ball ball) {

        // get the mtd
        Vector2d delta = (position.subtract(ball.position));
        float r = getRadius() + ball.getRadius();
        float dist2 = delta.dot(delta);

        if (dist2 > r * r) return;


        float d = delta.getLength();

        Vector2d mtd;
        if (d != 0.0f) {
            mtd = delta.multiply(((getRadius() + ball.getRadius()) - d) / d); // minimum translation distance to push balls apart after intersecting

        } else
        {
            d = ball.getRadius() + getRadius() - 1.0f;
            delta = new Vector2d(ball.getRadius() + getRadius(), 0.0f);

            mtd = delta.multiply(((getRadius() + ball.getRadius()) - d) / d);
        }


        float im1 = 1 / getMass(); // inverse mass quantities
        float im2 = 1 / ball.getMass();


        position = position.add(mtd.multiply(im1 / (im1 + im2)));
        ball.position = ball.position.subtract(mtd.multiply(im2 / (im1 + im2)));


        Vector2d v = (this.velocity.subtract(ball.velocity));
        float vn = v.dot(mtd.normalize());

        if (vn > 0.0f) return;

        float i = (-(1.0f + 1) * vn) / (im1 + im2);
        Vector2d impulse = mtd.multiply(i);

        this.velocity = this.velocity.add(impulse.multiply(im1));
        ball.velocity = ball.velocity.subtract(impulse.multiply(im2));

    }

    public int compareTo(Ball ball) {
        if (this.position.getX() - this.getRadius() > ball.position.getX() - ball.getRadius()) {
            return 1;
        } else if (this.position.getX() - this.getRadius() < ball.position.getX() - ball.getRadius()) {
            return -1;
        } else {
            return 0;
        }
    }
    public void slowDown(){


        this.velocity.setY(this.velocity.getY()+0.001f);

        if(0.0099f > velocity.getX() && velocity.getX() > -0.0099f){
            velocity.setX(0);
        }else{
            this.velocity.setX(this.velocity.getX()-this.velocity.getX()/1000);

        }



    }

    public void updateElapsedSeconds(float elapsedSeconds){
        this.elapsedSeconds += elapsedSeconds/1000;
    }



    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public int getRadius() {
        return radius;
    }

    public float getMass() {
        return mass;
    }

    public float getElapsedSeconds() {
        return elapsedSeconds;
    }
}