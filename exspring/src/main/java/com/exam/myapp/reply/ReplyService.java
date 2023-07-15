package com.exam.myapp.reply;

import java.util.List;

public interface ReplyService {

	public int insertReply(ReplyVo vo);

	public List<ReplyVo> selectReplyList(int repBbsNo);

	public ReplyVo selectReply(int repNo);

	public int deleteReply(ReplyVo vo);

}
