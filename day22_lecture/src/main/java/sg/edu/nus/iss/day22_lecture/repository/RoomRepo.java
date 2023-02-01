package sg.edu.nus.iss.day22_lecture.repository;

import java.util.List;



import sg.edu.nus.iss.day22_lecture.model.Room;


public interface RoomRepo {
    int count();

    //create
    Boolean save(Room room);

    //read all
    List<Room> findAll();

    //read one record
    Room findById(Integer id);

    //update
    int update(Room room);

    //delete
    int deleteById(Integer id);
}
