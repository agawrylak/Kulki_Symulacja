import javax.swing.*;
import java.awt.event.*;

public class Listener implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

    Panel panel;

    public Listener(Panel panel) {
        this.panel = panel; //przypisuje odpowiedni panel do listenera przy jego tworzeniu żeby móc korzystać z metod typu repaint() oraz zmiennych listOfBalls i size()
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            panel.setShowingTimer(!panel.isShowingTimer());
        }else if(SwingUtilities.isLeftMouseButton(mouseEvent)){
            panel.getListOfBalls().add(new Ball(new Vector2d(mouseEvent.getX(), mouseEvent.getY()), panel.getBallRadius()));
            panel.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {



    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for (Ball k : panel.getListOfBalls()) {


        }
        //TODO: Podobno nie korzysta się z repaint() tylko z paintComponent();
        panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(SwingUtilities.isLeftMouseButton(mouseEvent)){
            panel.getListOfBalls().add(new Ball(new Vector2d(mouseEvent.getX(), mouseEvent.getY()), panel.getBallRadius()));
            panel.repaint();
        }

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        for(Ball k : panel.getListOfBalls()){
            if(k.getElapsedSeconds() > 5){
                k.getVelocity().setY(-0.5f);
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            int notches = mouseWheelEvent.getWheelRotation();
            panel.setBallRadius(panel.getBallRadius()+notches);

    }

}