package org.edupoll.controller;

import org.edupoll.securty.support.Account;
import org.edupoll.service.SearchService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
	SearchService searchService;
	
	public SearchController(SearchService searchService) {
		super();
		this.searchService = searchService;
	}

	@GetMapping("/search")
	public String searchHandle(@AuthenticationPrincipal Account account, String q, Model model) {
		if (q == null || q.equals("")) {
			return "search/form";
		}
		if (account != null) {
			model.addAttribute("result", searchService.isFollow(account.getUsername(), searchService.getUsersMatchedQuery(q)));			
			model.addAttribute("login", true);
		}else {
			model.addAttribute("result", searchService.getUsersMatchedQuery(q));
		}
		model.addAttribute("query", q);			
		return "search/result";
	}
}
