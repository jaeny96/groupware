//board 페이지에 적용
$(function () {
  //테이블 객체
  // var DocumentTableObj = document.getElementById("apDocumentTableList");
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
    if (apBdCheck[i] == "대기") {
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
  // 이클립스에서 받아오는 url
  var backUrlApDocsList = "/back/showapdocsstatus";

  $.ajax({
    url: backUrlApDocsList,
    method: "get",
    data: {
      id: "DEV001",
      status: "승인",
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
