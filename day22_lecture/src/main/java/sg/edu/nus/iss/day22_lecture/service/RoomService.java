package sg.edu.nus.iss.day22_lecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day22_lecture.model.Room;
import sg.edu.nus.iss.day22_lecture.repository.RoomRepoImpl;

@Service
public class RoomService{
    
    //Can access RoomRepoImpl through RoomRepo
    //@Autowired
    //RoomRepo roomRepo; works too
    @Autowired
    RoomRepoImpl roomRepo;

    
    public int count() {
      
        return roomRepo.count();
    }

    public Boolean save(Room room) {
        
        return roomRepo.save(room);
    }


    public List<Room> findAll() {

        return roomRepo.findAll();
    }


    public Room findById(Integer id) {

        return roomRepo.findById(id);
    }

    public int update(Room room) {

        return roomRepo.update(room);
    }

    
    public int deleteById(Integer id) {
        
        return roomRepo.deleteById(id);
    }


}
