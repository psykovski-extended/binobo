package htlstp.diplomarbeit.binobo.model.robo;

import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class RobotData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private DataAccessToken dataAccessToken;
    @Column
    private Long uploadedOn = System.currentTimeMillis();
    @Column
    private Boolean expired = false;
    // current sampling-rate
    @Column
    private Integer sampling_rate;
    // Pinky
    @Column
    private Double p_tip;
    @Column
    private Double p_middle;
    @Column
    private Double p_base;
    @Column
    private Double p_base_rot;
    // Ring-Finger
    @Column
    private Double rf_tip;
    @Column
    private Double rf_middle;
    @Column
    private Double rf_base;
    @Column
    private Double rf_base_rot;
    // Middle-Finger
    @Column
    private Double mf_tip;
    @Column
    private Double mf_middle;
    @Column
    private Double mf_base;
    @Column
    private Double mf_base_rot;
    // Index-Finger
    @Column
    private Double if_tip;
    @Column
    private Double if_middle;
    @Column
    private Double if_base;
    @Column
    private Double if_base_rot;
    // Thumb
    @Column
    private Double th_tip;
    @Column
    private Double th_base;
    @Column
    private Double th_rot_orthogonal;
    @Column
    private Double th_rot_palm;

    // wrist
    @Column
    private Double wr_bf;
    @Column
    private Double wr_lr;

    // TODO adaptive: add elbow and shoulder variables

    public RobotData(){}

    @Override
    public String toString(){
        // will not be returned --> would slow the process down
        return "{\n" +
                "\"little_finger\":" + "{\n" +
                    "\"tip\":" + p_tip + ",\n" +
                    "\"middle\":" + p_middle + ",\n" +
                    "\"base\":" + p_base + ",\n" +
                    "\"rotation\":" + p_base_rot + "\n},\n" +
                "\"ring_finger\":" + "{\n" +
                    "\"tip\":" + rf_tip + ",\n" +
                    "\"middle\":" + rf_middle + ",\n" +
                    "\"base\":" + rf_base + ",\n" +
                    "\"rotation\":" + rf_base_rot + "\n},\n" +
                "\"middle_finger\":" + "{\n" +
                    "\"tip\":" + mf_tip + ",\n" +
                    "\"middle\":" + mf_middle + ",\n" +
                    "\"base\":" + mf_base + ",\n" +
                    "\"rotation\":" + mf_base + "\n},\n" +
                "\"pointer_finger\":" + "{\n" +
                    "\"tip\":" + if_tip + ",\n" +
                    "\"middle\":" + if_middle + ",\n" +
                    "\"base\":" + if_base + ",\n" +
                    "\"rotation\":" + if_base_rot + "\n},\n" +
                "\"thumb\":" + "{\n" +
                    "\"tip\":" + th_tip + ",\n" +
                    "\"base\":" + th_base + ",\n" +
                    "\"rot_orthogonal\":" + th_rot_orthogonal + ",\n" +
                    "\"rot_palm\":" + th_rot_palm + "\n},\n" +
                "\"arm_joint\": {\n" +
                    "\"fw_bw_rot\":" + wr_bf + ",\n" +
                    "\"le_re_rot\":" + wr_lr + "\n}\n" +
                "}\n";
    }

    public Boolean isExpired(){
        return expired;
    }
}
