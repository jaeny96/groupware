//결재선: 부서별 사원 리스트 생성
//html 갖고오기
var apLineTableObject = document.getElementById("apLineCardbody");
//임의데이터
var apLineStaffName = ["이주헌", "임창균", "정연주"];
var tbody = document.querySelector(".MSDTbody");
function createApLineElement(i) {
  var tr = document.createElement("tr");
  var td = document.createElement("td");
  tr.setAttribute("class", "listInner");
  tr.setAttribute("onclick", "javascript:getNameForApLine(this);");
  td.setAttribute("class", "apStaffName");
  td.innerHTML = apLineStaffName[i];
  tr.appendChild(td);
  tbody.appendChild(tr);
}

function init() {
  for (var i = 0; i < apLineStaffName.length; i++) {
    createApLineElement(i);
  }
}

init();
