package com.kh.Ulsan_Hanmadang.domain.post.svc;

import com.kh.Ulsan_Hanmadang.domain.EventInfo;
import com.kh.Ulsan_Hanmadang.domain.FacInfo;
import com.kh.Ulsan_Hanmadang.domain.common.file.svc.UploadFileSVC;
import com.kh.Ulsan_Hanmadang.domain.post.dao.Post;
import com.kh.Ulsan_Hanmadang.domain.post.dao.PostDAO;
import com.kh.Ulsan_Hanmadang.domain.post.dao.PostFilterCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostSVCImpl implements PostSVC{

  private final PostDAO postDAO;
  private final UploadFileSVC uploadFileSVC;
  @Override
  public Long saveOrigin(Post post) {
    return postDAO.saveOrigin(post);
  }

  @Override
  public Long saveOrigin(Post post, List<MultipartFile> files) {
    //1)원글 저장
    Long postId = saveOrigin(post);

    //2)첨부 저장
    uploadFileSVC.addFile(post.getPcategory(),postId,files);

    return postId;
  }
  //목록
  @Override
  public List<Post> findAll() {
    return postDAO.findAll();
  }

  @Override
  public List<Post> findAll(int startRec, int endRec) {
    return postDAO.findAll(startRec, endRec);
  }

  @Override
  public List<Post> findAll(String category, int startRec, int endRec) {
    return postDAO.findAll(category, startRec, endRec);
  }

  @Override
  public List<Post> findAll(PostFilterCondition filterCondition) {
    return postDAO.findAll(filterCondition);
  }

  @Override
  public List<EventInfo> findAllEvents(PostFilterCondition filterCondition) {
    return postDAO.findAllEvents(filterCondition);
  }

  @Override
  public List<EventInfo> findAllEvents(int startRec, int endRec) {
    return postDAO.findAllEvents(startRec, endRec);
  }
  //상세조회
  @Override
  public Post findByPostId(Long id) {
    Post findedItem = postDAO.findByPostId(id);
    postDAO.increaseHitCount(id);
    return findedItem;
  }

  @Override
  public EventInfo findByEventId(Long id) {
    EventInfo finedEvent = postDAO.findByEventId(id);
    postDAO.increaseHitCount(id);
    return finedEvent;
  }

  @Override
  public FacInfo findByFacId(String id) {
    FacInfo finedFac = postDAO.findByFacId(id);
    return finedFac;
  }
  //삭제
  @Override
  public int deleteByPostId(Long id) {
    //1)첨부파일 삭제
    String bcategory = postDAO.findByPostId(id).getPcategory();
    uploadFileSVC.deleteFileByCodeWithRid(bcategory, id);

    //2)게시글 삭제
    int affectedRow =  postDAO.deleteByPostId(id);

    return affectedRow;
  }
  //수정
  @Override
  public int updateByPostId(Long id, Post post) {
    return postDAO.updateByPostId(id, post);
  }
  //수정-첨부파일
  @Override
  public int updateByPostId(Long id, Post post, List<MultipartFile> files) {
    //1)수정
    int affectedRow = updateByPostId(id, post);
    //2)첨부 저장
    uploadFileSVC.addFile(post.getPcategory(),id,files);
    return affectedRow;
  }
  //답글
  @Override
  public Long saveReply(Long postId, Post replyPost) {
    return postDAO.saveReply(postId, replyPost);
  }

  @Override
  public Long saveReply(Long ppostId, Post replyPost, List<MultipartFile> files) {
    //1)답글 작성
    Long postId = postDAO.saveReply(ppostId, replyPost);
    //2)첨부 저장
    uploadFileSVC.addFile(replyPost.getPcategory(), ppostId, files);
    return postId;
  }
  //전체건수
  @Override
  public int totalCount() {
    return postDAO.totalCount();
  }

  @Override
  public int totalCount(String pcategory) {
    return postDAO.totalCount(pcategory);
  }

  @Override
  public int totalCount(PostFilterCondition filterCondition) {
    return postDAO.totalCount(filterCondition);
  }

  @Override
  public int totalPEventCount() {
    return postDAO.totalPEventCount();
  }

  /**
   * 내가 쓴 글
   * @param email 아이디
   * @return
   */
  @Override
  public List<Post> myPost(String email) {
    return postDAO.myPost(email);
  }
}
