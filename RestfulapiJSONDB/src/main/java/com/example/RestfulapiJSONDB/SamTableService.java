package com.example.RestfulapiJSONDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SamTableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 입력가능 KeyNO 조회(Max +1)
    public Integer getInsertKeyNo() {
    	List<Map<String, Object>> temp = new ArrayList<>();
        String sql = "SELECT max(keyno)+1 as next_keyno FROM sam_table";
        temp = jdbcTemplate.queryForList(sql);
        
        if (temp.get(0).get("next_keyno") == null) {
        	return 1;
        }else {
	        // Object nextKeyNo = temp.get(0).get("next_keyno");
	        // System.out.println("Type of next_keyno: " + nextKeyNo.getClass().getName());	        
	        return ((Long) temp.get(0).get("next_keyno")).intValue();
        }
    }
    
    // 모든 Value 조회
    public List<Map<String, Object>> getAllValue() {
        String sql = "SELECT * FROM sam_table";
        return jdbcTemplate.queryForList(sql);
    }

    // KeyNo로 Value 조회
    public List<Map<String, Object>> getKeyNoValue(Integer keyno) {
        String sql = "SELECT value FROM sam_table WHERE keyno = ?";
        return jdbcTemplate.queryForList(sql, keyno);
    }
    
    // 신규 KeyNo, Value 추가
    public int addValue(Integer keyno, String value) {
        String sql = "INSERT INTO sam_table (keyno, value) VALUES (?, ?)";
        return jdbcTemplate.update(sql, keyno, value);
    }

    // KeyNo 해당 Value 업데이트
    public int updateValue(Integer keyno, String value ) {
        String sql = "UPDATE sam_table SET value = ? WHERE keyno = ?";
        return jdbcTemplate.update(sql, value, keyno);
    }

    // KeyNo 해당 Row 삭제
    public int deleteValue(Integer keyno) {
        String sql = "DELETE FROM sam_table WHERE keyno = ?";
        return jdbcTemplate.update(sql, keyno);
    }
}
