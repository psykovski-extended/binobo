package htlstp.diplomarbeit.binobo.model.robo;

import htlstp.diplomarbeit.binobo.model.DataAccessToken;

import javax.persistence.*;

@Entity
public class RobotData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private DataAccessToken dataAccessToken;
    @Column
    private final Long uploadedOn = System.currentTimeMillis();
    @Column
    private Boolean expired = false;
    // current sampling-rate
    @Column
    private Integer sampling_rate;
    // Little-Finger
    @Column
    private Double lf_tip;
    @Column
    private Double lf_middle;
    @Column
    private Double lf_base;
    @Column
    private Double lf_base_rot;
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
    // Pointer-Finger
    @Column
    private Double pf_tip;
    @Column
    private Double pf_middle;
    @Column
    private Double pf_base;
    @Column
    private Double pf_base_rot;
    // Thumb
    @Column
    private Double th_tip;
    @Column
    private Double th_base;
    @Column
    private Double th_rot_orthogonal;
    @Column
    private Double th_rot_palm;

    // arm - joint
    @Column
    private Double aj_bf;
    @Column
    private Double aj_lr;

    // TODO adaptive: add elbow and shoulder variables

    public RobotData(){}

    @Override
    public String toString(){
        // will not be returned --> would slow the process down
        return "{\n" +
                "\"little_finger\":" + "{\n" +
                    "\"tip\":" + lf_tip + ",\n" +
                    "\"middle\":" + lf_middle + ",\n" +
                    "\"base\":" + lf_base + ",\n" +
                    "\"rotation\":" + lf_base_rot + "\n},\n" +
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
                    "\"tip\":" + pf_tip + ",\n" +
                    "\"middle\":" + pf_middle + ",\n" +
                    "\"base\":" + pf_base + ",\n" +
                    "\"rotation\":" + pf_base_rot + "\n},\n" +
                "\"thumb\":" + "{\n" +
                    "\"tip\":" + th_tip + ",\n" +
                    "\"base\":" + th_base + ",\n" +
                    "\"rot_orthogonal\":" + th_rot_orthogonal + ",\n" +
                    "\"rot_palm\":" + th_rot_palm + "\n},\n" +
                "\"arm_joint\": {\n" +
                    "\"fw_bw_rot\":" + aj_bf + ",\n" +
                    "\"le_re_rot\":" + aj_lr + "\n}\n" +
                "}\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAj_bf() {
        return aj_bf;
    }

    public void setAj_bf(Double aj_bf) {
        this.aj_bf = aj_bf;
    }

    public Double getAj_lr() {
        return aj_lr;
    }

    public void setAj_lr(Double aj_lr) {
        this.aj_lr = aj_lr;
    }

    public Double getLf_tip() {
        return lf_tip;
    }

    public void setLf_tip(Double lf_tip) {
        this.lf_tip = lf_tip;
    }

    public Double getLf_middle() {
        return lf_middle;
    }

    public void setLf_middle(Double lf_middle) {
        this.lf_middle = lf_middle;
    }

    public Double getLf_base() {
        return lf_base;
    }

    public void setLf_base(Double lf_base) {
        this.lf_base = lf_base;
    }

    public Double getLf_base_rot() {
        return lf_base_rot;
    }

    public void setLf_base_rot(Double lf_base_rot) {
        this.lf_base_rot = lf_base_rot;
    }

    public Double getRf_tip() {
        return rf_tip;
    }

    public void setRf_tip(Double rf_tip) {
        this.rf_tip = rf_tip;
    }

    public Double getRf_middle() {
        return rf_middle;
    }

    public void setRf_middle(Double rf_middle) {
        this.rf_middle = rf_middle;
    }

    public Double getRf_base() {
        return rf_base;
    }

    public void setRf_base(Double rf_base) {
        this.rf_base = rf_base;
    }

    public Double getRf_base_rot() {
        return rf_base_rot;
    }

    public void setRf_base_rot(Double rf_base_rot) {
        this.rf_base_rot = rf_base_rot;
    }

    public Double getMf_tip() {
        return mf_tip;
    }

    public void setMf_tip(Double mf_tip) {
        this.mf_tip = mf_tip;
    }

    public Double getMf_middle() {
        return mf_middle;
    }

    public void setMf_middle(Double mf_middle) {
        this.mf_middle = mf_middle;
    }

    public Double getMf_base() {
        return mf_base;
    }

    public void setMf_base(Double mf_base) {
        this.mf_base = mf_base;
    }

    public Double getMf_base_rot() {
        return mf_base_rot;
    }

    public void setMf_base_rot(Double mf_base_rot) {
        this.mf_base_rot = mf_base_rot;
    }

    public Double getPf_tip() {
        return pf_tip;
    }

    public void setPf_tip(Double pf_tip) {
        this.pf_tip = pf_tip;
    }

    public Double getPf_middle() {
        return pf_middle;
    }

    public void setPf_middle(Double pf_middle) {
        this.pf_middle = pf_middle;
    }

    public Double getPf_base() {
        return pf_base;
    }

    public void setPf_base(Double pf_base) {
        this.pf_base = pf_base;
    }

    public Double getPf_base_rot() {
        return pf_base_rot;
    }

    public void setPf_base_rot(Double pf_base_rot) {
        this.pf_base_rot = pf_base_rot;
    }

    public Double getTh_tip() {
        return th_tip;
    }

    public void setTh_tip(Double th_tip) {
        this.th_tip = th_tip;
    }

    public Double getTh_base() {
        return th_base;
    }

    public void setTh_base(Double th_base) {
        this.th_base = th_base;
    }

    public Double getTh_rot_orthogonal() {
        return th_rot_orthogonal;
    }

    public void setTh_rot_orthogonal(Double th_rot_orthogonal) {
        this.th_rot_orthogonal = th_rot_orthogonal;
    }

    public Double getTh_rot_palm() {
        return th_rot_palm;
    }

    public void setTh_rot_palm(Double th_rot_palm) {
        this.th_rot_palm = th_rot_palm;
    }

    public Integer getSampling_rate() {
        return sampling_rate;
    }

    public void setSampling_rate(Integer sampling_rate) {
        this.sampling_rate = sampling_rate;
    }

    public Long getUploadedOn() {
        return uploadedOn;
    }

    public Boolean isExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public DataAccessToken getDataAccessToken() {
        return dataAccessToken;
    }

    public void setDataAccessToken(DataAccessToken dataAccessToken) {
        this.dataAccessToken = dataAccessToken;
    }
}
