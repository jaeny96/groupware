$(function () {
  // 이클립스에서 받아오는 url : 맨 위에 있음
  var backUrlApDocsList = "/back/showdocsdetail";
  var bacKUrlCheck = "/back/checkuser";

  //구성요소 받아오기
  var tdDetailType = document.getElementById("apDocumentDetailType");
  var tdDetailNo = document.getElementById("apDocumentDetailNo");
  var tdDetailDep = document.getElementById("apDocumentDetailDep");
  var tdDetailWriter = document.getElementById("apDocumentDetailWriter");
  var tdDetailDate = document.getElementById("apDocumentDetailDate");

  var divDetailTitle = document.getElementById("apDocumentDetailTitle");
  var divDetailContent = document.getElementById("apDocumentContent");

  var apStep0Obj = document.getElementById("apApprovalStep0");
  var apStep1Obj = document.getElementById("apApprovalStep1");
  var apStep2Obj = document.getElementById("apApprovalStep2");
  var apStep3Obj = document.getElementById("apApprovalStep3");

  //승인테이블 이름
  var tdApStep0Name = document.getElementById("apApprovalStepName0");
  var tdApStep1Name = document.getElementById("apApprovalStepName1");
  var tdApStep2Name = document.getElementById("apApprovalStepName2");
  var tdApStep3Name = document.getElementById("apApprovalStepName3");
  var divApStep0Date = document.getElementById("apApprovalStepDate0");
  var divApStep1Date = document.getElementById("apApprovalStepDate1");
  var divApStep2Date = document.getElementById("apApprovalStepDate2");
  var divApStep3Date = document.getElementById("apApprovalStepDate3");
  //합의 테이블
  var tdAgName = document.getElementById("apAgreementName");
  //참조 테이블
  var tdReName = document.getElementById("apReferenceName");
  //이미지 or 버튼
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

  var spanTag = document.createElement("div");

  //받아올 데이터들
  var tmpDocsBdNo = localStorage.getItem("apDocumentNum"); //문서번호
  console.log(tmpDocsBdNo);
  var apDocsType = new Array();
  var apDocsNo = new Array();
  var apDocsDep = new Array();
  var apDocsWriter = new Array();
  var apDocsDate = new Array();

  var apDocsTitle = new Array();
  var apDocsContent = new Array();

  var apDocsApName0 = new Array();
  var apDocsApDate0 = new Array();
  var apDocsApType0 = new Array();
  var apDocsApName1 = new Array();
  var apDocsApDate1 = new Array();
  var apDocsApType1 = new Array();
  var apDocsApName2 = new Array();
  var apDocsApDate2 = new Array();
  var apDocsApType2 = new Array();
  var apDocsApName3 = new Array();
  var apDocsApDate3 = new Array();
  var apDocsApType3 = new Array();

  var apDocsAgName = new Array();
  var apDocsAgType = new Array();

  var apDocsReName = new Array();
  var apDocsReType = new Array();
  var myCheckId = null;
  //현재 로그인 이름
  var uidName = document.querySelector(
    "div.wrapper>div.main>nav.navbar-expand>div.navbar-collapse>ul.navbar-nav>li.nav-item>div.profileDropdown>div.dropdown-item>span"
  );
  console.log(uidName);

  //내가 승인해야할 부분
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

  function createApBdElement(i) {
    //문서정보 채우기
    tdDetailType.innerText = apDocsType;
    tdDetailNo.innerText = apDocsNo;
    tdDetailDep.innerText = apDocsDep;
    tdDetailWriter.innerText = apDocsWriter;
    tdDetailDate.innerText = apDocsDate;
    divDetailTitle.innerText = apDocsTitle;
    divDetailContent.innerText = apDocsContent;
    //결재선 채우기
    tdApStep0Name.innerText = apDocsApName0;
    tdApStep1Name.innerText = apDocsApName1;
    tdApStep2Name.innerText = apDocsApName2;
    tdApStep3Name.innerText = apDocsApName3;

    divApStep0Date.innerText = apDocsApDate0;
    divApStep1Date.innerText = apDocsApDate1;
    divApStep2Date.innerText = apDocsApDate2;
    divApStep3Date.innerText = apDocsApDate3;

    tdApStep0Name.appendChild(divApStep0Date);
    tdApStep1Name.appendChild(divApStep1Date);
    tdApStep2Name.appendChild(divApStep2Date);
    tdApStep3Name.appendChild(divApStep3Date);

    //승인 문서내용 승인or반려 검사
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

    //합의
    tdAgName.innerText = apDocsAgName;
    console.log(myCheckId === apDocsAgName.toString());
    if (apDocsAgType == "대기" && myCheckId === apDocsAgName.toString()) {
      tdAgName.appendChild(agButtonTag);
    } else if (apDocsAgType == "반려") {
      agImgTag.src = "img/icons/no.png";
      tdAgName.appendChild(agImgTag);
    } else if (apDocsAgType == "승인") {
      agImgTag.src = "img/icons/yes.png";
      tdAgName.appendChild(agImgTag);
    }

    //참조
    tdReName.innerText = apDocsReName;
    if (apDocsReType == "대기") {
      console.log("대기");
      spanTag.style.color = "#dfd5f5";
      spanTag.setAttribute("class", "fa fa-question");
      tdReName.appendChild(spanTag);
    } else if (apDocsReType == "승인") {
      console.log("노대기");
      spanTag.style.color = "#6A0888";
      spanTag.setAttribute("class", "fa fa-check");
      tdReName.appendChild(spanTag);
    }
  }

  //참조 승인 hover 효과
  $("#apReferenceName").hover(
    function () {
      console.log("위에 올라옹");
      $("#apReferenceName>div.fa-question").css("color", "#6A0888");
    },
    function () {
      $("#apReferenceName>div.fa-question").css("color", "#dfd5f5");
    }
  );

  //참조 승인 클릭 이벤트
  $("#apReferenceName").click(function () {
    console.log("클릭");
    alert("참조 확인하셨습니다 !");
    spanTag.style.color = "#6A0888";
    spanTag.setAttribute("class", "fa fa-check");
    //ajax 관련 내용 넣기
  });

  var modal = document.getElementById("modalApprovalComment");
  var confirmBtn = document.getElementById("apCommentConfirmBtn");

  console.log(confirmBtn);
  var cancelBtn = document.getElementById("apCommentCancelBtn");

  const openModal = () => {
    modal.classList.remove("hidden");
    console.log("열림");
  };

  const cancelModal = () => {
    modal.classList.add("hidden");
    console.log("닫힘");
  };

  const confirmModal = () => {
    window.alert("확인완료 되었습니다.");
    console.log("ol");
  };

  buttonTag.addEventListener("click", openModal);
  buttonTag1.addEventListener("click", openModal);
  buttonTag2.addEventListener("click", openModal);
  buttonTag3.addEventListener("click", openModal);
  agButtonTag.addEventListener("click", openModal); //버튼을 띄워줄려면, 만든건 직접적으로 클릭 이벤트
  confirmBtn.addEventListener("click", confirmModal);
  cancelBtn.addEventListener("click", cancelModal);

  //문서 상세정보 가지고오는 ajax
  $.ajax({
    url: backUrlApDocsList,
    method: "get",
    data: {
      docsNo: tmpDocsBdNo, //tmp들어갈자리  LE-휴가-20210624-0001 tmpDocsBdNo
    },
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        apDocsTitle[i] = e.document_title;
        apDocsType[i] = e.document_type.document_type;
        apDocsNo[i] = e.document_no;
        apDocsDep[i] = e.employee.department.department_title;
        apDocsWriter[i] = e.employee.name;
        apDocsDate[i] = e.draft_date;
        apDocsContent[i] = e.document_content;

        apDocsApName0[i] = e.approvals[0].employee_id.name;
        apDocsApType0[i] = "승인";
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
  var commentId = new Array();
  var commentDate = new Array();
  var commentCmt = new Array();

  var tableObj = document.getElementById("apDocumentTable");
  var commentObj = document.getElementById("apCommentTbody");

  function createCommentElement(i) {
    var tr = document.createElement("tr");
    var td1 = document.createElement("td");
    var td2 = document.createElement("td");
    var td3 = document.createElement("td");

    td1.innerHTML = commentId[i];
    console.log(td1);
    td2.innerHTML = commentCmt[i];
    td3.innerHTML = commentDate[i];

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);

    commentObj.appendChild(tr);
  }

  $.ajax({
    url: "/back/selectbycomments",
    method: "get",
    data: {
      documentNo: tmpDocsBdNo,
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
});
