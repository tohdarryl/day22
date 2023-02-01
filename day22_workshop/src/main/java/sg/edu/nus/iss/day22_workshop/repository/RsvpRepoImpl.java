package sg.edu.nus.iss.day22_workshop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day22_workshop.model.RSVP;

@Repository
public class RsvpRepoImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSQL = "select * from rsvp";

    private final String findByNameSQL = "select * from rsvp where full_name like '%' ? '%'";

    private final String insertSQL = "insert into rsvp(full_name, email, phone, confirmation_date, comments) " +
                        "values(?, ?, ?, ?, ?)";

    private final String updateSQL = "update rsvp " +
                        "set full_name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? " +
                        "where id = ?";

    private final String countSQL = "select count(*) as cnt from rsvp";

    private final String findByIdSQL = "select * from rsvp where id = ?";


    public RSVP findById(Integer id ){
        return jdbcTemplate.queryForObject(findByIdSQL, 
        BeanPropertyRowMapper.newInstance(RSVP.class),id);
    }

    public List<RSVP> findAll(){
        // .query returns a list of RSVP objects
        // .queryForObject returns 1 RSVP object
        // BeanPropertyRowMapper provides auto-mapping
        List<RSVP> rList = new ArrayList<>();
        rList = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
        return rList;
    }

    public List<RSVP> findByName(String name){
        List<RSVP> rList = new ArrayList<>();
        rList = jdbcTemplate.query(findByNameSQL,
        BeanPropertyRowMapper.newInstance(RSVP.class), name);
        return rList;
    }

    public Boolean save(RSVP rsvp){
        Integer result = jdbcTemplate.update(insertSQL, rsvp.getFullName(), rsvp.getEmail(), 
                            rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());
        // if result > 0, true. Else false.
        return result>0?true:false;
    }

    public Boolean update(RSVP rsvp){
        Integer result = jdbcTemplate.update(updateSQL, rsvp.getFullName(), rsvp.getEmail(), 
        rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getId());

        return result>0?true:false;
    }

    public Integer countAll(){
        Integer result = 0;
        //return class is Integer hence specify Integer.class
        result = jdbcTemplate.queryForObject(countSQL, Integer.class);
        
        //if result is null
        if (result == null) {
            return 0;
        }

        return result;
    }



    
}
