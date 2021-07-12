package htlstp.diplomarbeit.binobo.model.robo;

public class RobotFinger {

    private Double finger_top;
    private Double finger_middle;
    private Double finger_base;
    private Double finger_base_rot;

    public RobotFinger(double ft, double fm, double fb, double fbr){
        this.finger_top = ft;
        this.finger_middle = fm;
        this.finger_base = fb;
        this.finger_base_rot = fbr;
    }

    public Double getFinger_top() {
        return finger_top;
    }

    public void setFinger_top(Double finger_top) {
        this.finger_top = finger_top;
    }

    public Double getFinger_middle() {
        return finger_middle;
    }

    public void setFinger_middle(Double finger_middle) {
        this.finger_middle = finger_middle;
    }

    public Double getFinger_base() {
        return finger_base;
    }

    public void setFinger_base(Double finger_base) {
        this.finger_base = finger_base;
    }

    public Double getFinger_base_rot() {
        return finger_base_rot;
    }

    public void setFinger_base_rot(Double finger_base_rot) {
        this.finger_base_rot = finger_base_rot;
    }
}
