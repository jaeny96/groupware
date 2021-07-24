$(function () {
  //결재선: 부서별 사원 리스트 생성
  //html 갖고오기
  var apLineTableObject = document.getElementById("apLineCardbody");
  //임의데이터
  var apLineStaffName = new Array();
  var apLineStaffId = new Array();
  var tbody = document.querySelector(".SVCTbody");
  var svcLineBtnObj = document.querySelector("button.apLineSvc");

  function apStaffNameClickHandler(e) {
    localStorage.setItem("apLineName", e.target.innerText);
    localStorage.setItem("apLineId", e.target.id);
  }

  function createApLineElement(i) {
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    tr.setAttribute("class", "listInner");
    tr.setAttribute("onclick", "javascript:getNameForApLine(this);");
    td.setAttribute("class", "apStaffName");
    td.innerHTML = apLineStaffName[i];
    td.setAttribute("id", apLineStaffId[i]);
    td.addEventListener("click", apStaffNameClickHandler);
    tr.appendChild(td);
    tbody.appendChild(tr);
  }

  var backurlSvcEmpList = "/back/showaplinestafflist";

  $.ajax({
    url: backurlSvcEmpList,
    method: "get",
    data: {
      apLineDeptName: svcLineBtnObj.innerText.trim(),
    },
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        apLineStaffName[i] = e.name;
        apLineStaffId[i] = e.employee_id;
      });
      for (var i = 0; i < apLineStaffName.length; i++) {
        createApLineElement(i);
      }
    },
  });
});
