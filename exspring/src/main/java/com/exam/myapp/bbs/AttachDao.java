package com.exam.myapp.bbs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachDao {

//	List<BbsVo> selectBbsList();

	int insertAttach(AttachVo vo);

	AttachVo selectAttach(int attNo);

	int deleteAttach(int attNo);
	
//	int deleteBbs(int memIdv);

//	BbsVo selectBbs(int memId);

//	int updateBbs(BbsVo vo);
	
}