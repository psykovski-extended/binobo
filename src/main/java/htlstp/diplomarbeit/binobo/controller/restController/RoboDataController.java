package htlstp.diplomarbeit.binobo.controller.restController;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.service.RobotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping(value = "/roboData/rest_api")
public class RoboDataController {

    private final RobotDataService robotDataService;

    @Autowired
    public RoboDataController(RobotDataService robotDataService){
        this.robotDataService = robotDataService;
    }

    public static Double generateRandomNumberFromTo(double min, double max){
        return min + (max - min) * Math.random();
    }

    @PutMapping(value = "/{token}/generate_random_data/{nums}")
    public ResponseEntity<?> generateRandom(@PathVariable(name = "nums") Long nums, @PathVariable(name = "token") String token){
        DataAccessToken dat;
        try {
            dat = robotDataService.findDATByToken(token);
        } catch (NullPointerException npe) {
            return ResponseEntity.badRequest().body(new FlashMessage("Invalid token! Access denied!", FlashMessage.Status.FAILURE));
        }
        for(int i = 0; i < nums; i++){ // change to linear gradient because of wtf-reaction
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

            rd.setSampling_rate(20);
            rd.setDataAccessToken(dat);

            robotDataService.save(rd);
        }
        return ResponseEntity.accepted().body(robotDataService.findAllByDataAccessToken(token));
    }

    @GetMapping(value = "/{token}/retrieve_all_data")
    public ResponseEntity<?> getAllEntries(@PathVariable String token){
        try {
            return ResponseEntity.accepted().body(robotDataService.findAllByDataAccessToken(token));
        }catch (NullPointerException npe){
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    @GetMapping(value = "/{token}/retrieve_data")
    public ResponseEntity<?> getLatestRoboData(@PathVariable String token) {
        try {
            RobotData rdx = robotDataService.findTopByDataAccessToken(token);
            robotDataService.delete(rdx);
            return ResponseEntity.accepted().body(rdx);
        }catch (DataAccessResourceFailureException accessExc) {
            return ResponseEntity.badRequest().body(new FlashMessage(accessExc.getMessage(), FlashMessage.Status.FAILURE));
        }catch (NullPointerException npe) {
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    @PostMapping(value = "/{token}/upload_data")
    public ResponseEntity<?> insertData(@RequestBody RobotData robotData, @PathVariable String token){
        try {
            DataAccessToken dat = robotDataService.findDATByToken(token);
            robotData.setDataAccessToken(dat);
            return ResponseEntity.accepted().body(robotDataService.save(robotData));
        } catch (NullPointerException npe) {
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    @DeleteMapping(value = "/{token}/delete_all_to_synchronize")
    public ResponseEntity<?> deleteAllByToken(@PathVariable String token){
        try {
            robotDataService.deleteAllByDataAccessToken(token);
            return ResponseEntity.accepted().body(new FlashMessage("Data got successfully deleted", FlashMessage.Status.SUCCESS));
        } catch (NullPointerException npe){
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }
}
