package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	// Service Interface 사용 이유
	
	/* 1. 프로젝트에 규칙성을 부여하기 위해서
	 * 2. 클래스간의 결합도를 약화 시키기 위함 
	 *    --> 유지 보수를 위해서
	 * 3. Spring AOP를 위함
	 *   --> 최근엔 필요 없어졌지만
	 *       이전 버전과의 호환을 위해 사용
	 * */
	
	
	/** 회원 로그인 서비스를 위한 메소드
	 * @param mem
	 * @return loginUser
	 */
	public abstract Member loginMember(Member mem);

	
	/** 회원 가입 서비스를 위한 메소드
	 * @param mem
	 * @return result
	 */
	public abstract int insertMember(Member mem);


	/** 아이디 중복 검사
	 * @param id
	 * @return result
	 */
	public abstract int checkIdDup(String id);


	/**  회원 정보 수정 서비스를 위한 메소드
	 * @param mem
	 * @return result
	 */
	public abstract int updateMember(Member mem);
	
	
	
	/** 회원 탈퇴 서비스를 위한 메소드
	 * @param id
	 * @return result
	 */
	public abstract int deleteMember(String id);
	
	
	
	
	
}
