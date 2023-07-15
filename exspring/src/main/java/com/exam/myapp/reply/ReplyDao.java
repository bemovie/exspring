package com.exam.myapp.reply;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.myapp.bbs.BbsVo;

@Mapper
public interface ReplyDao {

	public int insertReply(ReplyVo vo);

	public List<ReplyVo> selectReplyList(int repBbsNo);

	public ReplyVo selectReply(int repNo);

	public int deleteReply(ReplyVo vo);

}
