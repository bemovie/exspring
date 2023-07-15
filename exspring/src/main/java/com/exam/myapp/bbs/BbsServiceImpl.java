package com.exam.myapp.bbs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//싱글톤(Singleton) : 애플리케이션 전체에서 인스턴스를 1개만 생성하여 사용하는 객체

@Service //스프링이 자동으로 객체를 만들어서 container에 등록함
@Transactional //이 객체의 모든 메서드들을 각각 하나의 트랜잭션으로 정의
public class BbsServiceImpl implements BbsService {
//	private MemberDao memberDao = new MemberDaoBatis(); // MemberDaoBatis()는 MemberDao 구현체
	
	@Autowired //spring에다가 여기에 맞는 객체 주입해달라고 요청
	private BbsDao bbsDao;
//	private MemberDao memberDao = MemberDaoBatis.getInstance();
	
	@Autowired
	private AttachDao attachDao;
	
//	@Value("C:/Temp/upload")
	@Value("${bbs.upload.path}") //지정한 값을 스프링이 변수에 주입(저장)	
	private String uploadPath; //게시판 첨부파일 저장 위치
//	private String uploadPath = "C:/Temp/upload"; //게시판 첨부파일 저장 위치
	
	@PostConstruct //스프링이 현재 객체의 초기화 작업이 완료된 후 실행
	public void init( ) {
//	public BbsServiceImpl() {
		new File(uploadPath).mkdirs(); //uploadPath 디렉토리가 없으면 생성
	}
	
	@Override
	public List<BbsVo> selectBbsList(SearchInfo info) {
		return bbsDao.selectBbsList(info);
	}
	
//	A계좌에서 B계좌로 100원 보내기
//	A계좌 잔액이 100원 이상인지 확인 => SELECT => 성공
//	A계좌 잔액에서 100원 감소 => UPDATE => 성공
//	B계좌 잔액에서 100원 증가 => UPDATE => 실패하면,
//	db를 이걸 하기 전 상태로 돌려놔야 함
//	=> 3개를 하나의 트랜잭션(transaction)으로 묶음
//	=> 3개가 다 성공하면, commit, 3개 중 1개라도 실패하면 rollback으로 돌려놓음
	
//	@Transactional //이 메서드를 하나의 트랜잭션으로 정의
	@Override
	public int insertBbs(BbsVo vo) {
		int num = bbsDao.insertBbs(vo);
		
//		List<MultipartFile> bbsFilList = vo.getBbsFile();
		for (MultipartFile f : vo.getBbsFile()) {
			if (f.getSize() /*==0*/<=0) continue; //파일크기가 0인 경우, 저장하지 않음
			
			System.out.println( "파일명:" + f.getOriginalFilename() ); //용량하고, 이름 확인하고, 서버에 저장하는 코드
			System.out.println( "파일크기:" + f.getSize() ); //용량하고, 이름 확인하고, 서버에 저장하는 코드
			
			String fname = null; 
			File saveFile = null;
			
			do {
				fname = UUID.randomUUID().toString(); //중복될 확률이 극도로 낮은 랜덤 문자열 생성
//				File saveFile = new File("C:/Temp/upload/"+fname);
				saveFile = new File(uploadPath, fname);
			} while ( saveFile.exists() ); // 낮은 확률이겠지만, 존재하면 돌아가고, 존재안하면 빠져나옴
			
//			saveFile.exists(); // boolean => 존재하면 true, 존재안하면, false => while문에 넣어줌
			
			try {
				f.transferTo(saveFile); //파일 f의 내용을 saveFile에 복사(저장)
				
//				int a = 10/0; //트랜잭션 적용 전, 강제 오류 발생
				
				AttachVo attVo = new AttachVo();
				attVo.setAttBbsNo(vo.getBbsNo()); //3)첨부파일이 속한 게시글 번호
				attVo.setAttOrgName(f.getOriginalFilename()); //1)첨부 파일 원래 이름
				attVo.setAttNewName(fname);//2)첨부 파일 저장 이름
				
				attachDao.insertAttach(attVo);
				
			} catch (IllegalStateException | IOException e) {
//				e.printStackTrace(); //이렇게만 처리해버리면, 트랜잭션이 롤백이 안되니까,
				throw new RuntimeException(e); //첨부파일 저장 중 오류 발생시 트랜잭션이 롤백되도록
				//발생한 에러가 전파가 되도록 다시 던져야 함 => 메서드가 실패가 되도록
			}
		}
		
		return num;
	}

	@Override
	public int deleteBbs(BbsVo vo) {
		BbsVo bbsVo = bbsDao.selectBbs(vo.getBbsNo()); // 삭제할 게시글 정보 조회
		
		if( !bbsVo.getBbsWriter().equals( vo.getBbsWriter() ) ) {
			return 0;
		}
		
		for ( AttachVo attVo : bbsVo.getAttachList()) { //게시글의 첨부파일을 하나씩 꺼내서
			new File(uploadPath, attVo.getAttNewName()).delete(); //디스크에서 첨부파일 삭제
//			File file = new File(uploadPath, attVo.getAttNewName()); //디스크에서 첨부파일 삭제
//			file.delete();
			attachDao.deleteAttach( attVo.getAttNo() ); //테이블에서 첨부파일 삭제
		}
		
		return bbsDao.deleteBbs(vo.getBbsNo()); //테이블에서 게시글 삭제
	}

	@Override
	public BbsVo selectBbs(int bbsNo) {
		return bbsDao.selectBbs(bbsNo);
	}

	@Override
	public int updateBbs(BbsVo vo) {
		return bbsDao.updateBbs(vo);
	}

	@Override
	public AttachVo selectAttach(int attNo) {
		return attachDao.selectAttach(attNo);
	}

	@Override
	public File getAttachFile(AttachVo vo) {
		return new File(uploadPath, vo.getAttNewName()); //첨부파일이 들어있는 경로 위치 + 파일이름 반환
	}

	@Override
	public int selectBbsCount(SearchInfo info) {
		return bbsDao.selectBbsCount(info);
	}
	
	


}
