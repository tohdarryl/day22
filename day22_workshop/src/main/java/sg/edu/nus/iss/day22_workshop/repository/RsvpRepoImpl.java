package sg.edu.nus.iss.day22_workshop.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.day22_workshop.model.RSVP;

@Repository
public class RsvpRepoImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSQL = "select * from rsvp";
    // Must be '%' ? '%' for like
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

    //If any records fail, will rollback (retu)
    @Transactional
    public int[] batchInsert(List<RSVP> rsvp){
        return jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

            //rsvp.getFullName(), rsvp.getEmail(), 
            //rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments()
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, rsvp.get(i).getFullName());
                ps.setString(2, rsvp.get(i).getEmail());
                ps.setInt(3, rsvp.get(i).getPhone());
                ps.setDate(4, rsvp.get(i).getConfirmationDate());
                ps.setString(5, rsvp.get(i).getComments());

            }
            //[1,1,1,1,1]
            @Override
            public int getBatchSize() {
                return rsvp.size();
            }
            
        });
    }

    
}
