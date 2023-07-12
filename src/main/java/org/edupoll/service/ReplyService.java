package org.edupoll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.Reply;
import org.edupoll.repository.ReplyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
	ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		super();
		this.replyRepository = replyRepository;
	}
	
	public void create(Reply reply) {
		replyRepository.save(reply);
	}
	
	public List<Reply> getReplys(String moimId, int page) {
		return replyRepository.findByMoimId(moimId, PageRequest.of(page-1, 10, Sort.by(Direction.ASC, "id")));
	}
	
	public List<String> getPages(String moimId, int page) {
		long cnt = replyRepository.countByMoimId(moimId, PageRequest.of(page-1, 10, Sort.by(Direction.ASC, "id")));
		int lastPage = (int)cnt / 10 + (cnt % 10 == 0? 0 : 1);
		List<String> pages = new ArrayList<>();
		int last = ((int)(Math.ceil(page/5)) + (page%5 == 0? 0 : 1)) * 5;
		int start = last - 4;
		for(int i=start; i<= (last > lastPage? lastPage:last); i++) {
			pages.add(String.valueOf(i));
		}
		return pages;
	}
	
	public boolean getNext(String moimId, int page) {
		long cnt = replyRepository.countByMoimId(moimId, PageRequest.of(page-1, 10, Sort.by(Direction.ASC, "id")));
		int lastPage = (int)cnt / 10 + (cnt % 10 == 0? 0 : 1);
		int last = ((int)(Math.ceil(page/5)) + (page%5 == 0? 0 : 1)) * 5;
		int start = last - 4;
		if (lastPage > last) {
			return true;
		}
		return false;
	}
}
