package htlstp.diplomarbeit.binobo.controller.websocket;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.service.RobotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
@EnableScheduling
public class SocketController {

    private final SimpMessagingTemplate template;
    private final RobotDataService robotDataService;

    @Autowired
    public SocketController(SimpMessagingTemplate simpMessagingTemplate, RobotDataService robotDataService){
        this.template = simpMessagingTemplate;
        this.robotDataService = robotDataService;
    }

    @Scheduled(fixedRate = 10)
    public void greeting() {
        HashMap<String, List<RobotData>> res = robotDataService.getAllSortedByToken();
        for(String key : res.keySet()){
            if(key != null && res.get(key).size() > 0)
                template.convertAndSend("/publish-data/" + key, res.get(key));
        }
    }
}
