package sg.edu.nus.iss.day22_workshop.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.day22_workshop.model.RSVP;
import sg.edu.nus.iss.day22_workshop.repository.RsvpRepoImpl;

@RestController
@RequestMapping("api/rsvps")
public class RSVPController {
    
    @Autowired
    RsvpRepoImpl rsvpRepo;


    // @RequestMapping(path = "/" , method =RequestMethod.GET, produces = "application/json")
    // @GetMapping(path = "/", produces = "application/json")
    @GetMapping({"/"})
    // if you need Response Code, use responsentity
    public ResponseEntity<List<RSVP>> getAllRSVPS(){
        List<RSVP> listRSVP = new ArrayList<>();
        listRSVP = rsvpRepo.findAll();

        if (listRSVP.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listRSVP,HttpStatus.OK);
        }
        
    }

    //localhost:8080/api/rsvps?name=fred
    @GetMapping({""})
    // if you need Response Code, use responsentity
    public ResponseEntity<List<RSVP>> getRSVPByName(@RequestParam("name") String name){
        List<RSVP> listRSVP = new ArrayList<>();
        listRSVP = rsvpRepo.findByName(name);
        if (listRSVP.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listRSVP,HttpStatus.OK);
        }
        
    }

    @PostMapping("")
    public ResponseEntity<String> saveRSVP(@RequestBody RSVP rsvp) {
        try {
        RSVP r = rsvp;
        Boolean saved = rsvpRepo.save(r);

        if(saved){
            return new ResponseEntity<>("RSVP record created.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("RSVP record failed to create.", HttpStatus.NOT_ACCEPTABLE);
        }
    } catch (Exception ex) {
        return new ResponseEntity<>("Server failed to process saveRSVP", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRSVP(@PathVariable("id") Integer id, @RequestBody RSVP rsvp){
        
        RSVP r = rsvpRepo.findById(id);
        
        Boolean result = false;
        //if record exist
        if (r != null){
        result = rsvpRepo.update(rsvp);
        }

        if(result){
            return new ResponseEntity<>("RSVP record updated.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("RSVP record NOT updated.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getRSVPCount(){
        Integer rsvpCount = rsvpRepo.countAll();

        return new ResponseEntity<>(rsvpCount,HttpStatus.OK);
    }
}
