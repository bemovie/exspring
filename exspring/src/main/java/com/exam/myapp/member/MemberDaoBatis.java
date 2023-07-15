package com.exam.myapp.member;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import com.exam.comm.MyBatisUtils;

//@Repository //이게 Dao임
public class MemberDaoBatis implements MemberDao{
	
//	@Autowired //직접 받는게 아니라 spring을 통해서 주입
//	private SqlSessionFactory sqlSessionFactory; //SqlSessionFactory cannot be resolved to a type => 라이브러리가 없어서 빨간줄
//	private SqlSessionFactory sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
	
	@Autowired
	private SqlSession session; 
	
	@Override
	public List<MemberVo> selectMemberList() {
		
		return session.selectList("com.exam.myapp.member.MemberDao.selectMemberList");
	}
	
//		List<MemberVo> list = null; //new ArrayList<MemberVo>(); <<이렇게 초기값을 줘도 된다
//		list = session.selectList("com.exam.member.MemberDao.selectMemberList");
//		return list;	
//	}
		
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			//실행할 SQL문과 동일한 이름의 메서드를 사용하여 SQL문 실행
//			//SELECT결과가 1행인 경우(result값이 하나) selectOne, 2행이상인 경우(list값으로) selectList 메서드 사용 
//			//첫번째 인자로 실행할 SQL문의 고유한 이름을 지정
//			//두번째 인자로 SQL문 실행시 필요한 데이터(를 담은 객체)를 전달
//			list = session.selectList("com.exam.member.MemberDao.selectMemberList");
//			}
//		
//		return list;
//	}

	@Override
	public int insertMember(MemberVo vo) {

		return session.insert("com.exam.myapp.member.MemberDao.insertMember", vo);
	}
	
//		int num = 0;
//		num = session.insert("com.exam.member.MemberDao.insertMember", vo);
//		return num;
//	}
	
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			num = session.insert("com.exam.member.MemberDao.insertMember", vo);
//			session.commit(); //INSERT,UPDATE,DELETE 후에는 commit 필요
//			}
//		
//		return num;
//	}

	//삭제버튼을 클릭하면,
	//삭제가 되도록 MemberDaoBatis 클래스와 MemberMapper.xml 파일을 변경하세요.
	
	@Override
	public int deleteMember(String memIdv) {
		
		return session.delete("com.exam.myapp.member.MemberDao.deleteMember", memIdv);
		
	}
	
//		int num = 0;
//		num = session.delete("com.exam.member.MemberDao.deleteMember", memIdv);
//		return num;
//	}
		
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			num = session.delete("com.exam.member.MemberDao.deleteMember", memIdv);
//			session.commit(); //INSERT,UPDATE,DELETE 후에는 commit 필요
//			}
//		
//		return num;
//	}

	@Override
	public MemberVo selectMember(String memId) {
	
		return session.selectOne("com.exam.myapp.member.MemberDao.selectMember", memId);
	}
		
//		MemberVo vo = null; //new ArrayList<MemberVo>(); <<이렇게 초기값을 줘도 된다
//		vo = session.selectOne("com.exam.member.MemberDao.selectMember", memId);
//		return vo;
//	}
		
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			//실행할 SQL문과 동일한 이름의 메서드를 사용하여 SQL문 실행
//			//SELECT결과가 1행인 경우(result값이 하나) selectOne, 2행이상인 경우(list값으로) selectList 메서드 사용 
//			//첫번째 인자로 실행할 SQL문의 고유한 이름을 지정
//			//두번째 인자로 SQL문 실행시 필요한 데이터(를 담은 객체)를 전달
//			vo = session.selectOne("com.exam.member.MemberDao.selectMember", memId);
//			}
//		
//		return vo;
//
//	}

	@Override
	public int updateMember(MemberVo vo) {
		// 데이터베이스에 회원정보가 변경되도록 구현
		
		return session.update("com.exam.myapp.member.MemberDao.updateMember", vo);
	}
		
//		int num = 0;
//		num = session.update("com.exam.member.MemberDao.updateMember", vo);
//		return num;
//		}
		
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			num = session.update("com.exam.member.MemberDao.updateMember", vo);
//			session.commit(); //INSERT,UPDATE,DELETE 후에는 commit 필요
//			}
//		
//		return num;
//	}

	@Override
	public MemberVo selectLogin(MemberVo mvo) {
		// vo 에 들어있는 아이디, 비밀번호가 일치하는 회원정보를
		// 데이터베이스에서 SELECT 하여 반환하도록 구현
		
		return session.selectOne("com.exam.myapp.member.MemberDao.selectLogin", mvo);
	}
		
//		MemberVo vo = null;
//		vo = session.selectOne("com.exam.member.MemberDao.selectLogin", mvo);
//		return vo;
//		}
		
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			//실행할 SQL문과 동일한 이름의 메서드를 사용하여 SQL문 실행
//			//SELECT결과가 1행인 경우(result값이 하나) selectOne, 2행이상인 경우(list값으로) selectList 메서드 사용 
//			//첫번째 인자로 실행할 SQL문의 고유한 이름을 지정
//			//두번째 인자로 SQL문 실행시 필요한 데이터(를 담은 객체)를 전달
//			vo = session.selectOne("com.exam.member.MemberDao.selectLogin", mvo);
//			}
//		
//		return vo;
//	}

	
//	public List<MemberVo> selectListMember() {
//	}
//	
//	public List<MemberVo> selectMemList() {
//	}
//	
//	memberDao.selectMemberList();
//	
//	새로운 걸 만드는 사람이 만들었다. 메서드 이름들이 다 바껴야함
//	뭘 가지고 구현하던간에 메서드 이름을 고치지 않고 강제할 수 있다
//	이름에 대한 규칙을 정의 = 인터페이스
	
		
}
