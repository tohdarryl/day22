package sg.edu.nus.iss.workshop22prac.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop22prac.model.RSVP;

@Repository
public class RsvpRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSQL = "select * from rsvp";
    private final String findByNameSQL = "select * from rsvp where f_name like '%' ? '%'";
    private final String insertSaveSQL = "insert into rsvp(f_name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";
    private final String updateSQL = "update rsvp set f_name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? where id = ?";
    private final String countSQL = "select count(*) from rsvp";
    private final String findByIdSQL = "select * from rsvp where id = ?";
    private final String findByEmailSQL = "select * from rsvp where email = ?";

    //Get all RSVPs from database
    public List<RSVP> findAll(){
        List<RSVP> rList = new LinkedList<>();
        rList = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
        return rList;
    }

    public List<RSVP> findByName(String name){
        List<RSVP> rList = new LinkedList<>();
        rList = jdbcTemplate.query(findByNameSQL, BeanPropertyRowMapper.newInstance(RSVP.class), name);
        return rList;
    }

    public Boolean saveRSVP(RSVP rsvp){
        Integer result = jdbcTemplate.update(insertSaveSQL, rsvp.getFName(), rsvp.getEmail(), rsvp.getPhone(), 
                                            rsvp.getConfirmationDate(), rsvp.getComments());
        return result > 0?true:false;
    }

    public Boolean updateRSVP(RSVP rsvp){
        Integer result = jdbcTemplate.update(updateSQL, rsvp.getFName(), rsvp.getEmail(), rsvp.getPhone(), 
                                            rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getId());
        return result > 0?true:false;
    }

    public Integer countAll(){
        Integer countAll = jdbcTemplate.queryForObject(countSQL, Integer.class);
        if (countAll == null){
            return 0;
        }
        
        return countAll;
    }

    public RSVP findById(Integer id){
        RSVP r = jdbcTemplate.queryForObject(findByIdSQL, BeanPropertyRowMapper.newInstance(RSVP.class), id);
        return r;
    }

    public RSVP findByEmail(String email){
        RSVP r = jdbcTemplate.queryForObject(findByEmailSQL, BeanPropertyRowMapper.newInstance(RSVP.class), email);
        return r;
    }
}
