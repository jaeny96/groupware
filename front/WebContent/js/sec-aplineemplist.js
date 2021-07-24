$(function () {
  //결재선: 부서별 사원 리스트 생성
  //html 갖고오기
  var apLineTableObject = document.getElementById("apLineCardbody");
  //임의데이터
  var apLineStaffName = new Array();
  var apLineStaffId = new Array();

  var tbody = document.querySelector(".SECTbody");
  var secLineBtnObj = document.querySelector("button.apLineSec");

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
    td.addEventListener("click", apStaffNameClickHandler);
    tr.appendChild(td);
    tbody.appendChild(tr);
  }

  var backurlSecEmpList = "/back/showaplinestafflist";

  $.ajax({
    url: backurlSecEmpList,
    method: "get",
    data: {
      apLineDeptName: secLineBtnObj.innerText.trim(),
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
