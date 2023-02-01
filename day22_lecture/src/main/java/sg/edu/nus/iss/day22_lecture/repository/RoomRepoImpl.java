package sg.edu.nus.iss.day22_lecture.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day22_lecture.model.Room;

@Repository
public class RoomRepoImpl implements RoomRepo{

    @Autowired
    JdbcTemplate jdbcTemplate;

    String countSQL = "select count(*) from room";
    String selectSQL = "select * from room";
    String selectByIdSQL = "select * from room where id =?";
    String insertSQL = "insert into room (room_type, price) values (?,?)";
    String updateSQL = "update room set room_type = ?, price =? where id = ?";
    String deleteSQL = "delete from room where id = ?";

    @Override
    public int count() {
        Integer result = 0;
        //return class is Integer hence specify Integer.class
        result = jdbcTemplate.queryForObject(countSQL, Integer.class);
        
        //if result is null
        if (result == null) {
            return 0;
        }

        return result;
    }

    @Override
    public Boolean save(Room room) {
        Boolean saved = false;
        //.execute 'prepared statement callback'
        //expecting a return of Prepared Statement Call Back (IOU)
        //"insert into room (room_type, price) values (?,?)"
        jdbcTemplate.execute(insertSQL, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                //to set insertSQL parameters
                ps.setString(1, room.getRoomType());
                ps.setInt(2, room.getPrice());
                //run SQL, send to database values associated with insert statement
                Boolean r = ps.execute();
                return r;
            }
            
        });
        return saved;
    }

    @Override
    public List<Room> findAll() {
        //List<Room> rList = new ArrayList<>();
        //rList = jdbcTemplate.query(selectSQL, BeanPropertyRowMapper.newInstance(Room.class));
        //return rList;

        //BeanPropertyRowMapper automatically maps results to Room.class
        //"select * from room"
        return jdbcTemplate.query(selectSQL, BeanPropertyRowMapper.newInstance(Room.class));
    }

    @Override
    public Room findById(Integer id) {
        //"select * from room where id =?"
        return jdbcTemplate.queryForObject(selectByIdSQL, BeanPropertyRowMapper.newInstance(Room.class), id);
    }

    @Override
    public int update(Room room) {
        int updated = 0;

        //"update room set room_type = ?, price =? where id = ?"
        updated = jdbcTemplate.update(updateSQL, new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,room.getRoomType());
                ps.setInt(2, room.getPrice());
                ps.setInt(3, room.getId());
            }
            
        });
        return updated;
    }

    @Override
    public int deleteById(Integer id) {
        int deleted = 0;
        // "delete from room where id = ?"

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1,id);
            }
            
        };

        deleted = jdbcTemplate.update(deleteSQL, pss);
        

        return deleted;
    }
    
}
