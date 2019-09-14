package com.kh.spring.notice.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.notice.model.dao.NoticeDao;
import com.kh.spring.notice.model.vo.Notice;
import com.kh.spring.notice.model.vo.Search;

@Service("nService")
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDao nDao;

	@Override
	public ArrayList<Notice> selectList() {
		return nDao.selectList();
	}

	@Override
	public int insertNotice(Notice notice) {
		
		// textarea의 개행문자를 <br>로 변경
		notice.setnContent(notice.getnContent().replace("\n", "<br>"));
		return nDao.insertNotice(notice);
	}

	@Override
	public Notice selectOne(int nId) {
		return nDao.selectOne(nId);
	}
	
	@Override
	public int updateNotice(Notice notice) {
		// textarea의 개행문자를 <br>로 변경
		notice.setnContent(notice.getnContent().replace("\n", "<br>"));
		return nDao.updateNotice(notice);
	}
	
	@Override
	public int deleteNotice(int nId) {
		return nDao.deleteNotice(nId);
	}

	@Override
	public ArrayList<Notice> searchList(Search search) {
		return nDao.searchList(search);
	}
	
}



