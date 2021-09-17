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
    private Long uploadedOn = System.currentTimeMillis();
    @Column
    private Boolean expired = false;
    // current sampling-rate
    @Column
    private Integer sampling_rate;
    // Little-Finger
    @Column
    private Integer lf_tip;
    @Column
    private Integer lf_middle;
    @Column
    private Integer lf_base;
    @Column
    private Integer lf_base_rot;
    // Ring-Finger
    @Column
    private Integer rf_tip;
    @Column
    private Integer rf_middle;
    @Column
    private Integer rf_base;
    @Column
    private Integer rf_base_rot;
    // Middle-Finger
    @Column
    private Integer mf_tip;
    @Column
    private Integer mf_middle;
    @Column
    private Integer mf_base;
    @Column
    private Integer mf_base_rot;
    // Pointer-Finger
    @Column
    private Integer pf_tip;
    @Column
    private Integer pf_middle;
    @Column
    private Integer pf_base;
    @Column
    private Integer pf_base_rot;
    // Thumb
    @Column
    private Integer th_tip;
    @Column
    private Integer th_base;
    @Column
    private Integer th_rot_orthogonal;
    @Column
    private Integer th_rot_palm;

    // arm - joint
    @Column
    private Integer aj_bf;
    @Column
    private Integer aj_lr;

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

    public Integer getAj_bf() {
        return aj_bf;
    }

    public void setAj_bf(Integer aj_bf) {
        this.aj_bf = aj_bf;
    }

    public Integer getAj_lr() {
        return aj_lr;
    }

    public void setAj_lr(Integer aj_lr) {
        this.aj_lr = aj_lr;
    }

    public Integer getLf_tip() {
        return lf_tip;
    }

    public void setLf_tip(Integer lf_tip) {
        this.lf_tip = lf_tip;
    }

    public Integer getLf_middle() {
        return lf_middle;
    }

    public void setLf_middle(Integer lf_middle) {
        this.lf_middle = lf_middle;
    }

    public Integer getLf_base() {
        return lf_base;
    }

    public void setLf_base(Integer lf_base) {
        this.lf_base = lf_base;
    }

    public Integer getLf_base_rot() {
        return lf_base_rot;
    }

    public void setLf_base_rot(Integer lf_base_rot) {
        this.lf_base_rot = lf_base_rot;
    }

    public Integer getRf_tip() {
        return rf_tip;
    }

    public void setRf_tip(Integer rf_tip) {
        this.rf_tip = rf_tip;
    }

    public Integer getRf_middle() {
        return rf_middle;
    }

    public void setRf_middle(Integer rf_middle) {
        this.rf_middle = rf_middle;
    }

    public Integer getRf_base() {
        return rf_base;
    }

    public void setRf_base(Integer rf_base) {
        this.rf_base = rf_base;
    }

    public Integer getRf_base_rot() {
        return rf_base_rot;
    }

    public void setRf_base_rot(Integer rf_base_rot) {
        this.rf_base_rot = rf_base_rot;
    }

    public Integer getMf_tip() {
        return mf_tip;
    }

    public void setMf_tip(Integer mf_tip) {
        this.mf_tip = mf_tip;
    }

    public Integer getMf_middle() {
        return mf_middle;
    }

    public void setMf_middle(Integer mf_middle) {
        this.mf_middle = mf_middle;
    }

    public Integer getMf_base() {
        return mf_base;
    }

    public void setMf_base(Integer mf_base) {
        this.mf_base = mf_base;
    }

    public Integer getMf_base_rot() {
        return mf_base_rot;
    }

    public void setMf_base_rot(Integer mf_base_rot) {
        this.mf_base_rot = mf_base_rot;
    }

    public Integer getPf_tip() {
        return pf_tip;
    }

    public void setPf_tip(Integer pf_tip) {
        this.pf_tip = pf_tip;
    }

    public Integer getPf_middle() {
        return pf_middle;
    }

    public void setPf_middle(Integer pf_middle) {
        this.pf_middle = pf_middle;
    }

    public Integer getPf_base() {
        return pf_base;
    }

    public void setPf_base(Integer pf_base) {
        this.pf_base = pf_base;
    }

    public Integer getPf_base_rot() {
        return pf_base_rot;
    }

    public void setPf_base_rot(Integer pf_base_rot) {
        this.pf_base_rot = pf_base_rot;
    }

    public Integer getTh_tip() {
        return th_tip;
    }

    public void setTh_tip(Integer th_tip) {
        this.th_tip = th_tip;
    }

    public Integer getTh_base() {
        return th_base;
    }

    public void setTh_base(Integer th_base) {
        this.th_base = th_base;
    }

    public Integer getTh_rot_orthogonal() {
        return th_rot_orthogonal;
    }

    public void setTh_rot_orthogonal(Integer th_rot_orthogonal) {
        this.th_rot_orthogonal = th_rot_orthogonal;
    }

    public Integer getTh_rot_palm() {
        return th_rot_palm;
    }

    public void setTh_rot_palm(Integer th_rot_palm) {
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

    public void setUploadedOn(Long uploadedOn) {
        this.uploadedOn = uploadedOn;
    }
}
