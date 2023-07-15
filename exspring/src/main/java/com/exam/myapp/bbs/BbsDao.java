package com.exam.myapp.bbs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BbsDao {

	List<BbsVo> selectBbsList(SearchInfo info);

	int insertBbs(BbsVo vo);

	int deleteBbs(int memIdv);

	BbsVo selectBbs(int memId);

	int updateBbs(BbsVo vo);

	int selectBbsCount(SearchInfo info);
	
}