package com.kh.Ulsan_Hanmadang.domain.post.dao;

import com.kh.Ulsan_Hanmadang.domain.EventInfo;
import com.kh.Ulsan_Hanmadang.domain.FacInfo;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@ToString
@Slf4j
@Repository
@RequiredArgsConstructor
public class PostDAOImpl implements PostDAO{

  private final JdbcTemplate jt;

  //원글작성
  @Override
  public Long saveOrigin(Post post) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into post (post_id,pcategory,title,email,nickname,pcontent,pgroup) ");
    sql.append("values(post_post_id_seq.nextval,?,?,?,?,?,post_post_id_seq.currval) ");


    KeyHolder keyHolder = new GeneratedKeyHolder();
    jt.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"post_id"});
        pstmt.setString(1,post.getPcategory());
        pstmt.setString(2, post.getTitle());
        pstmt.setString(3, post.getEmail());
        pstmt.setString(4, post.getNickname());
        pstmt.setString(5, post.getPcontent());
        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("post_id").toString());
  }

  //목록
  @Override
  public List<Post> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    sql.append("  post_id, ");
    sql.append("  pcategory, ");
    sql.append("  title, ");
    sql.append("  email, ");
    sql.append("  nickname, ");
    sql.append("  hit, ");
    sql.append("  pcontent, ");
    sql.append("  ppost_id, ");
    sql.append("  pgroup, ");
    sql.append("  step, ");
    sql.append("  pindent, ");
    sql.append("  status, ");
    sql.append("  cdate, ");
    sql.append("  udate ");
    sql.append("FROM ");
    sql.append("  post ");
    sql.append("Order by pgroup desc, step asc ");

    List<Post> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Post.class));

    return list;
  }

  //카테고리별 목록
  @Override
  public List<Post> findAll(String category) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    sql.append("  post_id, ");
    sql.append("  pcategory, ");
    sql.append("  title, ");
    sql.append("  email, ");
    sql.append("  nickname, ");
    sql.append("  hit, ");
    sql.append("  pcontent, ");
    sql.append("  ppost_id, ");
    sql.append("  pgroup, ");
    sql.append("  step, ");
    sql.append("  pindent, ");
    sql.append("  status, ");
    sql.append("  cdate, ");
    sql.append("  udate ");
    sql.append("FROM ");
    sql.append("  post ");
    sql.append("WHERE pcategory = ? ");
    sql.append("Order by pgroup desc, step asc ");

    List<Post> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Post.class),category);

    return list;
  }

  @Override
  public List<Post> findAll(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT ");
    sql.append("    ROW_NUMBER() OVER (ORDER BY pgroup DESC, step ASC) no, ");
    sql.append("    post_id, ");
    sql.append("    pcategory, ");
    sql.append("    title, ");
    sql.append("    email, ");
    sql.append("    nickname, ");
    sql.append("    hit, ");
    sql.append("    pcontent, ");
    sql.append("    ppost_id, ");
    sql.append("    pgroup, ");
    sql.append("    step, ");
    sql.append("    pindent, ");
    sql.append("    status, ");
    sql.append("    cdate, ");
    sql.append("    udate ");
    sql.append("    FROM post) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Post> list = jt.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(Post.class),
            startRec, endRec
    );
    return list;
  }

  @Override
  public List<Post> findAll(String category, int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT ");
    sql.append("      ROW_NUMBER() OVER (ORDER BY pgroup DESC, step ASC) no, ");
    sql.append("      post_id, ");
    sql.append("      pcategory, ");
    sql.append("      title, ");
    sql.append("      email, ");
    sql.append("      nickname, ");
    sql.append("      hit, ");
    sql.append("      pcontent, ");
    sql.append("      ppost_id, ");
    sql.append("      pgroup, ");
    sql.append("      step, ");
    sql.append("      pindent, ");
    sql.append("      status, ");
    sql.append("      cdate, ");
    sql.append("      udate ");
    sql.append("    FROM post ");
    sql.append("   where pcategory = ? ) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Post> list = jt.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(Post.class),
            category, startRec, endRec
    );
    return list;
  }
  @Override
  public List<EventInfo> findAllEvents(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT ");
    sql.append("      ROW_NUMBER() OVER (ORDER BY event_id DESC) no, ");
    sql.append("      event_id, ");
    sql.append("      mt20id, ");
    sql.append("      prfnm, ");
    sql.append("      prfpdfrom, ");
    sql.append("      prfpdto, ");
    sql.append("      fcltynm, ");
    sql.append("      prfcast, ");
    sql.append("      prfcrew, ");
    sql.append("      prfruntime, ");
    sql.append("      prfage, ");
    sql.append("      entrpsnm, ");
    sql.append("      pcseguidance, ");
    sql.append("      poster, ");
    sql.append("      genrenm, ");
    sql.append("      prfstate ");
    sql.append("    from p_event) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<EventInfo> result = jt.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(EventInfo.class),
            startRec,
            endRec);
    try {
      log.info("Evt query = {}", result.size());
    } catch (NullPointerException e) {
      log.info("널포인트");
    }
    return result;
  }

  //검색
  @Override
  public List<Post> findAll(PostFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT  ROW_NUMBER() OVER (ORDER BY pgroup DESC, step ASC) no, ");
    sql.append("            post_id, ");
    sql.append("            pcategory, ");
    sql.append("            title, ");
    sql.append("            email, ");
    sql.append("            nickname, ");
    sql.append("            hit, ");
    sql.append("            pcontent, ");
    sql.append("            ppost_id, ");
    sql.append("            pgroup, ");
    sql.append("            step, ");
    sql.append("            pindent, ");
    sql.append("            status, ");
    sql.append("            cdate, ");
    sql.append("            udate ");
    sql.append("      FROM post ");
    sql.append("     WHERE ");

    //분류
    sql = dynamicQuery(filterCondition, sql);

    sql.append(") t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Post> list = null;

    //게시판 전체
//    if(StringUtils.isEmpty(filterCondition.getCategory())){
//      list = jt.query(
//          sql.toString(),
//          new BeanPropertyRowMapper<>(post.class),
//          filterCondition.getStartRec(),
//          filterCondition.getEndRec()
//      );
    //게시판 분류
//    }else{
    list = jt.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(Post.class),
            filterCondition.getCategory(),
            filterCondition.getStartRec(),
            filterCondition.getEndRec()
    );
//    }

    return list;
  }

  @Override
  public List<EventInfo> findAllEvents(PostFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT  ROW_NUMBER() OVER (ORDER BY event_id DESC) no, ");
    sql.append("            event_id, ");
    sql.append("            mt20id, ");
    sql.append("            prfnm, ");
    sql.append("            prfpdfrom, ");
    sql.append("            prfpdto, ");
    sql.append("            fcltynm, ");
    sql.append("            prfcast, ");
    sql.append("            prfcrew, ");
    sql.append("            prfruntime, ");
    sql.append("            prfage, ");
    sql.append("            entrpsnm, ");
    sql.append("            pcseguidance, ");
    sql.append("            poster, ");
    sql.append("            sty, ");
    sql.append("            genrenm, ");
    sql.append("            prfstate, ");
    sql.append("            dtguidance ");
    sql.append("      FROM p_event ");
    sql.append("     WHERE ");

    //분류
    sql = dynamicQueryForEvent(filterCondition, sql);
    sql.append(") t1 ");
    sql.append("where t1.no between ? and ? ");

    List<EventInfo> list = null;
    list = jt.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(EventInfo.class),
            filterCondition.getStartRec(),
            filterCondition.getEndRec()
    );
    return list;
  }

  //조회
  @Override
  public Post findByPostId(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT  ");
    sql.append("  post_id, ");
    sql.append("  pcategory,  ");
    sql.append("  title,  ");
    sql.append("  email,  ");
    sql.append("  nickname, ");
    sql.append("  hit,  ");
    sql.append("  pcontent, ");
    sql.append("  ppost_id,  ");
    sql.append("  pgroup, ");
    sql.append("  step, ");
    sql.append("  pindent,  ");
    sql.append("  status, ");
    sql.append("  cdate,  ");
    sql.append("  udate ");
    sql.append("FROM  ");
    sql.append("  post ");
    sql.append("where post_id = ?  ");

    Post postItem = null;
    try {
      postItem = jt.queryForObject(
              sql.toString(),
              new BeanPropertyRowMapper<>(Post.class),
              id);
    }catch (Exception e){ // 1건을 못찾으면
      postItem = null;
    }

    return postItem;
  }

  @Override
  public EventInfo findByEventId(Long id) {

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT  ");
    sql.append("  event_id, ");
    sql.append("  mt20id,  ");
    sql.append("  prfnm,  ");
    sql.append("  prfpdfrom,  ");
    sql.append("  prfpdto, ");
    sql.append("  fcltynm,  ");
    sql.append("  prfcast, ");
    sql.append("  prfcrew,  ");
    sql.append("  prfruntime, ");
    sql.append("  prfage, ");
    sql.append("  entrpsnm,  ");
    sql.append("  pcseguidance, ");
    sql.append("  poster,  ");
    sql.append("  sty,  ");
    sql.append("  genrenm,  ");
    sql.append("  prfstate,  ");
    sql.append("  openrun,  ");
    sql.append("  mt10id,  ");
    sql.append("  dtguidance ");
    sql.append("FROM  ");
    sql.append("  p_event ");
    sql.append("where event_id = ?  ");

    EventInfo eventInfo = null;
    try {
      eventInfo = jt.queryForObject(
              sql.toString(),
              new BeanPropertyRowMapper<>(EventInfo.class),
              id);
    }catch (Exception e){ // 1건을 못찾으면
      eventInfo = null;
    }

    return eventInfo;
  }


  @Override
  public FacInfo findByFacId(String id) {

    log.info("id={}", id);
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT  ");
    sql.append("  mt10id, ");
    sql.append("  fcltynm,  ");
    sql.append("  mt13cnt,  ");
    sql.append("  fcltychartr,  ");
    sql.append("  seatscale, ");
    sql.append("  telno,  ");
    sql.append("  relateurl, ");
    sql.append("  adres,  ");
    sql.append("  opende, ");
    sql.append("  la, ");
    sql.append("  lo  ");
    sql.append("FROM  ");
    sql.append("  p_facility ");
    sql.append("where mt10id = ? ");

    FacInfo facInfo = null;
    try {
      facInfo = jt.queryForObject(
              sql.toString(),
              new BeanPropertyRowMapper<>(FacInfo.class),
              id);
    }catch (Exception e){ // 1건을 못찾으면
      facInfo = null;
    }

    return facInfo;
  }

  //삭제
  @Override
  public int deleteByPostId(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM post ");
    sql.append(" WHERE post_id = ? ");

    int updateItemCount = jt.update(sql.toString(), id);

    return updateItemCount;
  }

  //수정
  @Override
  public int updateByPostId(Long id, Post post) {

    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE post ");
    sql.append("   SET pcategory = ?, ");
    sql.append("       title = ?, ");
    sql.append("       pcontent = ?, ");
    sql.append("       udate = systimestamp ");
    sql.append(" WHERE post_id = ? ");

    int updatedItemCount = jt.update(
            sql.toString(),
            post.getPcategory(),
            post.getTitle(),
            post.getPcontent(),
            id
    );

    return updatedItemCount;
  }

  @Override
  public String facilityLink(String facid) {

    return null;
  }

  //답글
  @Override
  public Long saveReply(Long ppostId, Post replypost) {

    //부모글 참조반영
    Post post = addInfoOfParentToChild(ppostId,replypost);

    StringBuffer sql = new StringBuffer();
    sql.append("insert into post (post_id,pcategory,title,email,nickname,pcontent,ppost_id,pgroup,step,pindent) ");
    sql.append("values(post_post_id_seq.nextval,?,?,?,?,?,?,?,?,?) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jt.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"post_id"});
        pstmt.setString(1, post.getPcategory());
        pstmt.setString(2, post.getTitle());
        pstmt.setString(3, post.getEmail());
        pstmt.setString(4, post.getNickname());
        pstmt.setString(5, post.getPcontent());
        pstmt.setLong(6, post.getPpostId());
        pstmt.setLong(7, post.getPgroup());
        pstmt.setInt(8, post.getStep());
        pstmt.setInt(9, post.getPindent());
        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("post_id").toString());
  }

  //답글에 부모정보 반영하기
  private Post addInfoOfParentToChild(Long ppostId, Post replypost) {
    //부모글
    Post post = findByPostId(ppostId);

    //부모글의 카테고리 가져오기
    replypost.setPcategory(post.getPcategory());

    //bgroup 로직
    // 답글의 bgroup = 부모글의 bgroup
    replypost.setPgroup(post.getPgroup());

    //step 로직
    //1) 부모글의 bgroup값과 동일한 게시글중 부모글의 step보다큰 게시글의 bstep을 1씩 증가
    int affectedRows = updateBstep(post);
    //2) 답글의 bstep값은 부모글의 bstep값 + 1
    replypost.setStep(post.getStep()+1);

    //bindent 로직
    // 답글의 bindent = 부모글의 bindent + 1
    replypost.setPindent(post.getPindent()+1);

    replypost.setPpostId(ppostId);
    return replypost;
  }

  //부모글과 동일한그룹 bstep반영
  private int updateBstep(Post post) {
    StringBuffer sql = new StringBuffer();

    sql.append("update post ");
    sql.append("   set step = step + 1 ");
    sql.append(" where pgroup =  ? ");
    sql.append("   and step  >  ? ");

    int affectedRows = jt.update(sql.toString(), post.getPgroup(), post.getStep());

    return affectedRows;
  }

  //조회수증가
  @Override
  public int increaseHitCount(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("update post  ");
    sql.append("set hit = hit + 1 ");
    sql.append("where post_id = ? ");

    int affectedRows = jt.update(sql.toString(), id);

    return affectedRows;
  }


  //좋아요증감
  @Override
  public int increaseGoodCount(Long id) {
    return 0;
  }

  //전체건수
  @Override
  public int totalCount() {

    String sql = "select count(*) from post";

    Integer cnt = jt.queryForObject(sql, Integer.class);

    return cnt;
  }

  @Override
  public int totalPEventCount() {

    String sql = "select count(*) from p_event";

    Integer cnt = jt.queryForObject(sql, Integer.class);
    log.info("Event size ={}", cnt);
    return cnt;
  }

  @Override
  public int totalCount(String bcategory) {

    String sql = "select count(*) from post where pcategory = ? ";

    Integer cnt = jt.queryForObject(sql, Integer.class, bcategory);

    return cnt;
  }

  @Override
  public int totalCount(PostFilterCondition filterCondition) {

    StringBuffer sql = new StringBuffer();

    sql.append("select count(*) ");
    sql.append("  from post  ");
    sql.append(" where  ");

    sql = dynamicQuery(filterCondition, sql);

    Integer cnt = 0;
    //게시판 전체 검색 건수
    if(StringUtils.isEmpty(filterCondition.getCategory())) {
      cnt = jt.queryForObject(
              sql.toString(), Integer.class
      );
      //게시판 분류별 검색 건수
    }else{
      cnt = jt.queryForObject(
              sql.toString(), Integer.class,
              filterCondition.getCategory()
      );
    }

    return cnt;
  }

  @Override
  public int totalEventCount(PostFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();

    sql.append("select count(*) ");
    sql.append("  from p_event  ");
    sql.append(" where  ");

    sql = dynamicQueryForEvent(filterCondition, sql);

    Integer cnt = 0;
    //게시판 전체 검색 건수
    if(StringUtils.isEmpty(filterCondition.getCategory())) {
      cnt = jt.queryForObject(
              sql.toString(), Integer.class
      );
      //게시판 분류별 검색 건수
    }else{
      cnt = jt.queryForObject(
              sql.toString(), Integer.class,
              filterCondition.getCategory()
      );
    }

    return cnt;
  }

  private StringBuffer dynamicQuery(PostFilterCondition filterCondition, StringBuffer sql) {
    //분류
    if(StringUtils.isEmpty(filterCondition.getCategory())){

    }else{
      log.info("dnmic query cate={}", filterCondition.getCategory());
      sql.append("       pcategory = ? ");
    }

    //분류,검색유형,검색어 존재
    if(!StringUtils.isEmpty(filterCondition.getCategory()) &&
            !StringUtils.isEmpty(filterCondition.getSearchType()) &&
            !StringUtils.isEmpty(filterCondition.getKeyword())){

      sql.append(" AND ");
    }

    //검색유형
    switch (filterCondition.getSearchType()){
      case "TC":  //제목 + 내용
        sql.append("    (  title    like '%"+ filterCondition.getKeyword()+"%' ");
        sql.append("    or pcontent like '%"+ filterCondition.getKeyword()+"%' )");
        break;
      case "T":   //제목
        sql.append("       title    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "C":   //내용
        sql.append("       pcontent like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "E":   //아이디(이메일)
        sql.append("       email    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "N":   //별칭
        sql.append("       nickname like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      default:
    }
    return sql;
  }

  private StringBuffer dynamicQueryForEvent(PostFilterCondition filterCondition, StringBuffer sql) {

    //검색유형
    switch (filterCondition.getSearchType()){
      case "ETC":   //이벤트 제목+내용
        sql.append("       prfnm    like '%"+ filterCondition.getKeyword()+"%' ");
        sql.append("    or sty like '%"+ filterCondition.getKeyword()+"%' )");
        break;
      case "ET":  //이벤트 제목
        sql.append("       prfnm    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "EC":  //이벤트 내용
        sql.append("       sty like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "EG":  //이벤트 장르
        sql.append("       genrenm like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "ED":  //이벤트 일자
        sql.append("       to_date(prfpdfrom) <= '" + filterCondition.getKeyword() + "' ");
        sql.append("       and to_date(prfpdto) >= '" + filterCondition.getKeyword() + "' ");
        break;
      case "EA":  //이벤트 관람 가능 연령
        sql.append("       prfage like '%"+ filterCondition.getKeyword()+"%' ");
        break;

      default:
    }
    return sql;
  }

  /**
   * 내가 쓴 글
   * @param email 아이디
   * @return
   */
  @Override
  public List<Post> myPost(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select post_id, title, cdate from post where email = ? ");
    sql.append("order by cdate desc ");

    List<Post> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Post.class), email);

    return list;
  }
}
