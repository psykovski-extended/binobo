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
    // Pinky
    @Column
    private Integer p_tip;
    @Column
    private Integer p_middle;
    @Column
    private Integer p_base;
    @Column
    private Integer p_base_rot;
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
    // Index-Finger
    @Column
    private Integer if_tip;
    @Column
    private Integer if_middle;
    @Column
    private Integer if_base;
    @Column
    private Integer if_base_rot;
    // Thumb
    @Column
    private Integer th_tip;
    @Column
    private Integer th_base;
    @Column
    private Integer th_rot_orthogonal;
    @Column
    private Integer th_rot_palm;

    // wrist
    @Column
    private Integer wr_bf;
    @Column
    private Integer wr_lr;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWr_bf() {
        return wr_bf;
    }

    public void setWr_bf(Integer aj_bf) {
        this.wr_bf = aj_bf;
    }

    public Integer getWr_lr() {
        return wr_lr;
    }

    public void setWr_lr(Integer aj_lr) {
        this.wr_lr = aj_lr;
    }

    public Integer getP_tip() {
        return p_tip;
    }

    public void setP_tip(Integer lf_tip) {
        this.p_tip = lf_tip;
    }

    public Integer getP_middle() {
        return p_middle;
    }

    public void setP_middle(Integer lf_middle) {
        this.p_middle = lf_middle;
    }

    public Integer getP_base() {
        return p_base;
    }

    public void setP_base(Integer lf_base) {
        this.p_base = lf_base;
    }

    public Integer getP_base_rot() {
        return p_base_rot;
    }

    public void setP_base_rot(Integer lf_base_rot) {
        this.p_base_rot = lf_base_rot;
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

    public Integer getIf_tip() {
        return if_tip;
    }

    public void setIf_tip(Integer pf_tip) {
        this.if_tip = pf_tip;
    }

    public Integer getIf_middle() {
        return if_middle;
    }

    public void setIf_middle(Integer pf_middle) {
        this.if_middle = pf_middle;
    }

    public Integer getIf_base() {
        return if_base;
    }

    public void setIf_base(Integer pf_base) {
        this.if_base = pf_base;
    }

    public Integer getIf_base_rot() {
        return if_base_rot;
    }

    public void setIf_base_rot(Integer pf_base_rot) {
        this.if_base_rot = pf_base_rot;
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
