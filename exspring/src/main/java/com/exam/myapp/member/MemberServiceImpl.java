package com.exam.myapp.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//싱글톤(Singleton) : 애플리케이션 전체에서 인스턴스를 1개만 생성하여 사용하는 객체

@Service //스프링이 자동으로 객체를 만들어서 container에 등록함
public class MemberServiceImpl implements MemberService {
//	private MemberDao memberDao = new MemberDaoBatis(); // MemberDaoBatis()는 MemberDao 구현체
	
	@Autowired //spring에다가 여기에 맞는 객체 주입해달라고 요청
	private MemberDao memberDao;
//	private MemberDao memberDao = MemberDaoBatis.getInstance();

	
	@Override
	public List<MemberVo> selectMemberList() {
		return memberDao.selectMemberList();
	}

	@Override
	public int insertMember(MemberVo vo) {
		return memberDao.insertMember(vo);
	}

	@Override
	public int deleteMember(String memIdv) {
		return memberDao.deleteMember(memIdv);
	}

	@Override
	public MemberVo selectMember(String memId) {
		return memberDao.selectMember(memId);
	}

	@Override
	public int updateMember(MemberVo vo) {
		return memberDao.updateMember(vo);
	}

	@Override
	public MemberVo selectLogin(MemberVo vo) {
		return memberDao.selectLogin(vo);
	}

}
