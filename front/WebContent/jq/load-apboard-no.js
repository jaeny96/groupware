  // 이클립스에서 받아오는 url
  var backUrlApDocsList = "/back/showapdocsstatus";

$(function () {
  //테이블 객체
  var DocumentTableObj = document.getElementById("apDocumentTable");
  //table바디 객체
  var tBodyObject = null;
  //table에 적용시킬 변수들 : list 형식
  var apBdNo = new Array();
  var apBdTitle = new Array();
  var apBdEmp = new Array();
  var apBdEmpName = new Array();
  var apBdDate = new Array();
  var apBdStatus = new Array();
  var apBdCheck = new Array();
  var tBodyObject = document.getElementById("apDocumentTbody");

    //tbody객체 만들어주는 함수
    function createTbodyElement() {
      tBodyObject = document.createElement("tbody");
      tBodyObject.setAttribute("id", "apDocumentTbody");
      DocumentTableObj.appendChild(tBodyObject);
    }
  
    function removeElement(target) {
      if (target != null) {
        target.remove();
      }
    }
  //기존 객체 및 배열 지워주기
  function emptyBdElement(target) {
    removeElement(target);
    apBdNo = [];
    apBdTitle = [];
    apBdEmp = [];
    apBdEmpName = [];
    apBdDate = [];
    apBdStatus = [];
    apBdCheck = [];
  }

  var apSearchFormObj = document.getElementById("apSearchFormObj");
  var apGroupButton = document.getElementById("apSearchGroup");
  var apInputSearch = document.querySelector("input[type=text]");
  var apSearchButton = document.getElementById("apSearchButton");
  var apSearchCategory = null;

  function categoryHandler(e) {
    if (e.target.id == "apSearchTitle") {
      apInputSearch.setAttribute("placeholder", "제목으로 검색");
      apSearchCategory = "title";
    } else {
      apInputSearch.setAttribute("placeholder", "내용으로 검색");
      apSearchCategory = "content";
    }
  }

  
  function bdSearchFromSubmitHandler(e) {
    if (apSearchCategory == null) {
      alert("검색값을 설정해주세요");
    } else {
      $.ajax({
        url: "/back/usersearchdocs",
        method: "get",
        data: {
          id: "DEV001",
          searchCategory: apSearchCategory,
          search: apInputSearch.value,
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

          console.log($titleObj);

          $titleObj.click(function (e) {
            localStorage.setItem("apDocumentNum", e.target.id); //클릭시 a링크에 담겨있는 문서값 저장
            $(
              'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="approval-detail.html"]'
            ).trigger("click");
          });
        },
      });
    }
    e.preventDefault();
  }
  apGroupButton.addEventListener("click", categoryHandler);
  apSearchFormObj.addEventListener("submit", bdSearchFromSubmitHandler);


  //tbody내용 생성
  function createApBdElement(i) {
    //	createTbodyElement();
    var tBodyObject = document.getElementById("apDocumentTbody");
    var tr = document.createElement("tr");
    var th = document.createElement("th");
    th.setAttribute("scope", "row");
    th.innerHTML = i + 1;
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
    var tdCheck = document.createElement("td");
    if (apBdCheck[i] == "대기"||apBdCheck[i]==null) {
      console.log(tdCheck[i]);
      tdCheck.innerHTML = "미확인";
    } else if (apBdCheck[i] != "대기") {
      tdCheck.innerHTML = "확인";
    }

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

  $.ajax({
    url: backUrlApDocsList,
    method: "get",
    data: {
      id: "DEV001",
      status: "반려",
    },
    success: function (responseData) {
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

      for (var i = 0; i < apBdTitle.length; i++) {
        createApBdElement(i);
      }
      $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");

      console.log($titleObj);

      $titleObj.click(function (e) {
        localStorage.setItem("apDocumentNum", e.target.id); //클릭시 a링크에 담겨있는 문서값 저장
        $(
          'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="approval-detail.html"]'
        ).trigger("click");
      });
    },
  });
});

//dropdown동작 관련
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}
window.onclick = function (event) {
  if (!event.target.matches(".dropbtn")) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      openDropdown = dropdowns[i];
      if (openDropdown.classList.contains("show")) {
        openDropdown.classList.remove("show");
      }
    }
  }
};
