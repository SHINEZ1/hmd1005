package com.kh.Ulsan_Hanmadang.domain.post.svc;

import com.kh.Ulsan_Hanmadang.domain.EventInfo;
import com.kh.Ulsan_Hanmadang.domain.FacInfo;
import com.kh.Ulsan_Hanmadang.domain.post.dao.Post;
import com.kh.Ulsan_Hanmadang.domain.post.dao.PostFilterCondition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostSVC {

  /**
   * 원글작성
   * @param post
   * @return 게시글 번호
   */
  Long saveOrigin(Post post);

  /**
   * 원글작성-첨부파일 있는경우
   * @param post
   * @param files 첨파일
   * @return 게시글 번호
   */
  Long saveOrigin(Post post, List<MultipartFile> files);

  /**
   * 목록
   * @return
   */
  List<Post> findAll();
  List<Post>  findAll(int startRec, int endRec);
  List<Post>  findAll(String category, int startRec, int endRec);
  /**
   * 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<Post>  findAll(PostFilterCondition filterCondition);

  List<EventInfo> findAllEvents(PostFilterCondition filterCondition);
  /**
   * 목록
   * @return 이벤트 정보
   */
  List<EventInfo> findAllEvents(int startRec, int endRec);
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
   * @param post 수정내용
   * @return 수정건수
   */
  int updateByPostId(Long id, Post post);

  /**
   * 수정-첨부
   * @param id 게시글 번호
   * @param post 수정내용
   * @param files 첨부파일
   * @return 수정건수
   */
  int updateByPostId(Long id, Post post, List<MultipartFile> files);

  /**
   * 답글작성
   * @param ppostId 부모글번호
   * @param replypost 답글
   * @return 답글번호
   */
  Long saveReply(Long ppostId, Post replypost);
  Long saveReply(Long ppostId, Post replypost, List<MultipartFile> files);
  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();
  int totalCount(String pcategory);
  int totalCount(PostFilterCondition filterCondition);
  int totalPEventCount();

  /**
   * 내가 쓴 글
   * @param email 아이디
   * @return
   */
  List<Post> myPost(String email);
}