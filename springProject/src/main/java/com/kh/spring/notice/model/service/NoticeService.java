package com.kh.spring.notice.model.service;

import java.util.ArrayList;

import com.kh.spring.notice.model.vo.Notice;
import com.kh.spring.notice.model.vo.Search;

public interface NoticeService {

	/** 공지사항 전체 조회 서비스
	 * @return list
	 */
	public abstract ArrayList<Notice> selectList();

	/** 공지사항 등록 서비스
	 * @param notice
	 * @return result
	 */
	public abstract int insertNotice(Notice notice);

	
	/** 공지사항 상세조회용 서비스
	 * @param nId
	 * @return notice
	 */
	public abstract Notice selectOne(int nId);

	
	
	/** 공지사항 수정용 서비스
	 * @param notice
	 * @return result
	 */
	public abstract int updateNotice(Notice notice);

	
	/** 공지사항 삭제용 서비스
	 * @param nId
	 * @return result
	 */
	public abstract int deleteNotice(int nId);

	/** 공지사항 검색용 서비스 
	 * @param search
	 * @return searchList
	 */
	public abstract ArrayList<Notice> searchList(Search search);
	
	
	
	
}
