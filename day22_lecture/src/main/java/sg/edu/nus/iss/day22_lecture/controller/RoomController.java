package sg.edu.nus.iss.day22_lecture.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.day22_lecture.model.Room;
import sg.edu.nus.iss.day22_lecture.service.RoomService;

@RestController
@RequestMapping ("/api/rooms")
public class RoomController {
    
    @Autowired
    RoomService roomSvc;

    //If Select, use GetMapping
    @GetMapping("count")
    public Integer getRoomCount(){
        
        Integer roomCount = roomSvc.count();
        return roomCount;
    }

    @GetMapping("/")
    public ResponseEntity<List<Room>> retrieveAllRooms(){

        List<Room>rooms = new ArrayList<>();
        rooms = roomSvc.findAll();

        if (rooms.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> retrieveRoomById (@PathVariable("id") int id){
        Room room = roomSvc.findById(id);

        if (room == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> createRoom(@RequestBody Room room){
        Room rm = room;
        Boolean result = roomSvc.save(rm);

        if(result) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Integer> updateRoom(@RequestBody Room room){
        Room rm = room;
        int updated = roomSvc.update(rm);

        if (updated == 1){
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRoom(@PathVariable("id") Integer id){
        int deleteResult = 0;

        deleteResult = roomSvc.deleteById(id);

        if (deleteResult == 0){
            return new ResponseEntity<>(0,HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(1,HttpStatus.OK);
        }
    }

}
