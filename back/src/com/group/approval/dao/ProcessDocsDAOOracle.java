package com.group.approval.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.Reference;
import com.group.approval.exception.ModifyException;
import com.group.approval.exception.UpdateException;
import com.group.employee.dto.Employee;
import com.group.sql.DBConnection;
import com.group.sql.MyConnection;

public class ProcessDocsDAOOracle implements ProcessDocsDAO {

	@Override
	// 사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (결재승인테이블)
	public void updateApproval(Approval ap) throws UpdateException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UpdateException(e.getMessage());
		}

		// ap_type, ap_date, ap_comment가 수정돼야 함
		String updateApprovalSQL = "UPDATE approval SET ap_type=?, ap_date=SYSTIMESTAMP, ap_comment=?\r\n"
				+ "WHERE document_no=? and employee_id=?";

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(updateApprovalSQL);
			pstmt.setString(1, ap.getAp_type().getAp_type());
			pstmt.setString(2, ap.getAp_ap_comment());
			pstmt.setString(3, ap.getDocument_no().getDocument_no());
			pstmt.setString(4, ap.getEmployee_id().getEmployee_id());
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				System.out.println("결재완료");
			} else {
				System.out.println(cnt);
				throw new UpdateException("입력양식을 확인해주세요");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UpdateException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	// 사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (합의승인테이블)
	@Override
	public void updateAgreement(Agreement ag) throws UpdateException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UpdateException(e.getMessage());
		}
		// ap_type, ap_date, ap_comment가 수정돼야 함
		String updateAgreementSQL = "UPDATE agreement SET ap_type=?, ap_date=SYSTIMESTAMP, ap_comment=?\r\n" + 
				"WHERE document_no=? and employee_id=?";

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(updateAgreementSQL);
			pstmt.setString(1, ag.getAg_ap_type().getAp_type());
			pstmt.setString(2, ag.getAg_ap_comment());
			pstmt.setString(3, ag.getDocument_no().getDocument_no());
			pstmt.setString(4, ag.getEmployee_id().getEmployee_id());
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				System.out.println("합의결재 완료");
			} else {
				System.out.println(cnt);
				throw new UpdateException("입력양식을 확인해주세요");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UpdateException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	// 참조자의 참조 승인 선택
	public void updateReference(Reference R) throws UpdateException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UpdateException(e.getMessage());
		}
		// ap_type 수정돼야 함
		String updateReferenceSQL = "UPDATE reference SET ap_type=? WHERE document_no=? AND employee_id=?";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(updateReferenceSQL);
			pstmt.setString(1, R.getRe_ap_type().getAp_type());
			pstmt.setString(2, R.getDocument_no().getDocument_no());
			pstmt.setString(3, R.getEmployee_id().getEmployee_id());
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				System.out.println("참조확인 완료");
			} else {
				System.out.println(cnt);
				throw new UpdateException("입력양식을 확인해주세요");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UpdateException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	// 모두 승인처리를 할 경우, 최종 상태를 '승인'으로
	public void documentAudmit(String document_no) throws ModifyException {
		// DB연결
		Connection con = DBConnection.
		String documentAudmitSQL = "create or replace procedure AUDMIT(documentNo varchar2, EmployeeId varchar2)\r\n" + 
				"IS \r\n" + 
				"    cntAg1 number;\r\n" + 
				"    cntAg2 number;\r\n" + 
				"    \r\n" + 
				"    cntAp1 number;\r\n" + 
				"    cntAp2 number;\r\n" + 
				"BEGIN\r\n" + 
				"  Update approval set ap_type='승인' WHERE document_no=documentNo AND employee_id=EmployeeId;\r\n" + 
				"  commit;\r\n" + 
				"   SELECT count(*) into cntAg1 \r\n" + 
				"    FROM agreement \r\n" + 
				"    WHERE ap_type='승인' AND document_no=documentNo;\r\n" + 
				"\r\n" + 
				"    SELECT count(*) into cntAg2 \r\n" + 
				"    FROM agreement \r\n" + 
				"    WHERE document_no=documentNo;  \r\n" + 
				"\r\n" + 
				"    SELECT count(*) into cntAp1 \r\n" + 
				"    FROM approval \r\n" + 
				"    WHERE ap_type='승인' AND document_no=documentNo; \r\n" + 
				"\r\n" + 
				"    SELECT count(*) into cntAp2 \r\n" + 
				"    FROM approval \r\n" + 
				"    WHERE document_no=documentNo;\r\n" + 
				"\r\n" + 
				"    IF cntAg1=cntAg2 AND cntAp1=cntAp2 THEN\r\n" + 
				"        UPDATE document SET document_status='승인' WHERE document_no=documentNo; \r\n" + 
				"\r\n" + 
				"    END IF;   \r\n" + 
				"commit; \r\n" + 
				"END;\r\n" + 
				"/\r\n" + 
				"\r\n" + 
				"EXECUTE AUDMIT(? , ?)";
		CallableStatement cstmt;
		cstmt = DBConnection.prepareCall("{call AUDMIT(?,?)}");
		cstmt.execute();
	}

	@Override
	// 한 명이라도 반려 할 경우, 최종 상태는 '반려'
	public void documentRefuse(String document_no) throws ModifyException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		String documentRefuseSQL = "create or replace procedure REFUSE(documentNo varchar2, EmployeeId varchar2)\r\n" + 
				"IS \r\n" + 
				"cntAg1 number;\r\n" + 
				"cntAp1 number; \r\n" + 
				"BEGIN\r\n" + 
				"  Update agreement set ap_type='반려' WHERE document_no=documentNo AND employee_id=EmployeeId;\r\n" + 
				"  commit;\r\n" + 
				"\r\n" + 
				"      SELECT count(*) into cntAg1 \r\n" + 
				"    FROM agreement \r\n" + 
				"    WHERE ap_type='반려' AND document_no=documentNo;\r\n" + 
				"    \r\n" + 
				"\r\n" + 
				"    SELECT count(*) into cntAp1 \r\n" + 
				"    FROM approval \r\n" + 
				"    WHERE ap_type='반려' AND document_no=documentNo; \r\n" + 
				"    \r\n" + 
				"\r\n" + 
				"  IF cntAg1>=1 OR cntAp1>=1 THEN\r\n" + 
				"  DBMS_OUTPUT.PUT_LINE(cntAg1);\r\n" + 
				"  DBMS_OUTPUT.PUT_LINE(cntAp1);\r\n" + 
				"        UPDATE document SET document_status='반려' WHERE document_no=documentNo;\r\n" + 
				"\r\n" + 
				"    END IF;   \r\n" + 
				"commit; \r\n" + 
				"END;\r\n" + 
				"/\r\n" + 
				"\r\n" + 
				"EXECUTE REFUSE(?,?)";
	}

	public static void main(String[] args) throws Exception {
//		//approval case. 승인선택, 코멘트남긴 경우
//		try {
//			ProcessDocsDAOOracle dao = new ProcessDocsDAOOracle();
//			Employee em = new Employee();
//			Approval ap = new Approval();
//			// ap_type넣기 위한 처리 ApprovalStatus 상태값 저장 후 -> Approval에 ApprovalStatus객체저장
//			ApprovalStatus aps = new ApprovalStatus();
//			aps.setAp_type("승인");
//			ap.setAp_type(aps);
//			// comment
//			ap.setAp_ap_comment("허가하노라");
//			// employee_id를 넣기 위한 처리 Employee에서 아이디값 저장 후 -> Approval에 Employee객체 저장
//			em.setEmployee_id("IFR001");
//			ap.setEmployee_id(em);
//			// Document를 넣기 위한 처리 Document에서 아이디값 저장 후 -> Approval에 Document객체 저장
//			Document d = new Document();
//			d.setDocument_no("CR-회람-20210621-0001");
//			ap.setDocument_no(d);
//			// Approval에 필요한값 다 넣었으니 dao에게 보내기
//			dao.updateApproval(ap);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
//		//agreement case. 반려선택, 코멘트남긴 경우
//		try {
//			ProcessDocsDAOOracle dao = new ProcessDocsDAOOracle();
//			Employee em = new Employee();
//			Agreement ag = new Agreement();
//			ApprovalStatus aps = new ApprovalStatus();
//			aps.setAp_type("반려");
//			ag.setAg_ap_type(aps);
//			ag.setAg_ap_comment("이딴걸 보고서라고 써와?!");
//			em.setEmployee_id("DEV005");
//			ag.setEmployee_id(em);
//			Document d = new Document();
//			d.setDocument_no("AC-품의-20210627-0003");
//			ag.setDocument_no(d);
//			dao.updateAgreement(ag);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
//		//Reference case. 확인test
//		try {
//			ProcessDocsDAOOracle dao = new ProcessDocsDAOOracle();
//			Employee em = new Employee();
//			Document d = new Document();
//			Reference R = new Reference();
//			ApprovalStatus aps = new ApprovalStatus();
//			aps.setAp_type("승인");
//			R.setRe_ap_type(aps);
//			em.setEmployee_id("DEV001");
//			R.setEmployee_id(em);
//			d.setDocument_no("LE-휴가-20210624-0001");
//			R.setDocument_no(d);
//			dao.updateReference(R);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}		
	}
}
