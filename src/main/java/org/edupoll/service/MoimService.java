package org.edupoll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.edupoll.model.dto.request.MoimResponseData;
import org.edupoll.model.dto.response.PageItem;
import org.edupoll.model.dto.response.Pagination;
import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.User;
import org.edupoll.repository.MoimRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class MoimService {
	MoimRepository moimRepository;
	
	@Value("${config.moim.pageSize}")
	int pageSize;

	public MoimService(MoimRepository moimRepository) {
		super();
		this.moimRepository = moimRepository;
	}
	
	public void create(User loginId, Moim moim) {
		moim.setUser(loginId);
		moimRepository.save(moim);
	}
	
	public List<MoimResponseData> findByAll(int p) {
		List<Moim> moim = moimRepository.findByTargetDateAfter(new Date(), PageRequest.of(p-1, pageSize, Sort.by(Direction.ASC, "targetDate")));
		return moim.stream()
				.map(t -> new MoimResponseData(t))
				.collect(Collectors.toList());
//		return moimRepository.findByTargetDateAfter(new Date(), PageRequest.of(p-1, 12, Sort.by(Direction.ASC, "targetDate")));
	}
	
	public List<String> getPages(int p) {
		long cnt = moimRepository.countByTargetDateAfter(new Date(), PageRequest.of(p-1, pageSize, Sort.by(Direction.ASC, "targetDate")));
		int lastPage = (int)cnt / pageSize + (cnt % pageSize == 0? 0 : 1);
		List<String> pages = new ArrayList<>();
		int last = ((int)(Math.ceil(p/5)) + (p%5 == 0? 0 : 1)) * 5;
		int start = last - 4;
		for(int i=start; i<= (last > lastPage? lastPage:last); i++) {
			pages.add(String.valueOf(i));
		}
		return pages;
	}
	
	public boolean getNext(int p) {
		long cnt = moimRepository.countByTargetDateAfter(new Date(), PageRequest.of(p-1, pageSize, Sort.by(Direction.ASC, "targetDate")));
		int lastPage = (int)cnt / pageSize + (cnt % pageSize == 0? 0 : 1);
		int last = ((int)(Math.ceil(p/5)) + (p%5 == 0? 0 : 1)) * 5;
		int start = last - 4;
		if (lastPage > last) {
			return true;
		}
		return false;
	}
	
	public Moim findById(String id) {
		return moimRepository.findById(id).get();
	}
	
	// 페이지 정보 만들어주는 서비스 메서드
		public Pagination createPagination(int currentValue) {
			// 페이지 분량은 어떻게 설정하고.. 특정페이지에 active 처리할려면..?
			long cnt = moimRepository.countByTargetDateAfter(new Date(), PageRequest.of(currentValue-1, pageSize, Sort.by(Direction.ASC, "targetDate")));
			long last = cnt / pageSize + (cnt%pageSize > 0 ? 1: 0);
			List<PageItem> li = new ArrayList<>();
			
			long endValue = (long)(Math.ceil(currentValue/5.0) * 5);
			long startValue = endValue-4;
			
			if(endValue > last) {
				endValue = last;
			}
			for(long i=startValue; i<=endValue; i++) {
				li.add(new PageItem(i, i == currentValue));
			}
			PageItem prev = (startValue!=1) ? new PageItem(startValue-1, true) : new PageItem(startValue, false) ;
			PageItem next = (endValue < last) ? new PageItem( endValue+1, true) : new PageItem(endValue, false); 
			
			Pagination pagination = new Pagination(prev, next, li);	
			
			return pagination;
		}
}
