localStorage.removeItem("apLineName");

//모달창 객체 가져옴
var modalApLineSettingObj = document.querySelector("div#modalApprovalSetting");
//모달창 내 결재 버튼 객체 가져옴
var modalApBtnObj = modalApLineSettingObj.querySelector(
  "button#modalBtnApproval"
);
//모달창 내 합의 버튼 객체 가져옴
var modalAgBtnObj = modalApLineSettingObj.querySelector(
  "button#modalBtnAgreement"
);
//모달창 내 참조 버튼 객체 가져옴
var modalReBtnObj = modalApLineSettingObj.querySelector(
  "button#modalBtnReference"
);
//모달창 내 결재 테이블 객체 가져옴
var modalApTableObj = modalApLineSettingObj.querySelector(
  "table#approvalNameList"
);
//결재선 0번에 관한 tr과 td 객체 가져옴
var modalApZeroObj = modalApTableObj.querySelector("tbody>tr:first-child");
var modalApZeroNameObj = modalApZeroObj.querySelector("td:last-child");
//결재선 1번에 관한 tr과 td 객체 가져옴
var modalApFirstObj = modalApTableObj.querySelector("tbody>tr:nth-child(2)");
var modalApFirstNameObj = modalApFirstObj.querySelector("td:last-child");
//결재선 2번에 관한 tr과 td 객체 가져옴
var modalApSecondObj = modalApTableObj.querySelector("tbody>tr:nth-child(3)");
var modalApSecondNameObj = modalApSecondObj.querySelector("td:last-child");
//결재선 3번에 관한 tr과 td 객체 가져옴
var modalApThirdObj = modalApTableObj.querySelector("tbody>tr:last-child");
var modalApThirdNameObj = modalApThirdObj.querySelector("td:last-child");

//모달창 내 합의 div객체 및 버튼 객체 가져옴
var modalAgObj = document.querySelector("div#agreementBox");
var modalAgNameObj = modalAgObj.querySelector("button#agreementBoxBtn");
//모달창 내 참조 div객체 및 버튼 객체 가져옴
var modalReObj = document.querySelector("div#referenceBox");
var modalReNameObj = modalReObj.querySelector("button#referenceBoxBtn");

var docApZeroNameObj = document.querySelector("td#apApprovalStepName0");
var docApFirstNameObj = document.querySelector("td#apApprovalStepName1");
var docApSecondNameObj = document.querySelector("td#apApprovalStepName2");
var docApThirdNameObj = document.querySelector("td#apApprovalStepName3");
var docAgNameObj = document.querySelector("td#apAgreementName");
var docReNameObj = document.querySelector("td#apReferenceName");

//현재 로그인한 사용자의 이름
var loginInfoNameObj = document.querySelector(
  "div.profileDropdown span.loginName"
);
//모달창 내 사원 관련 table td 객체 모두 가져옴
var apStaffName = document.querySelectorAll("div.card-body table td");

//모달창 내 form 객체 가져옴
// var apSetApLineFormObj = document.querySelector("form.apLineSelectForm");
var apSetApLineSaveBtnObj = document.querySelector("button#apSettingSave");
//staffName 클릭 시 클릭 핸들러, localStorage의 값을 타겟의 innerText값(이름값)으로 설정
function apStaffNameClickHandler(e) {
  localStorage.setItem("apLineName", e.target.innerText);
}

//이벤트 적용
for (var i = 0; i < apStaffName.length; i++) {
  apStaffName[i].addEventListener("click", apStaffNameClickHandler);
}

//각 사원의 이름을 가지고 있는 td객체를 배열로 선언해줌
var modalApStepNameArr = [
  modalApZeroNameObj,
  modalApFirstNameObj,
  modalApSecondNameObj,
  modalApThirdNameObj,
];

//현재 결재 단계를 지정해주는 변수
var apStep = 1;

//로그인한 사원을 작성자로 보고 0번째 결재자를 자신의 이름으로 설정
modalApStepNameArr[0].innerHTML = loginInfoNameObj.innerText;

//결재 버튼 클릭 시 클릭핸들러
//apLineName의 값이 undefined일때는 추가 x, apStep이 4여도 추가 x
//apStep이 4미만이면서 사용자가 선택한 사원이 있는 경우에만 추가하고 apStep 증가
function modalApBtnClickHandler() {
  console.log(apStep);
  if (localStorage.getItem("apLineName") == undefined) {
    alert("선택한 사원이 없습니다");
  } else {
    if (apStep == 4) {
      alert("더이상 추가할 수 없습니다");
    } else {
      modalApStepNameArr[apStep].innerHTML = localStorage.getItem("apLineName");
      apStep += 1;
    }
  }
}

//합의 버튼 클릭 시 클릭 핸들러
//localStorage apLineName의 값이 undefined일때 추가 x
//이미 해당 버튼 내에 이름이 있는 경우에 추가 x
function modalAgBtnClickHandler() {
  if (localStorage.getItem("apLineName") == undefined) {
    alert("선택한 사원이 없습니다");
  } else {
    if (modalAgNameObj.innerText == "") {
      modalAgNameObj.innerText = localStorage.getItem("apLineName");
    } else {
      alert("이미 선택한 사원이 있습니다");
    }
  }
}

//참조 버튼 클릭 시 클릭 핸들러
//localStorage apLineName의 값이 undefined일때 추가 x
//이미 해당 버튼 내에 이름이 있는 경우에 추가 x
function modalReBtnClickHandler() {
  if (localStorage.getItem("apLineName") == undefined) {
    alert("선택한 사원이 없습니다");
  } else {
    if (modalReNameObj.innerText == "") {
      modalReNameObj.innerText = localStorage.getItem("apLineName");
    } else {
      alert("이미 선택한 사원이 있습니다");
    }
  }
}

//합의자 이름이 있는 버튼 클릭 시 클릭 핸들러
//innerText를 초기화해줌
function modalAgNameBtnClickHandler() {
  modalAgNameObj.innerText = "";
}

//참조자 이름이 있는 버튼 클릭 시 클릭 핸들러
//innerText를 초기화해줌
function modalReNameBtnClickHandler() {
  modalReNameObj.innerText = "";
}

function modalSetApLineSubmitHandler() {
  modalApLineSettingObj.classList.add("hidden");
  docApZeroNameObj.innerText = "";
  docApFirstNameObj.innerText = "";
  docApSecondNameObj.innerText = "";
  docApThirdNameObj.innerText = "";
  docAgNameObj.innerText = "";
  docReNameObj.innerText = "";

  docApZeroNameObj.innerText = modalApZeroNameObj.innerText;
  docApFirstNameObj.innerText = modalApFirstNameObj.innerText;
  docApSecondNameObj.innerText = modalApSecondNameObj.innerText;
  docApThirdNameObj.innerText = modalApThirdNameObj.innerText;
  docAgNameObj.innerText = modalAgNameObj.innerText;
  docReNameObj.innerText = modalReNameObj.innerText;
}

//합의, 참조자 이름 있는 버튼 클릭 핸들러 addEvent
modalAgNameObj.addEventListener("click", modalAgNameBtnClickHandler);
modalReNameObj.addEventListener("click", modalReNameBtnClickHandler);

//결재, 합의 ,참조 버튼 클릭 핸듫러 addEvent
modalApBtnObj.addEventListener("click", modalApBtnClickHandler);
modalAgBtnObj.addEventListener("click", modalAgBtnClickHandler);
modalReBtnObj.addEventListener("click", modalReBtnClickHandler);

apSetApLineSaveBtnObj.addEventListener("click", modalSetApLineSubmitHandler);
