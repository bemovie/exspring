package com.exam.myapp.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.myapp.bbs.BbsDao;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired //spring에다가 여기에 맞는 객체 주입해달라고 요청
	private ReplyDao replyDao;
	
	@Override
	public int insertReply(ReplyVo vo) {
		return replyDao.insertReply(vo);
	}

	@Override
	public List<ReplyVo> selectReplyList(int repBbsNo) {
		return replyDao.selectReplyList(repBbsNo);
	}

	@Override
	public ReplyVo selectReply(int repNo) {
		return replyDao.selectReply(repNo);
	}

	@Override
	public int deleteReply(ReplyVo vo) {
		return replyDao.deleteReply(vo);
	}

}
