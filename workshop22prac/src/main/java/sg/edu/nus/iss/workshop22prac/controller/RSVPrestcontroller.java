package sg.edu.nus.iss.workshop22prac.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.workshop22prac.model.RSVP;
import sg.edu.nus.iss.workshop22prac.repository.RsvpRepo;


@RestController
@RequestMapping ("api/rsvps")
public class RSVPrestcontroller {
    @Autowired
    RsvpRepo rsvpRepo;


    @GetMapping("/")
    public ResponseEntity<List<RSVP>> getAllRSVPS(){
        List<RSVP> rlist = rsvpRepo.findAll();

        if (rlist.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(rlist, HttpStatus.OK);
        }
    }
    //localhost:8080/api/rsvps?name=""
    @GetMapping("")
    public ResponseEntity<List<RSVP>> getRSVPByName(@RequestParam("name") String name){
        List<RSVP> rlist = rsvpRepo.findByName(name);

        if (rlist.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(rlist, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> saveRSVP(@RequestBody RSVP rsvp){
        RSVP r = rsvp;
        Boolean saved = rsvpRepo.saveRSVP(r);

        if(saved){
            return new ResponseEntity<>("Saved", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to save", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<String> updateRSVP(@PathVariable("id") Integer id, @RequestBody RSVP rsvp){
    //     RSVP r = rsvpRepo.findById(id);

    //     Boolean result = false;
    //     if (r != null){
    //         result = rsvpRepo.updateRSVP(rsvp);
    //     }
        
    //     if(result){
    //         return new ResponseEntity<>("Updated", HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>("Not updated", HttpStatus.NOT_ACCEPTABLE);
    //     }
    // }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateRSVP(@PathVariable("email") String email, @RequestBody RSVP rsvp){
        RSVP r = rsvpRepo.findByEmail(email);

        Boolean result = false;
        if (r != null){
            result = rsvpRepo.updateRSVP(rsvp);
        }
        
        if(result){
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not updated", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getRSVPCount(){
        Integer rsvpCount = rsvpRepo.countAll();

        return new ResponseEntity<>(rsvpCount,HttpStatus.OK);
    }

}
