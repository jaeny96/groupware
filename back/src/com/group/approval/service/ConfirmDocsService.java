package com.group.approval.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.group.approval.dao.ConfirmDocsDAO;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.exception.SearchException;

public class ConfirmDocsService {
   private ConfirmDocsDAO dao;
   private static ConfirmDocsService service;
   public static String envProp;
   
   private ConfirmDocsService(){
      Properties env =new Properties();
      try {
         env.load(new FileInputStream(envProp));
         String className = env.getProperty("ConfirmDocsDAO");
         System.out.println(className);
         Class c = Class.forName(className);
         dao = (ConfirmDocsDAO)c.newInstance();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (InstantiationException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
   
   public static ConfirmDocsService getInstance() {
      if(service == null) {
         service = new ConfirmDocsService();
      }
      return service;
   }
   

   /**
    * (전체/승인/대기/반려)사용자는 확인 문서를 선택해서 볼 수 있다. 
    * @param id 로그인한 사용자 id
    * @param status 문서 상태값
    * @return 사용자가 선택한 결과값 목록
    * @throws FindException
    */   
   public List<Document> findCheckDocsOk(String id,String status) throws FindException{
      List<Document> lists=null;
      if(status.equals("")) {
         lists=dao.selectByCheckAllOk(id);
      }else if(status.equals("대기")){
         lists=dao.selectByCheckWaitOk(id);
      }else if(status.equals("승인")){
         lists=dao.selectByCheckOkOk(id);
      }else if(status.equals("반려")) {
         lists=dao.selectByCheckNoOk(id);
      }
      return lists;
   }
   
   /**
    * (전체/승인/대기/반려)사용자는 미확인 문서를 선택해서 볼 수 있다. 
    * @param id 로그인한 사용자 id
    * @param status 문서 상태값
    * @return 사용자가 선택한 결과값 목록
    * @throws FindException
    */   
   public List<Document> findCheckDocsNo(String id,String status) throws FindException{
      List<Document> lists=null;
      if(status.equals("")) {
         lists=dao.selectByCheckAllNo(id);
      }else if(status.equals("대기")){
         lists=dao.selectByCheckWaitNo(id);
      }else if(status.equals("승인")){
         lists=dao.selectByCheckOkNo(id);
      }else if(status.equals("반려")) {
         lists=dao.selectByCheckNoNo(id);
      }
      return lists;
   }

   /**
    * 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
    * @param id 로그인한 사용자 id
    * @param docsNo 클릭한 문서 번호 
    * @throws FindException
    */
   public List<String> findDocsMyCheck(String id, String docsNo) throws FindException{
      return dao.selectByMyClick(id, docsNo);
   };

   
   /**
    * 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
    * @param docsNo 선택한 문서번호
    * @return 문서 상세 내용 + 결재선 정보
    * @throws FindExceptionb
    */
   public List<Document> findDocsDetail(String docsNo) throws FindException{      
      return dao.selectByDocsDetail(docsNo);
   }
   
   /**
    * 해당 문서의 코멘트를 확인할 수 있다. 
    * @param docsNo 선택한 문서번호
    * @return 코멘트 내용
    * @throws FindException
    */
    public List<Approval> findByComments(String docNo) throws FindException{
       return dao.selectByComments(docNo);
    }
   

   /**6.
    * 문서에 대해 제목으로 검색할 수 있다.
    * @param id 로그인한 사용자 id
    * @param title 제목 검색시 입력값
    * @param 사이드바 전체 / 대기 / 승인 / 반려 값 
    * @return 사용자가 입력한 검색어에 일치하는 목록 
    * @throws FindException
    * @throws SearchException 
    */   
   public List<Document> findMySearchTitle(String id,String title,String status) throws FindException, SearchException{
      
      List<Document> lists=null;
      if(status.equals("")) {
         lists=dao.selectBySearchTitle(id, title);
      }else if(status.equals("대기")){
         lists=dao.selectBySearchTitleWait(id, title);
      }else if(status.equals("승인")){
         lists=dao.selectBySearchTitleOk(id, title);
      }else if(status.equals("반려")) {
         lists=dao.selectBySearchTitleNo(id, title);
      }
      return lists;
   }
   
   /**6.
    * 문서에 대해 내용으로 검색할 수 있다.
    * @param id 로그인한 사용자 id
    * @param content 내용 검색시 입력값
    * @param 사이드바 전체 / 대기 / 승인 / 반려 값
    * @return 사용자가 입력한 검색어에 일치하는 목록 
    * @throws FindException
    * @throws SearchException 
    */ 
   public List<Document> findMySearchContent(String id,String content,String status) throws FindException, SearchException{
      List<Document> lists=null;
      if(status.equals("")) {
         lists=dao.selectBySearchContent(id, content);
      }else if(status.equals("대기")){
         lists=dao.selectBySearchContentWait(id, content);
      }else if(status.equals("승인")){
         lists=dao.selectBySearchContentOk(id, content);
      }else if(status.equals("반려")) {
         lists=dao.selectBySearchContentNo(id, content);
      }
      return lists;
   }
   
   
//   public static void main(String[] args) {
//      ConfirmDocsService service = ConfirmDocsService.getInstance();
//      
//      String id="DEV001";
//   
//      //확인or미확인 문서 목록
//      try {
//         String documentState="반려";
//         String check="확인";
//         List<Document> selectCheckList = new ArrayList<>();
//         System.out.println(id+"사원이 받은 문서들의 "+check+"값의 전체 목록");
//         selectCheckList=service.findCheckDocsAll(id,check);
//         for(Document d: selectCheckList) {   
//            System.out.println(d.getDocument_no()+" "+
//                  d.getDocument_title()+" "+
//                  d.getEmployee().getEmployee_id()+" "+
//                  d.getEmployee().getName()+" "+
//                  d.getDraft_date()+" "+
//                  d.getDocument_type().getDocument_type()+" "+
//                  d.getApproval().getAp_type().getApStatus_type());
//         }
//         System.out.println();
//         
////         List<Document> selectCheckList1 = new ArrayList<>();
////         System.out.println(id+"사원이 받은 문서들의 "+check+"값의 "+documentState+" 목록");
////         selectCheckList1=service.findCheckDocsState(id,documentState,check);
////         for(Document d: selectCheckList1) {   
////            System.out.println(d.getDocument_no()+" "+
////                  d.getDocument_title()+" "+
////                  d.getEmployee().getEmployee_id()+" "+
////                  d.getEmployee().getName()+" "+
////                  d.getDraft_date()+" "+
////                  d.getDocument_type().getDocument_type()+" "+
////                  d.getApproval().getAp_type().getApStatus_type());
////         }
////         System.out.println();
//                  
//         
//      
//      } catch (FindException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//      
//      //승인해야할 부분   
//      try {
//         List<String> list;
//         list = service.findDocsMyCheck(id,"CR-회람-20210621-0001");
//         System.out.println("자신의 승인 부분 : "+list.get(0).toString()+" "+list.get(1).toString()+" "+list.get(2).toString());
//         System.out.println();
//      } catch (FindException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//   
//      //문서 detail받기
//      try {
//         String docsNum="LE-휴가-20210624-0001";
//         List<Document> detailList = new ArrayList<>();
//         System.out.println(docsNum+" 상세 내용");
//         detailList=service.findDocsDetail(docsNum);
//         for(Document d: detailList) {   
//            System.out.println("문서내용 ="+
//                  d.getDocument_title()+" "+
//                  d.getDocument_type().getDocument_type()+" "+
//                  d.getDocument_no()+" "+
//                  d.getEmployee().getName()+" "+
//                  d.getEmployee().getEmployee_id()+" "+
//                  d.getDraft_date()+" "+
//                  d.getDocument_content()+
//                  "\n결재자0="+
//                  d.getApprovals().get(0).getEmployee_id().getName()+
//                  d.getApprovals().get(0).getAp_ap_date()+
//                  "\n결재자1="+
//                  d.getApprovals().get(1).getEmployee_id().getName()+
//                  d.getApprovals().get(1).getAp_ap_date()+
//                  "\n결재자2="+
//                  d.getApprovals().get(2).getEmployee_id().getName()+
//                  d.getApprovals().get(2).getAp_ap_date()+
//                  "\n결재자3="+
//                  d.getApprovals().get(3).getEmployee_id().getName()+
//                  d.getApprovals().get(3).getAp_ap_date()+
//                  "\n합의자 ="+
//                  d.getAgreement().getEmployee_id().getName()+" "+
//                  d.getAgreement().getAg_ap_date()+
//                  "\n참조자="+
//                  d.getReference().getEmployee_id().getName()
//                  );
//         }
//         System.out.println();
//      } catch (FindException e) {
//         e.printStackTrace();
//      }
//      
//      //제목,내용 검색 
//      try {
//         String title="";
//         String content="연락";
//         List<Document> searchList = new ArrayList<>();
//         System.out.println("제목 검색값 :"+title);
//         System.out.println("내용 검색값 :"+content);
//         searchList=service.findMySearchTitle(id, "휴가");
//         for(Document d: searchList) {
//            System.out.println(
//                  d.getState()+" "+
//                  d.getDocument_no()+" "+
//                  d.getDocument_title()+" "+
//                  d.getEmployee().getEmployee_id()+" "+
//                  d.getEmployee().getName()+" "+
//                  d.getDraft_date()+" "+
//                  d.getDocument_type().getDocument_type()+" "+
//                  d.getApproval().getAp_type().getApStatus_type());
//         }
//         
//         System.out.println();
//      } catch (FindException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      } catch (SearchException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//      
//      
//      
//   }

}