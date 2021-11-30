package htlstp.diplomarbeit.binobo.controller.restController;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.service.RobotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Vector;

@RestController
@ResponseBody
@RequestMapping(value = "/roboData/rest_api")
public class RoboDataController {

    private final RobotDataService robotDataService;

    @Autowired
    public RoboDataController(RobotDataService robotDataService){
        this.robotDataService = robotDataService;
    }

    /**
     * Generates a random number in between min and max
     * @param min Lower border
     * @param max Upper border
     * @return Returns the random number as casted to Integer
     */
    public static Integer generateRandomNumberFromTo(double min, double max){
        return (int) Math.floor(min + (max - min) * Math.random());
    }

    /**
     * generates Random test-data
     * @param nums Amount of datasets to be created
     * @param token Token of user to create data for
     * @return Returns all generated datasets
     */
    @PutMapping(value = "/{token}/generate_random_data/{nums}")
    public ResponseEntity<?> generateRandom(@PathVariable(name = "nums") Long nums, @PathVariable(name = "token") String token){
        DataAccessToken dat;
        try {
            dat = robotDataService.findDATByToken(token);
        } catch (NullPointerException npe) {
            return ResponseEntity.badRequest().body(new FlashMessage("Invalid token! Access denied!", FlashMessage.Status.FAILURE));
        }
        for(int i = 0; i < nums; i++){ // change to linspace because of wtf-reaction
            RobotData rd = new RobotData();
            rd.setP_tip(generateRandomNumberFromTo(0, 90));
            rd.setP_middle(generateRandomNumberFromTo(0, 90));
            rd.setP_base(generateRandomNumberFromTo(0, 90));
            rd.setP_base_rot(generateRandomNumberFromTo(0, 90));

            rd.setRf_tip(generateRandomNumberFromTo(0, 90));
            rd.setRf_middle(generateRandomNumberFromTo(0, 90));
            rd.setRf_base(generateRandomNumberFromTo(0, 90));
            rd.setRf_base_rot(generateRandomNumberFromTo(0, 90));

            rd.setMf_tip(generateRandomNumberFromTo(0, 90));
            rd.setMf_middle(generateRandomNumberFromTo(0, 90));
            rd.setMf_base(generateRandomNumberFromTo(0, 90));
            rd.setMf_base_rot(generateRandomNumberFromTo(0, 90));

            rd.setIf_tip(generateRandomNumberFromTo(0, 90));
            rd.setIf_middle(generateRandomNumberFromTo(0, 90));
            rd.setIf_base(generateRandomNumberFromTo(0, 90));
            rd.setIf_base_rot(generateRandomNumberFromTo(0, 90));

            rd.setTh_tip(generateRandomNumberFromTo(0, 90));
            rd.setTh_base(generateRandomNumberFromTo(0, 90));
            rd.setTh_rot_orthogonal(generateRandomNumberFromTo(0, 90));
            rd.setTh_rot_palm(generateRandomNumberFromTo(0, 90));

            rd.setWr_lr(generateRandomNumberFromTo(0, 90));
            rd.setWr_bf(generateRandomNumberFromTo(0, 90));

            rd.setSampling_rate(20);
            rd.setDataAccessToken(dat);
            rd.setUploadedOn(System.currentTimeMillis());

            robotDataService.save(rd);
        }
        return ResponseEntity.accepted().body(robotDataService.findAllByDataAccessToken(token));
    }

    /**
     * Retrieves all entries of user
     * @param token DAT of user
     * @return Returns all entries matching this token
     */
    @GetMapping(value = "/{token}/retrieve_all_data")
    public ResponseEntity<?> getAllEntries(@PathVariable String token){
        try {
            List<RobotData> robotDataList = robotDataService.findAllByDataAccessToken(token);
            robotDataService.deleteAllMatching(robotDataList);
            return ResponseEntity.accepted().body(robotDataList);
        }catch (NullPointerException npe){
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    /**
     * Retrieves the oldest database entry, matching parsed token
     * @param token DAT of user
     * @return Returns the retrieved entry, or an error
     */
    @GetMapping(value = "/{token}/retrieve_data")
    public ResponseEntity<?> getLatestRoboData(@PathVariable String token) {
        try {
            RobotData rdx = robotDataService.findTopByDataAccessToken(token);
            robotDataService.delete(rdx);
            return ResponseEntity.accepted().body(rdx);
        }catch (DataAccessResourceFailureException | NullPointerException accessExc) {
            return ResponseEntity.badRequest().body(new FlashMessage(accessExc.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    /**
     * Uploads a set of data with the parsed token
     * @param robotData Data to be uploaded
     * @param token DAT of user
     * @return Returns Success or Error Message
     */
    @PostMapping(value = "/{token}/upload_many_data")
    public ResponseEntity<?> insertManyData(@RequestBody Iterable<RobotData> robotData, @PathVariable String token){
        try {
            DataAccessToken dat = robotDataService.findDATByToken(token);
            robotData.forEach(p -> {
                p.setUploadedOn(System.currentTimeMillis());
                p.setDataAccessToken(dat);
            });
            robotDataService.saveAll(robotData);
            return ResponseEntity.accepted().body(new FlashMessage("Successfully inserted data list!", FlashMessage.Status.SUCCESS));
        } catch (NullPointerException npe) {
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    /**
     * Uploads a set of data with the parsed token
     * @param rawData Data to be uploaded - in raw format
     * @param token DAT of user
     * @return Returns Success or Error Message
     */
    @PostMapping(value = "/{token}/upload_many_raw_data")
    public ResponseEntity<?> insertManyRawData(@RequestBody List<Integer> rawData, @PathVariable String token){
        try {
            DataAccessToken dat = robotDataService.findDATByToken(token);
            List<RobotData> robotData = new Vector<>();
            for(int i = 0; i < rawData.size(); i+=22){
                RobotData temp = new RobotData();
                temp.setUploadedOn(System.currentTimeMillis());
                temp.setDataAccessToken(dat);

                temp.setP_tip(rawData.get(i));
                temp.setP_middle(rawData.get(i + 1));
                temp.setP_base(rawData.get(i + 2));
                temp.setP_base_rot(rawData.get(i + 3));

                temp.setRf_tip(rawData.get(i + 4));
                temp.setRf_middle(rawData.get(i + 5));
                temp.setRf_base(rawData.get(i + 6));
                temp.setRf_base_rot(rawData.get(i + 7));

                temp.setMf_tip(rawData.get(i + 8));
                temp.setMf_middle(rawData.get(i + 9));
                temp.setMf_base(rawData.get(i + 10));
                temp.setMf_base_rot(rawData.get(i + 11));

                temp.setIf_tip(rawData.get(i + 12));
                temp.setIf_middle(rawData.get(i + 13));
                temp.setIf_base(rawData.get(i + 14));
                temp.setIf_base_rot(rawData.get(i + 15));

                temp.setTh_tip(rawData.get(i + 16));
                temp.setTh_base(rawData.get(i + 17));
                temp.setTh_rot_orthogonal(rawData.get(i + 18));
                temp.setTh_rot_palm(rawData.get(i + 19));


                temp.setWr_bf(rawData.get(i + 20));
                temp.setWr_lr(rawData.get(i + 21));

                robotData.add(temp);
            }
            robotDataService.saveAll(robotData);
            return ResponseEntity.accepted().body(new FlashMessage("Successfully inserted data list!", FlashMessage.Status.SUCCESS));
        } catch (NullPointerException npe) {
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    /**
     * Inserts one single entry into the database with the parsed token
     * @param robotData Data to be inserted
     * @param token DAT of user
     * @return Returns the saved entity or an error message
     */
    @PostMapping(value = "/{token}/upload_data")
    public ResponseEntity<?> insertData(@RequestBody RobotData robotData, @PathVariable String token){
        try {
            robotData.setUploadedOn(System.currentTimeMillis());

            DataAccessToken dat = robotDataService.findDATByToken(token);
            robotData.setDataAccessToken(dat);

            return ResponseEntity.accepted().body(robotDataService.save(robotData));
        } catch (NullPointerException npe) {
            return ResponseEntity.badRequest().body(new FlashMessage(npe.getMessage(), FlashMessage.Status.FAILURE));
        }
    }

    /**
     * Deletes all entries in order to synchronize with the controller
     * @param token DAT of user
     * @return Returns success or error message
     */
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
