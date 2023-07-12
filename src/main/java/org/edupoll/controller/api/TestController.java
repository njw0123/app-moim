package org.edupoll.controller.api;

import java.util.List;

import org.edupoll.model.dto.response.TestResponseData;
import org.edupoll.model.entity.User;
import org.edupoll.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api")
public class TestController {
	ObjectMapper objectMapper;
	UserRepository userRepository;

	public TestController(ObjectMapper objectMapper, UserRepository userRepository) {
		super();
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
	}

	@GetMapping("/test-a")
	@ResponseBody
	public String testAHandle() throws JsonProcessingException {
		String[] ar = new String[] {"saito", "sadako", "goku"};
		String jsonStr = objectMapper.writeValueAsString(ar);
						// gson.toJson();
		System.out.println(jsonStr);
		return jsonStr;
	}
	
	@GetMapping("/test-b")
	@ResponseBody
	public String[] testBHandle() {
		String[] ar = new String[] {"saito", "sadako", "goku"};
		return ar;
	}
	
	@GetMapping("/test-c")
	@ResponseBody
	public TestResponseData testCHandle() {
		TestResponseData trd = new TestResponseData(2, "정상처리되었다", new String[] {"루피","조로"});
		return trd;
	}
	
	@GetMapping("/test-d")
	@ResponseBody
	public User testDHandle() {
		return userRepository.findById("jw").get();
	}
}
