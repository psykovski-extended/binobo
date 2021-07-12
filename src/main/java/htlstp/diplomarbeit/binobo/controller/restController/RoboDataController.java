package htlstp.diplomarbeit.binobo.controller.restController;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.service.RobotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value = "/roboData/rest_api")
public class RoboDataController { // TODO implement all functions

    @Autowired
    RobotDataService robotDataService;

    // Funktion to watch the timestamp, if older than 5mins, then it must be deleted
    // some way for recognizing if Binobo is actually connected to the server

    // client-side: retrieve data from this rest-api with ajax and jQuery

    public static Double generateRandomNumberFromTo(double min, double max){
        return min + (max - min) * Math.random();
    }

    @GetMapping(value = "/generate_random_data/{nums}")
    public ResponseEntity<List<RobotData>> generateRandom(@PathVariable Long nums){
        for(int i = 0; i < nums; i++){
            RobotData rd = new RobotData();
            rd.setLf_tip(generateRandomNumberFromTo(0, 360));
            rd.setLf_middle(generateRandomNumberFromTo(0, 360));
            rd.setLf_base(generateRandomNumberFromTo(0, 360));
            rd.setLf_base_rot(generateRandomNumberFromTo(0, 360));

            rd.setRf_tip(generateRandomNumberFromTo(0, 360));
            rd.setRf_middle(generateRandomNumberFromTo(0, 360));
            rd.setRf_base(generateRandomNumberFromTo(0, 360));
            rd.setRf_base_rot(generateRandomNumberFromTo(0, 360));

            rd.setMf_tip(generateRandomNumberFromTo(0, 360));
            rd.setMf_middle(generateRandomNumberFromTo(0, 360));
            rd.setMf_base(generateRandomNumberFromTo(0, 360));
            rd.setMf_base_rot(generateRandomNumberFromTo(0, 360));

            rd.setPf_tip(generateRandomNumberFromTo(0, 360));
            rd.setPf_middle(generateRandomNumberFromTo(0, 360));
            rd.setPf_base(generateRandomNumberFromTo(0, 360));
            rd.setPf_base_rot(generateRandomNumberFromTo(0, 360));

            rd.setTh_tip(generateRandomNumberFromTo(0, 360));
            rd.setTh_base(generateRandomNumberFromTo(0, 360));
            rd.setTh_rot_orthogonal(generateRandomNumberFromTo(0, 360));
            rd.setTh_rot_palm(generateRandomNumberFromTo(0, 360));

            rd.setAj_lr(generateRandomNumberFromTo(0, 360));
            rd.setAj_bf(generateRandomNumberFromTo(0, 360));

            robotDataService.save(rd);
        }
        return ResponseEntity.accepted().body(robotDataService.findAll());
    }

    @GetMapping(value = "/retrieve_all_data")
    public ResponseEntity<List<RobotData>> getAllEntries(){
        return ResponseEntity.accepted().body(robotDataService.findAll());
    }

    @GetMapping(value = "/retrieve_data")
    public ResponseEntity<RobotData> getLatestRoboData() throws ResourceNotFoundException {
        RobotData rdx = robotDataService.findTopByOrderByIdAsc();
        try {
            robotDataService.delete(rdx);
            return ResponseEntity.ok().body(rdx);
        }catch (Exception iae) {
            return ResponseEntity.badRequest().body(new RobotData());
        }
    }

    @PostMapping(value = "/upload_data")
    public ResponseEntity<RobotData> insertData(@RequestBody RobotData robotData){
        return ResponseEntity.ok().body(robotDataService.save(robotData));
    }
}
