package htlstp.diplomarbeit.binobo.model;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class DataAccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private final String token = UUID.randomUUID().toString();
    @OneToMany
    private List<RobotData> robotDataList;

    public DataAccessToken(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public List<RobotData> getRobotDataList() {
        return robotDataList;
    }

    public void setRobotDataList(List<RobotData> robotDataList) {
        this.robotDataList = robotDataList;
    }
}
