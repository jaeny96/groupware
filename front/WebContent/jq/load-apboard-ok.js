$(function () {
  //테이블 객체 변수
  var DocumentTableObj = document.getElementById("apDocumentTable");
  //table바디 객체 변수 
  var tBodyObject = null;
  //table에 적용시킬 변수들 : list 형식
  var apMyStatus = new Array();
  var apBdNo = new Array();
  var apBdTitle = new Array();
  var apBdEmp = new Array();
  var apBdEmpName = new Array();
  var apBdDate = new Array();
  var apBdStatus = new Array();
  var apBdCheck = new Array();

  var $content = $("main.content");

  //tbody객체 만들어주는 함수
  function createTbodyElement() {
    tBodyObject = document.createElement("tbody");
    tBodyObject.setAttribute("id", "apDocumentTbody");
    DocumentTableObj.appendChild(tBodyObject);
  }

  //구성요소 지우기 위한 함수 : tBody
  function removeElement(target) {
    if (target != null) {
      target.remove();
    }
  }

  //기존 객체 및 배열 지워주기
  function emptyBdElement(target) {
    removeElement(target);
    apMyStatus = [];
    apBdNo = [];
    apBdTitle = [];
    apBdEmp = [];
    apBdEmpName = [];
    apBdDate = [];
    apBdStatus = [];
    apBdCheck = [];
  }

////////////////////////검색 카테고리 선택 관련

  //검색 카테고리 버튼 관련 div로 묶어놓은 거 객체 변수 
  var apBtnGroupObj = document.querySelector("div.btn-group-sm");
  //검색 카테고리 버튼 클릭했을때 나오는 메뉴 객체 변수
  var apCategoryObj = apBtnGroupObj.querySelector("div.searchDropdown");
  //검색 카테고리 버튼 객체 변수 
  var apSearchCategoryBtnObj = document.querySelector("button.searchBtn");
  //검색 카테고리 form 객체 변수 
  var apSearchFormObj = document.querySelector("form.searchForm");
  //검색 카테고리 버튼 + form 이랑 다 div로 묶어놓은 거 객체 변수
  var apInputGroupObj = document.querySelector("div.input-group");
  //사용자가 선택한 카테고리 값 받아오는 변수 
  var apSearchCategory = null;
  //사용자가 입력하는 글자 받아오는 input 객체 변수 
  var apSearchObj = apInputGroupObj.querySelector("input[type=text]");

  //검색 카테고리 선택시 발생하는 함수
  function categoryHandler(e) {
    if (e.target.id == "apCategoryTitle") {//제목 선택시
      apSearchCategoryBtnObj.innerHTML = "제목";//버튼 이름 바꾸기
      apSearchObj.setAttribute("placeholder", "제목으로 검색하기");//입력란
      apSearchCategory = "title";//변수에 담기
    } else if (e.target.id == "apCategoryWriter") {//내용 선택시
      apSearchCategoryBtnObj.innerHTML = "내용";
      apSearchObj.setAttribute("placeholder", "내용으로 검색하기");
      apSearchCategory = "content";
    }
  }

  //검색 버튼 클릭시 발생하는 ajax관련 함수 
  function apSearchFormSubmitHandler(e) {
    if (apSearchCategory == null) {
      alert("카테고리가 지정되지 않았습니다");
    } else {
      $.ajax({
        url: "/back/usersearchdocs",
        method: "get",
        data: {
          searchCategory: apSearchCategory,//제목 or 내용
          searchWord: apSearchObj.value,//검색 값
          status: "승인",//상태값
        },
        success: function (responseData) {
          emptyBdElement(tBodyObject);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            apMyStatus[i] = e.state;
            apBdNo[i] = e.document_no;
            apBdTitle[i] = e.document_title;
            apBdEmp[i] = e.employee.employee_id;
            apBdEmpName[i] = e.employee.name;
            apBdDate[i] = e.draft_date;
            apBdStatus[i] = e.document_type.document_type;
            apBdCheck[i] = e.approval.ap_type.apStatus_type;
          });

          for (var i = 0; i < apBdTitle.length; i++) {
            createApBdElement(i);
          }

          //링크관련 클릭 이벤트 재생성 
          $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");

          console.log($titleObj);

          $titleObj.click(function (e) {
            localStorage.setItem("apDocumentNum", e.target.id); //클릭시 a링크에 담겨있는 문서값 저장
            var href = $(this).attr("href");
            console.log(href);
            switch (href) {
              case "approval-detail.html":
                $content.load(href, function (responseTxt, statusTxt, xhr) {
                  if (statusTxt == "error")
                    alert("Error: " + xhr.status + ": " + xhr.statusText);
                });
                break;
            }
            return false;
          });
        },
      });
    }
    e.preventDefault();
  }

  //검색 카테고리 이벤트 리스너 설정
  apCategoryObj.addEventListener("click", categoryHandler);
  apSearchFormObj.addEventListener("submit", apSearchFormSubmitHandler);

 //////////////////////////////문서상태 선택 관련

  //문서상태 선택값 답는 변수
  var nowStatus = "";
  //문서상태 선택 dropDown 객체 변수
  var $categoryObj = $("div.apCategoryDropdown");
  //문서상태 선택 dropDown 객체 변수
  var apChangeStatusBtnObj = document.getElementById("apDropDownStatusGroup");

  //문서상태 선택 시 nowStatus변수에 값 설정시키는 함수
  function statusHandler(e) {
    if (e.target.id == "apDocumentStatusOk") {//문서상태 : 확인일때
      apChangeStatusBtnObj.innerHTML = "확인";
      nowStatus = "확인";
      apStatusPickSubmitHandler(e);
    }
    if (e.target.id == "apDocumentStatusNo") {//문서상태 : 미확인일때
      apChangeStatusBtnObj.innerHTML = "미확인";
      nowStatus = "미확인";
      apStatusPickSubmitHandler(e);
    }
    if (e.target.id == "apDocumentStatusAll") {//문서상태 : 모든문서일때 
      apChangeStatusBtnObj.innerHTML = "모든문서";
      nowStatus = "모든문서";
      apStatusPickSubmitHandler(e);
    }
  }
 
  //문서상태 선택시, 토글 띄우기 
  apChangeStatusBtnObj.addEventListener("click", function () {
    $categoryObj.toggle();
  });
  //토글 내부 구성요소 클릭시 핸들러 함수 작동
  $categoryObj.click(statusHandler);

  //문서상태 선택했을때 발동하는 핸들러
  function apStatusPickSubmitHandler(e) {
    if (e.target.id == "apDocumentStatusAll") {//모든 문서일때
      //trigger로 발생시키고 싶었지만 , 클릭시에만 보이는 구성요소는 작동 안되는지 안되네유 ㅠ 저의 한계,, 
      //trigger로 하고싶다 하시는 분은 밑에 찾아놨으니 이걸 이용해서 해보세야,,
      // var test = document.querySelector(
      //   "div.wrapper>nav.sidebar>div.js-simplebar>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div.simplebar-content-wrapper>div.simplebar-content>ul.sidebar-nav>li.sidebar-item>ul.ml-3>li.sidebar-item>ul.sidebar-dropdown>li.sidebar-item>a"
      // );
      // console.log(test);
      $.ajax({
        url: "/back/showapdocsstatus",
        method: "get",
        data: {
          status: "승인",
        },
        success: function (responseData) {
          emptyBdElement(tBodyObject);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            console.log(i + "," + e);
            apBdNo[i] = e.document_no;
            apBdTitle[i] = e.document_title;
            apBdEmp[i] = e.employee.employee_id;
            apBdEmpName[i] = e.employee.name;
            apBdDate[i] = e.draft_date;
            apBdStatus[i] = e.document_status.document_type;
            apBdCheck[i] = e.approval.ap_type.apStatus_type;
          });
    
          //받아온 데이터 만큼 구성요소 생성 
          for (var i = 0; i < apBdTitle.length; i++) {
            createApBdElement(i);
          }
          $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");
    
          console.log($titleObj);
    
          $titleObj.click(function (e) {
            localStorage.setItem("apDocumentNum", e.target.id); //클릭시 a링크에 담겨있는 문서값 저장
            var href = $(this).attr("href");
            console.log(href);
            switch (href) {
              case "approval-detail.html":
                $content.load(href, function (responseTxt, statusTxt, xhr) {
                  if (statusTxt == "error")
                    alert("Error: " + xhr.status + ": " + xhr.statusText);
                });
                break;
            }
            return false;
          });
        },
      });
    } else {
      //확인 or 미확인을 선택했을때 
      $.ajax({
        url: "/back/selectcheckpick",
        method: "get",
        data: {
          check: nowStatus,//확인 or 미확인 
          status: "승인",
        },
        success: function (responseData) {
          emptyBdElement(tBodyObject);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            apBdNo[i] = e.document_no;
            apBdTitle[i] = e.document_title;
            apBdEmp[i] = e.employee.employee_id;
            apBdEmpName[i] = e.employee.name;
            apBdDate[i] = e.draft_date;
            apBdStatus[i] = e.document_type.document_type;
            apBdCheck[i] = e.approval.ap_type.apStatus_type;
          });

          for (var i = 0; i < apBdTitle.length; i++) {
            createApBdElement(i);
          }
          $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");

          $titleObj.click(function (e) {
            localStorage.setItem("apDocumentNum", e.target.id);
            var href = $(this).attr("href");
            switch (href) {
              case "approval-detail.html":
                $content.load(href, function (responseTxt, statusTxt, xhr) {
                  if (statusTxt == "error")
                    alert("Error: " + xhr.status + ": " + xhr.statusText);
                });
                break;
            }
            return false;
          });
        },
      });
    }
    e.preventDefault();
  }

 //목록 구성요소 만드는 함수 
 function createApBdElement(i) {
  var apDocsNo = document.getElementById("apDocsNo");
  var tr = document.createElement("tr");
  var th = document.createElement("th");
  th.setAttribute("scope", "row");

  //테이블 맨 첫번째칸 관련
  if (apMyStatus[i] != null) {//검색시에만 작동
    th.innerHTML = apMyStatus[i];//결재서류 or 기안 서류 값 넣기 
    apDocsNo.innerHTML = "서류상태";//thead이름 바꾸기  
  } else {
    th.innerHTML = i + 1;//문서 번호 순서대로 넣기 
    apDocsNo.innerHTML = "확인";//thead이름 바꾸기 
  }

  //제목의 링크연결 
  var tdTitle = document.createElement("td");
  var a = document.createElement("a");
  a.setAttribute("class", "sidebar-link-js");
  a.setAttribute("style", "color: black");
  a.setAttribute("id", apBdNo[i]);
  a.setAttribute("href", "approval-detail.html");
  a.innerText = apBdTitle[i];

  var tdDocsNo = document.createElement("td");
  tdDocsNo.innerHTML = apBdNo[i];
  var tdEmployee = document.createElement("td");
  tdEmployee.innerHTML = apBdEmp[i];
  var tdWriter = document.createElement("td");
  tdWriter.innerHTML = apBdEmpName[i];
  var tdDate = document.createElement("td");
  tdDate.innerHTML = apBdDate[i];
  var tdStatus = document.createElement("td");
  tdStatus.innerHTML = apBdStatus[i];

  //승인, 반려값일때는 확인 값으로 넣기 위해 
  var tdCheck = document.createElement("td");
  if (apBdCheck[i] == "대기" || apBdCheck[i] == null) {
    console.log(tdCheck[i]);
    tdCheck.innerHTML = "미확인";
  } else if (apBdCheck[i] != "대기") {
    tdCheck.innerHTML = "확인";
  }

  //내가 올린 문서만 색 다르게 설정
  if (
    apBdEmp[i] === localStorage.getItem("loginInfo") &&
    apBdCheck[i] === "확인"
  ) {
    th.setAttribute("style", "background-color: #dfd5f5");
  }

  //구성요소 연결하기
  tdTitle.appendChild(a);
  tr.appendChild(th);
  tr.appendChild(tdDocsNo);
  tr.appendChild(tdTitle);
  tr.appendChild(tdEmployee);
  tr.appendChild(tdWriter);
  tr.appendChild(tdDate);
  tr.appendChild(tdStatus);
  tr.appendChild(tdCheck);
  tBodyObject.appendChild(tr);
}


  //첫 화면 조회하는 ajax
  $.ajax({
    url: "/back/showapdocsstatus",
    method: "get",
    data: {
      status: "승인",
    },
    success: function (responseData) {
      emptyBdElement(tBodyObject);
      createTbodyElement();
      $(responseData).each(function (i, e) {
        console.log(i + "," + e);
        apBdNo[i] = e.document_no;
        apBdTitle[i] = e.document_title;
        apBdEmp[i] = e.employee.employee_id;
        apBdEmpName[i] = e.employee.name;
        apBdDate[i] = e.draft_date;
        apBdStatus[i] = e.document_status.document_type;
        apBdCheck[i] = e.approval.ap_type.apStatus_type;
      });

      //받아온 데이터 만큼 구성요소 생성 
      for (var i = 0; i < apBdTitle.length; i++) {
        createApBdElement(i);
      }
      $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");

      console.log($titleObj);

      $titleObj.click(function (e) {
        localStorage.setItem("apDocumentNum", e.target.id); //클릭시 a링크에 담겨있는 문서값 저장
        var href = $(this).attr("href");
        console.log(href);
        switch (href) {
          case "approval-detail.html":
            $content.load(href, function (responseTxt, statusTxt, xhr) {
              if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
            });
            break;
        }
        return false;
      });
    },
  });
});
