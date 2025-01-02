package com.example.RestfulapiJSONDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Vue 개발 서버 주소
public class RestfulapiJsondbApplication {

	@Autowired
	private SamTableService dbmsService;
	
	public static void main(String[] args) {
		SpringApplication.run(RestfulapiJsondbApplication.class, args);
	}

	private List<Map<String, Object>> dataList = new ArrayList<>();
	


    // POST: 데이터 추가 ################
    @PostMapping("/data")
    public String createData(@RequestBody Map<String, String> request) {
        // ArrayList 메모리에 저장
    	// Map<String, Object> data = new HashMap<>();
        // int id = dataList.size() + 1;        
        // data.put("KeyNo", id);
        // data.put("Value", request.get("value"));
        // dataList.add(data);
        // return "Data created with ID: " + id + ", JSON : " + data.toString();
        
        // DB Table에 저장
        Integer  hviKeyno = dbmsService.getInsertKeyNo();
        String   hvsValue = request.get("value");
        int rowsAffected = dbmsService.addValue(hviKeyno, hvsValue);
        return rowsAffected > 0 ? "Data created with ID: " + hviKeyno + ", Value: " + hvsValue : "Value Insert Error" ;
    }

    // PUT: 데이터 업데이트
    @PutMapping("/data/{id}")
    public String updateData(@PathVariable int id, @RequestBody Map<String, String> request) {
    	// ArrayList 메모리 수정
    	//for (Map<String, Object> data : dataList) {
        //    if (data.get("KeyNo").equals(id)) {
        //        data.put("Value", request.get("value")); // 새로운 값으로 업데이트
        //        return "Data with ID " + id + " updated.";
        //    }
        //}
        //return "Data with ID " + id + " not found.";
    	
    	// DB Table에 데이터 수정
        Integer  hviKeyno = id;
        String   hvsValue = request.get("value");
        int rowsAffected = dbmsService.updateValue(hviKeyno, hvsValue);
        return rowsAffected > 0 ? "Data with ID " + hviKeyno.toString() + " updated." : "Data with ID " + hviKeyno.toString() + " not found.";
    }

    // DELETE: 데이터 삭제
    @DeleteMapping("/data/{id}")
    public String deleteData(@PathVariable int id) {
    	// ArrayList 메모리 삭제
    	//for (Map<String, Object> data : dataList) {
        //    if (data.get("KeyNo").equals(id)) {
        //    	dataList.remove(data); // 데이터 삭제
        //        return "Data with ID " + id + " deleted.";
        //    }
        //}
        //return "Data with ID " + id + " not found.";
    	
    	// DB Table에 데이터 삭제
        Integer  hviKeyno = id; 
        int rowsAffected = dbmsService.deleteValue(hviKeyno);
        return rowsAffected > 0 ? "Data with ID " + hviKeyno.toString() + " deleted." : "Data with ID " + hviKeyno.toString() + " not found.";
    }

    // GET: 데이터 조회 (테스트용)
    @GetMapping("/data/{id}")
    public String getData(@PathVariable int id) {
    	if (id == 0) {
    		// ArrayList 메모리 Value 전체 목록 리턴     		
            //try {
            //    // ObjectMapper 인스턴스 생성
            //    ObjectMapper objectMapper = new ObjectMapper();
            //    
            //    // Map을 JSON 문자열로 변환
            //    return objectMapper.writeValueAsString(dataList);
            //} catch (Exception e) {
            //    return e.getMessage();
            //}
    		
    		// DB Table에 데이터 조회 (All)
    		List<Map<String, Object>> temp = new ArrayList<>();   
            temp = dbmsService.getAllValue();
            try {
	            // ObjectMapper 인스턴스 생성
	            ObjectMapper objectMapper = new ObjectMapper();                
	            // Map을 JSON 문자열로 변환
	            return objectMapper.writeValueAsString(temp);
            } catch (Exception e) {
            	return e.getMessage();
            }
    	
    	} else {
    		// ArrayList 메모리 Value 조회 
        	//for (Map<String, Object> data : dataList) {
            //    if (data.get("KeyNo").equals(id)) { 
            //        return "Data :" + data.get("Value");
            //    }
            //}
            //return "Data with ID " + id + " not found.";
    		
    		// DB Table에 데이터 조회 (KeyNo 해당 Value)
    		List<Map<String, Object>> temp = new ArrayList<>();
            Integer  hviKeyno = id;  
            temp = dbmsService.getKeyNoValue(hviKeyno);
            if (temp.isEmpty()) { 
            	return "Data with ID " + id + " not found.";
            } else {
            	Map<String, Object> data = temp.get(0);             
            	return "Find Data :" + data.get("Value");
            }
    	}
    }
}
