$(function () {
  //문서정보 관련 테이블 구성요소 받아오기
  var tdDetailType = document.getElementById("apDocumentDetailType");//문서종류 칸
  var tdDetailNo = document.getElementById("apDocumentDetailNo");//문서번호 칸
  var tdDetailDep = document.getElementById("apDocumentDetailDep");//기안부서 칸
  var tdDetailWriter = document.getElementById("apDocumentDetailWriter");//기안자 칸
  var tdDetailDate = document.getElementById("apDocumentDetailDate");//기안 일시 칸

  //내용 관련 구성요소 받아오기
  var divDetailTitle = document.getElementById("apDocumentDetailTitle");//문서 제목
  var divDetailContent = document.getElementById("apDocumentContent");//문서 내용

  //결재 관련 테이블 구성요소 받아오기
  var apStep0Obj = document.getElementById("apApprovalStep0");//결재 0 칸 , 이미지 or 버튼 들어갈 곳
  var apStep1Obj = document.getElementById("apApprovalStep1");//결재 1 칸 , 이미지 or 버튼 들어갈 곳
  var apStep2Obj = document.getElementById("apApprovalStep2");//결재 2 칸 , 이미지 or 버튼 들어갈 곳
  var apStep3Obj = document.getElementById("apApprovalStep3");//결재 3 칸 , 이미지 or 버튼 들어갈 곳
  var tdApStep0Name = document.getElementById("apApprovalStepName0");//결재 0 칸 , 이름 들어갈 곳
  var tdApStep1Name = document.getElementById("apApprovalStepName1");//결재 1 칸 , 이름 들어갈 곳 
  var tdApStep2Name = document.getElementById("apApprovalStepName2");//결재 2 칸 , 이름 들어갈 곳 
  var tdApStep3Name = document.getElementById("apApprovalStepName3");//결재  3 칸 , 이름 들어갈 곳
  var divApStep0Date = document.getElementById("apApprovalStepDate0");//결재 0 칸 , 날짜 들어갈 곳
  var divApStep1Date = document.getElementById("apApprovalStepDate1");//결재 1 칸 , 날짜 들아갈 곳
  var divApStep2Date = document.getElementById("apApprovalStepDate2");//결재 2 칸 , 날짜 들어갈 곳 
  var divApStep3Date = document.getElementById("apApprovalStepDate3");//결재 3 칸 , 날짜 들어갈 곳
  //합의 관련 테이블 구성요소 받아오기 
  var tdAgName = document.getElementById("apAgreementName");
  //참조 관련 테이블 구성요소 받아오기 
  var tdReName = document.getElementById("apReferenceName");
  
  //승인요망 버튼 구성요소 생성하기 : 결재선 0,1,2,3 단계 모두 생성
  var buttonTag = document.createElement("button");
  buttonTag.setAttribute("id", "apCommentConfirmBtn");
  buttonTag.setAttribute("class", "btn");
  buttonTag.setAttribute("class", "ap-btn-outline-purple");
  buttonTag.innerHTML = "승인 요망";
  var buttonTag1 = document.createElement("button");
  buttonTag1.setAttribute("id", "apCommentConfirmBtn");
  buttonTag1.setAttribute("class", "btn");
  buttonTag1.setAttribute("class", "ap-btn-outline-purple");
  buttonTag1.innerHTML = "승인 요망";
  var buttonTag2 = document.createElement("button");
  buttonTag2.setAttribute("id", "apCommentConfirmBtn");
  buttonTag2.setAttribute("class", "btn");
  buttonTag2.setAttribute("class", "ap-btn-outline-purple");
  buttonTag2.innerHTML = "승인 요망";
  var buttonTag3 = document.createElement("button");
  buttonTag3.setAttribute("id", "apCommentConfirmBtn");
  buttonTag3.setAttribute("class", "btn");
  buttonTag3.setAttribute("class", "ap-btn-outline-purple");
  buttonTag3.innerHTML = "승인 요망";
  console.log(buttonTag3);
  var agButtonTag = document.createElement("button");
  agButtonTag.setAttribute("id", "approvalCommentBtn");
  agButtonTag.setAttribute("class", "btn");
  agButtonTag.setAttribute("class", "ap-btn-outline-purple");
  agButtonTag.setAttribute("style", "width: 90px");
  agButtonTag.innerHTML = "승인 요망";
 //이미지 구성요소 생성하기 : 결재선 0,1,2,3 단계 모두 생성 
  var imgTag = document.createElement("img");
  imgTag.style.width = "100px";
  var imgTag1 = document.createElement("img");
  console.log(imgTag1);
  imgTag1.style.width = "100px";
  var imgTag2 = document.createElement("img");
  imgTag2.style.width = "100px";
  var imgTag3 = document.createElement("img");
  imgTag3.style.width = "100px";
  var agImgTag = document.createElement("img");
  agImgTag.style.width = "60px";
// 참조 확인용을 위한 구성요소 생성하기 
  var spanTag = document.createElement("div");

  //받아올 데이터들
  var tmpDocsBdNo = localStorage.getItem("apDocumentNum"); //선택한 문서번호 : local에 가져오기

  var apDocsType = new Array();//문서 종류 
  var apDocsNo = new Array();//문서 번호
  var apDocsDep = new Array();//기안 부서
  var apDocsWriter = new Array();//기안자
  var apDocsDate = new Array();//기안 일시

  var apDocsTitle = new Array();//문서 제목
  var apDocsContent = new Array();//문서 내용

  var apDocsApName0 = new Array();//결재자 0 이름
  var apDocsApDate0 = new Array();//결재자 0 승인날짜
  var apDocsApType0 = new Array();//결재자 0 승인여부

  var apDocsApName1 = new Array();//결재자 1 이름
  var apDocsApDate1 = new Array();//결재자 1 승인날짜
  var apDocsApType1 = new Array();//결재자 1 승인여부
  
  var apDocsApName2 = new Array();//결재자 2 이름
  var apDocsApDate2 = new Array();//결재자 2 승인날짜
  var apDocsApType2 = new Array();//결재자 2 승인여부
  
  var apDocsApName3 = new Array();//결재자 3 이름
  var apDocsApDate3 = new Array();//결재자 3 승인날짜
  var apDocsApType3 = new Array();//결재자 3 승인여부

  var apDocsAgName = new Array();//합의자 이름
  var apDocsAgType = new Array();//합의자 승인여부

  var apDocsReName = new Array();//참조자 이름
  var apDocsReType = new Array();//참조자 승인여부

  var myCheckId = null;// 내가 승인할 위치인지 확인하기 위해 만든 변수


  //내가 승인해야할 부분 받아오기 ajax
  $.ajax({
    url: "/back/selectmyadmin",
    method: "get",
    data: {
      docsNo: tmpDocsBdNo,
    },

    success: function (responseObj) {
      myCheckId = responseObj.loginName;
      console.log(myCheckId);
    },
  });

  //상세내용 구성요소 채우기 관련 함수 시작
  function createApBdElement(i) {
    //문서정보 채우기
    tdDetailType.innerText = apDocsType;
    tdDetailNo.innerText = apDocsNo;
    tdDetailDep.innerText = apDocsDep;
    tdDetailWriter.innerText = apDocsWriter;
    tdDetailDate.innerText = apDocsDate;
    divDetailTitle.innerText = apDocsTitle;
    divDetailContent.innerText = apDocsContent;

    // 결재 관련 
    //결재선 이름 채우기
    tdApStep0Name.innerText = apDocsApName0;
    tdApStep1Name.innerText = apDocsApName1;
    tdApStep2Name.innerText = apDocsApName2;
    tdApStep3Name.innerText = apDocsApName3;
    //결재선 승인날짜 채우기
    divApStep0Date.innerText = apDocsApDate0;
    divApStep1Date.innerText = apDocsApDate1;
    divApStep2Date.innerText = apDocsApDate2;
    divApStep3Date.innerText = apDocsApDate3;
  
    tdApStep0Name.appendChild(divApStep0Date);
    tdApStep1Name.appendChild(divApStep1Date);
    tdApStep2Name.appendChild(divApStep2Date);
    tdApStep3Name.appendChild(divApStep3Date);

    //결재 관련 승인여부에 따라, 알맞은 이미지 부여하기
    //결재자 0
    if (apDocsApType0 == "대기" && myCheckId === apDocsApName0.toString()) {
      apStep0Obj.appendChild(buttonTag);
    } else if (apDocsApType0 == "대기") {
      apStep0Obj.innerText = "대기중";
    } else if (apDocsApType0 == "반려") {
      imgTag.src = "img/icons/no.png";
      apStep0Obj.appendChild(imgTag);
    } else if (apDocsApType0 == "승인") {
      imgTag.src = "img/icons/yes.png";
      apStep0Obj.appendChild(imgTag);
    }
    //결재자 1
    if (apDocsApType1 == "대기" && myCheckId === apDocsApName1.toString()) {
      apStep1Obj.appendChild(buttonTag1);
    } else if (apDocsApType1 == "대기") {
      apStep1Obj.innerText = "대기중";
    } else if (apDocsApType1 == "반려") {
      imgTag1.src = "img/icons/no.png";
      apStep1Obj.appendChild(imgTag1);
    } else if (apDocsApType1 == "승인") {
      imgTag1.src = "img/icons/yes.png";
      apStep1Obj.appendChild(imgTag1);
    }
    //결재자 2
    if (apDocsApType2 == "대기" && myCheckId === apDocsApName2.toString()) {
      apStep2Obj.appendChild(buttonTag2);
    } else if (apDocsApType2 == "대기") {
      apStep2Obj.innerHTML = "대기중";
    } else if (apDocsApType2 == "반려") {
      imgTag2.src = "img/icons/no.png";
      apStep2Obj.appendChild(imgTag2);
    } else if (apDocsApType2 == "승인") {
      imgTag2.src = "img/icons/yes.png";
      apStep2Obj.appendChild(imgTag2);
    }
    //결재자 3
    if (apDocsApType3 == "대기" && myCheckId === apDocsApName3.toString()) {
      apStep3Obj.appendChild(buttonTag3);
    } else if (apDocsApType3 == "대기") {
      apStep3Obj.innerText = "대기중";
    } else if (apDocsApType3 == "반려") {
      imgTag3.src = "img/icons/no.png";
      apStep3Obj.appendChild(imgTag3);
    } else if (apDocsApType3 == "승인") {
      imgTag3.src = "img/icons/yes.png";
      apStep3Obj.appendChild(imgTag3);
    }

    //힙의 이름 채우기
    tdAgName.innerText = apDocsAgName;
    console.log(myCheckId === apDocsAgName.toString());
   //합의 관련 승인여부에 따라, 알맞은 이미지 부여하기
    if (apDocsAgType == "대기" && myCheckId === apDocsAgName.toString()) {
      tdAgName.appendChild(agButtonTag);
    } else if (apDocsAgType == "반려") {
      agImgTag.src = "img/icons/no.png";
      tdAgName.appendChild(agImgTag);
    } else if (apDocsAgType == "승인") {
      agImgTag.src = "img/icons/yes.png";
      tdAgName.appendChild(agImgTag);
    }

    //참조 이름 채우기
    tdReName.innerText = apDocsReName;
    //참조 관련 승인여부에 따라, 알맞은 이미지 부여하기
    if (apDocsReType == "대기") {
      spanTag.style.color = "#dfd5f5";
      spanTag.setAttribute("class", "fa fa-question");
      tdReName.appendChild(spanTag);
    } else if (apDocsReType == "승인") {
      spanTag.style.color = "#6A0888";
      spanTag.setAttribute("class", "fa fa-check");
      tdReName.appendChild(spanTag);
    }
  }
  //상세내용 구성요소 채우기 관련 함수 끝


  //참조 버튼 클릭시 실행하는 것 관련
  //참조 승인관련 hover 효과 주기 
  $("#apReferenceName").hover(
    function () {
      $("#apReferenceName>div.fa-question").css("color", "#6A0888");
    },
    function () {
      $("#apReferenceName>div.fa-question").css("color", "#dfd5f5");
    }
  );
  //로그인 아이디 받아오기 
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  var currentLoginId = loginInfoIdObj.innerText;
  //참조 버튼 클릭시 알림창 + ajax 처리 
  $("#apReferenceName").click(function () {
    console.log(myCheckId);
    console.log(apDocsReName.toString());
     if(apDocsReType=="대기" && myCheckId === apDocsReName.toString()){
      console.log("조건 클릭");
      //ajax 관련 내용 넣기
      $.ajax({
        url: "/back/updaterecheck",
        method: "post",
        data: {
          docsNo: tmpDocsBdNo,
          id :currentLoginId,
        },
    
        success: function () {
         
          alert("참조 확인하셨습니다 !");  
          spanTag.style.color = "#6A0888";
          spanTag.setAttribute("class", "fa fa-check");
         
        },
        error: function(request, status, error) {
          alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
      });

     }
  });

  //문서 상세정보 데이터 가지고오는 ajax
  $.ajax({
    url: "/back/showdocsdetail",
    method: "get",
    data: {
      docsNo: tmpDocsBdNo, 
    },
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        //문서 정보 받아오기
        apDocsTitle[i] = e.document_title;
        apDocsType[i] = e.document_type.document_type;
        apDocsNo[i] = e.document_no;
        apDocsDep[i] = e.employee.department.department_title;
        apDocsWriter[i] = e.employee.name;
        apDocsDate[i] = e.draft_date;
        apDocsContent[i] = e.document_content;

        //
        apDocsApName0[i] = e.approvals[0].employee_id.name;
        apDocsApType0[i] = "승인";// 결재자 0번은 무조건 승인처리하므로, 고정값 입력
        apDocsApDate0[i] = e.approvals[0].ap_ap_date;
        apDocsApName1[i] = e.approvals[1].employee_id.name;
        apDocsApType1[i] = e.approvals[1].ap_type.apStatus_type;
        apDocsApDate1[i] = e.approvals[1].ap_ap_date;
        apDocsApName2[i] = e.approvals[2].employee_id.name;
        apDocsApType2[i] = e.approvals[2].ap_type.apStatus_type;
        apDocsApDate2[i] = e.approvals[2].ap_ap_date;
        apDocsApName3[i] = e.approvals[3].employee_id.name;
        apDocsApType3[i] = e.approvals[3].ap_type.apStatus_type;
        apDocsApDate3[i] = e.approvals[3].ap_ap_date;

        apDocsAgName[i] = e.agreement.employee_id.name;
        apDocsAgType[i] = e.agreement.ag_ap_type.apStatus_type;

        apDocsReName[i] = e.reference.employee_id.name;
        apDocsReType[i] = e.reference.re_ap_type.apStatus_type;
      });

      for (var i = 0; i < apDocsType.length; i++) {
        console.log(apDocsType[i]);
      }

      for (var i = 0; i < apDocsTitle.length; i++) {
        createApBdElement(i);
      }
    },
  });

  //코멘트 관련
  var commentId = new Array();//코멘트 id
  var commentDate = new Array();//코멘트 날짜
  var commentCmt = new Array();//코멘트 내용

  var commentObj = document.getElementById("apCommentTbody");//코멘트 내용넣을 tbody객체 받아오기 

  // 코멘트 구성요소 만들기 + 값 채워넣기 
  function createCommentElement(i) {
    var tr = document.createElement("tr");
    var td1 = document.createElement("td");//코멘트 id들어갈곳
    var td2 = document.createElement("td");//코멘트 날짜 들어갈 곳
    var td3 = document.createElement("td");//코멘트 내용 들어갈 곳 

    td1.innerHTML = commentId[i];
    td2.innerHTML = commentCmt[i];
    td3.innerHTML = commentDate[i];

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);

    commentObj.appendChild(tr);
  }

  //코멘트 데이터값 받아오기 ajax
  $.ajax({
    url: "/back/selectbycomments",
    method: "get",
    data: {
      documentNo: tmpDocsBdNo,//local에서 받아온 문서번호 값 
    },

    success: function (responseData) {
      console.log(commentObj);
      console.log(responseData);
      $(responseData).each(function (i, e) {
        commentId[i] = e.employee_id.employee_id;
        console.log(commentId);
        commentDate[i] = e.ap_ap_date;
        console.log(commentDate);
        commentCmt[i] = e.ap_ap_comment;
      });
      console.log(commentId.length);
      for (var i = 0; i < commentId.length; i++) {
        createCommentElement(i);
      }
    },
  });

  
  // 모달 관련 
  var modal = document.getElementById("modalApprovalComment");//모달 전체 객체 
  var confirmBtn = document.getElementById("apCommentConfirmBtn");//저장버튼
  var cancelBtn = document.getElementById("apCommentCancelBtn");//취소버튼

  const openModal = () => {
    modal.classList.remove("hidden");
  };

  const cancelModal = () => {
    modal.classList.add("hidden");
  };

  const confirmModal = () => {
    window.alert("확인완료 되었습니다.");
  };

  buttonTag.addEventListener("click", openModal);
  buttonTag1.addEventListener("click", openModal);
  buttonTag2.addEventListener("click", openModal);
  buttonTag3.addEventListener("click", openModal);
  agButtonTag.addEventListener("click", openModal); //버튼을 띄워줄려면, 만든건 직접적으로 클릭 이벤트
  confirmBtn.addEventListener("click", confirmModal);
  cancelBtn.addEventListener("click", cancelModal);


});
