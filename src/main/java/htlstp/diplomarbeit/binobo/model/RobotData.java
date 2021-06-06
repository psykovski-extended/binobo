package htlstp.diplomarbeit.binobo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RobotData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isConnected;

    // define all fingers with their joints
    private Double lf_tip;
    private Double lf_middle;
    private Double lf_bottom;
    private Double lf_base_rot;


}
