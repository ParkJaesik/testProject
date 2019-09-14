package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service("mService") // Service의 의미를 가지는 클래스라는 것을 명시하는 어노테이션
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao mDao;
	
	// 암호화를 위한 객체(DI(의존성 주입))
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Member loginMember(Member mem) {
		// 스프링에서는 service단에서 sqlSession을 생성하지 않고
		// dao에서 DI를 통해 sqlSession을 생성
		
		// 암호화 후 로그인 처리
		Member loginUser = mDao.selectMember(mem);
		
		if(!bCryptPasswordEncoder.matches(mem.getPwd(), loginUser.getPwd())) {
			loginUser = null;
		}
		
		return loginUser;
	}
	
	
	//Propagation.REQUIRED : 기본값,
	//	->앞서 생성된 트랜잭션이 존재하면 해당 트랜잭션에 참여
	//	  없다면 새로운 트랜잭션 생성
	//	Isolation.READ_COMMITTED : 커밋되지 않은 다른 트랜잭션 정보를 읽을 수 없음
	// 	Isolation.READ_UNCOMMITTED : 커밋되지않은 다른 트랜잭션 정보를 읽을 수 있음.
	// 트랜잭션 관리가 필요가 메소드에 적용시 자동으로 커밋 롤백등의 트랜잭션을 관리해준다.
	@Override
	@Transactional(propagation=Propagation.REQUIRED,
					isolation=Isolation.READ_COMMITTED,
					rollbackFor=Exception.class)
	public int insertMember(Member mem) {
		
		// 암호화된 비밀번호를 저장할 변수
		String encPwd = bCryptPasswordEncoder.encode(mem.getPwd());
		
		mem.setPwd(encPwd);
		
		return mDao.insertMember(mem);
	}

	@Override
	public int checkIdDup(String id) {
		return mDao.checkIdDup(id);
	}

	@Override
	public int updateMember(Member mem) {
		return mDao.updateMember(mem);
	}
	
	
	@Override
	public int deleteMember(String id) {
		return mDao.deleteMember(id);
	}
	
	
	
	
}
