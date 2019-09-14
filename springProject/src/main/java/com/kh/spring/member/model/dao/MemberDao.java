package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository("mDao")
public class MemberDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	public Member selectMember(Member mem) {
		Member loginUser 
			= sqlSession.selectOne("memberMapper.selectOne", mem);
		return loginUser;
	}


	public int insertMember(Member mem) {
		return sqlSession.insert("memberMapper.insertMember", mem);
	}


	public int checkIdDup(String id) {
		return sqlSession.selectOne("memberMapper.checkIdDup",id);
	}


	public int updateMember(Member mem) {
		return sqlSession.update("memberMapper.updateMember", mem);
	}

	
	public int deleteMember(String id) {
		return sqlSession.delete("memberMapper.deleteMember", id);
	}
	
	
	
	
}
