package org.edupoll.controller.api;

import org.edupoll.model.dto.response.TestResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestTwoController {
	
	@GetMapping("/test-1")
	public TestResponseData test1Handle() {
		TestResponseData trd = new TestResponseData(3, "블라블라", null);
		return trd;
	}
	
	@GetMapping("/test-2")
	public String test2Handle() {
		return "moim/create";
	}
	
	@GetMapping("/test-3")
	public String test3Handle() {
		return "redirect:/moim";
	}
}
