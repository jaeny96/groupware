var mainObj = document.querySelector("div.wrapper>div.main");
var mainProfileObj = mainObj.querySelector("div.profileDropdown");
var mainLoginIdObj = mainProfileObj.querySelector("span.loginId");
var mainLoginNameObj = mainProfileObj.querySelector("span.loginName");

var mainContentObj = mainObj.querySelector("main.content");
var mainBdTBodyObj = mainContentObj.querySelector(
  ".card-body tbody.mainBdTbody"
);
var mainApTBodyObj = mainContentObj.querySelector(
  ".card-body tbody.mainApTbody"
);
var mainSkdTBodyObj = mainContentObj.querySelector(
  ".card-body tbody.mainSkdTbody"
);
var mainLeaveTBodyObj = mainContentObj.querySelector(
  ".card-body tbody.mainLeaveTbody"
);
var mainGrantDaysObj = mainLeaveTBodyObj.querySelector("td.mainGrantDays");
var mainUseDaysObj = mainLeaveTBodyObj.querySelector("td.mainUseDays");
var mainRemainDaysObj = mainLeaveTBodyObj.querySelector("td.mainRemainDays");

var mainLoginId = "MSD001";
var mainLoginName = "오지수";

var mainBdId = ["BD1", "BD2"];
var mainBdTitle = ["안녕", "안녕22"];
var mainBdWriter = ["오지수", "박미정"];
var mainBdDate = ["2021-07-07", "2021-07-07"];

var mainApId = ["회람", "휴가"];
var mainApTitle = ["회람임", "휴가보내주세요"];
var mainApDate = ["2021-07-06", "2021-07-07"];

var mainSkdId = ["SKD1", "SKD2"];
var mainSkdDate = ["15:00", "16:30"];
var mainSkdTitle = ["회의", "프론트 끝"];

var mainGrantLeave = 15;
var mainRemainLeave = 10;

function insertMainLeaveElement() {
  mainGrantDaysObj.innerHTML = mainGrantLeave + "일";
  mainUseDaysObj.innerHTML = mainGrantLeave - mainRemainLeave + "일";
  mainRemainDaysObj.innerHTML = mainRemainLeave + "일";
}

function createMainSkdElement(i) {
  var tr = document.createElement("tr");
  var tdSkdDate = document.createElement("td");
  var tdSkdDateCenter = document.createElement("center");
  tdSkdDateCenter.innerHTML = mainSkdDate[i];
  tdSkdDate.appendChild(tdSkdDateCenter);
  var tdSkdTitle = document.createElement("td");
  tdSkdTitle.innerHTML = mainSkdTitle[i];
  tdSkdTitle.setAttribute("style", "width:80%");
  // var tdSkdTitleA = document.createElement("a");
  // tdSkdTitleA.innerHTML = mainSkdTitle[i];
  // tdSkdTitleA.setAttribute("class", "sidebar-link-js");
  // tdSkdTitleA.setAttribute("id", mainSkdId[i]);
  // tdSkdTitleA.setAttribute("href", "schedule-detail.html");
  // tdSkdTitleA.setAttribute("style", "color:black");
  // tdSkdTitle.setAttribute("style", "width:60%");
  // tdSkdTitle.appendChild(tdSkdTitleA);

  tr.appendChild(tdSkdDate);
  tr.appendChild(tdSkdTitle);

  mainSkdTBodyObj.appendChild(tr);
}

function createMainApElement(i) {
  var tr = document.createElement("tr");
  var tdApId = document.createElement("td");
  tdApId.innerHTML = mainApId[i];
  var tdApTitle = document.createElement("td");
  var tdApTitleA = document.createElement("a");
  tdApTitleA.innerHTML = mainApTitle[i];
  tdApTitleA.setAttribute("class", "sidebar-link-js");
  tdApTitleA.setAttribute("id", mainApId[i]);
  tdApTitleA.setAttribute("href", "approval-detail.html");
  tdApTitleA.setAttribute("style", "color:black");
  tdApTitle.setAttribute("style", "width:60%");
  tdApTitle.appendChild(tdApTitleA);
  var tdApDate = document.createElement("td");
  tdApDate.innerHTML = mainApDate[i];

  tr.appendChild(tdApId);
  tr.appendChild(tdApTitle);
  tr.appendChild(tdApDate);

  mainApTBodyObj.appendChild(tr);
}

function createMainBdElement(i) {
  var tr = document.createElement("tr");
  var tdBdTitle = document.createElement("td");
  var tdBdTitleA = document.createElement("a");
  tdBdTitleA.innerHTML = mainBdTitle[i];
  tdBdTitleA.setAttribute("class", "sidebar-link-js");
  tdBdTitleA.setAttribute("id", mainBdId[i]);
  tdBdTitleA.setAttribute("href", "board-detail.html");
  tdBdTitleA.setAttribute("style", "color:black");
  tdBdTitle.setAttribute("style", "width:60%");
  tdBdTitle.appendChild(tdBdTitleA);

  var tdBdWriter = document.createElement("td");
  tdBdWriter.innerHTML = mainBdWriter[i];
  var tdBdDate = document.createElement("td");
  tdBdDate.innerHTML = mainBdDate[i];

  tr.appendChild(tdBdTitle);
  tr.appendChild(tdBdWriter);
  tr.appendChild(tdBdDate);

  mainBdTBodyObj.appendChild(tr);
}

function insertProfileInfo() {
  mainLoginIdObj.innerHTML = mainLoginId;
  mainLoginNameObj.innerHTML = mainLoginName;
}

function init() {
/*  insertProfileInfo();

  for (var i = 0; i < mainBdId.length; i++) {
    createMainBdElement(i);
  }

  for (var i = 0; i < mainApId.length; i++) {
    createMainApElement(i);
  }

  for (var i = 0; i < mainSkdId.length; i++) {
    createMainSkdElement(i);
  }

  insertMainLeaveElement();*/
}

init();
