package com.kh.Ulsan_Hanmadang.domain.post.dao;

import com.kh.Ulsan_Hanmadang.domain.EventInfo;
import com.kh.Ulsan_Hanmadang.domain.FacInfo;

import java.util.List;

public interface PostDAO {
  /**
   * 원글작성
   * @param post
   * @return 게시글 번호
   */
  Long saveOrigin(Post post);

  /**
   * 목록
   * @return
   */
  List<Post> findAll();
  List<Post>  findAll(String category);

  List<Post>  findAll(int startRec, int endRec);
  List<Post>  findAll(String category, int startRec, int endRec);
  List<EventInfo> findAllEvents(int startRec, int endRec);
  /**
   * 홍보,후기 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<Post>  findAll(PostFilterCondition filterCondition);

  /**
   * 이벤트검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<EventInfo> findAllEvents(PostFilterCondition filterCondition);

  /**
   * 상세 조회
   * @param id 게시글번호
   * @return
   */
  Post findByPostId(Long id);

  EventInfo findByEventId(Long id);

  FacInfo findByFacId(String id);
  /**
   * 삭제
   * @param id 게시글번호
   * @return 삭제건수
   */
  int deleteByPostId(Long id);

  /**
   * 수정
   * @param id 게시글 번호
   * @param bbs 수정내용
   * @return 수정건수
   */
  int updateByPostId(Long id, Post bbs);


  String facilityLink(String facid);
  /**
   * 답글작성
   * @param pbbsId 부모글번호
   * @param replyPost 답글
   * @return 답글번호
   */
  Long saveReply(Long pbbsId, Post replyPost);

  /**
   * 조회수증가
   * @param id 게시글번호
   * @return  수정건수
   */
  int increaseHitCount(Long id);

  /**
   * 좋아요증감
   * @param id 게시글번호
   * @return 수정건수
   */
  int increaseGoodCount(Long id);
  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();
  int totalCount(String pcategory);
  int totalCount(PostFilterCondition filterCondition);

  int totalEventCount(PostFilterCondition filterCondition);

  /**
   * 전체건수
   * @return 등록 이벤트 건수
   */
  int totalPEventCount();

  /**
   * 내가 쓴 글
   * @param email 아이디
   * @return
   */
  List<Post> myPost(String email);
}
