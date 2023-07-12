package org.edupoll.model.dto.response;

import java.util.List;

public class Pagination {
	PageItem prev;
	PageItem next;
	List<PageItem> pages;

	public PageItem getPrev() {
		return prev;
	}

	public void setPrev(PageItem prev) {
		this.prev = prev;
	}

	public PageItem getNext() {
		return next;
	}

	public void setNext(PageItem next) {
		this.next = next;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public void setPages(List<PageItem> pages) {
		this.pages = pages;
	}

	public Pagination(PageItem prev, PageItem next, List<PageItem> pages) {
		super();
		this.prev = prev;
		this.next = next;
		this.pages = pages;
	}

}
