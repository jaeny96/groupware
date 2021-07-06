//옆에 메뉴 객체
var navObj = document.querySelector("li.navObj");
//메뉴의 드롭다운 객체
var ulDeptObj = navObj.querySelector("ul.sidebar-dropdown");
//내용 담는 객체
var contentObj = document.querySelector("div.deptContent");
//내용담는 객체의 헤더 객체
var empHeaderObj = contentObj.querySelector("h5.empHeader");
//내용담는 객체의 내용
var empBodyObj = contentObj.querySelector("div.empBody");

var empContentObj = null;

//불러온 부서명
var deptArr = ["경영지원실", "기획개발실"];
//불러온 부서별 사원 수
var deptCntArr = [1, 1];
//불러온 부서번호
var deptIdArr = ["MSD", "DEV"];
//불러온 부서별 사원 이름명
var empArr = ["이지은", "손현우"];
//불러온 부서별 사원 직책명
var positionArr = ["책임", "선임"];
//불러온 사원의 사번
var empIdArr = ["MSD001", "MSD002"];

function empClickHandler(e) {
  e.target.setAttribute("id", "open");
}

function removeEmpElement(target) {
  target.remove();
}

//부서별 사원 내용 생성
function createEmpElement(i) {
  var divIcon = document.createElement("div");
  divIcon.setAttribute("class", "fa fa-user fa-3x mr-3");
  var divContent = document.createElement("div");
  divContent.setAttribute("class", "flex-grow-1 mr-2");
  divContent.setAttribute("id", empIdArr[i]);
  var small = document.createElement("small");
  small.setAttribute("class", "text-muted");
  small.innerText = positionArr[i];
  divContent.innerHTML = empArr[i] + "<br/>";

  divContent.appendChild(small);
  divContent.addEventListener("click", empClickHandler);

  empContentObj.appendChild(divIcon);
  empContentObj.appendChild(divContent);
}

function createEmpElementBig() {
  empContentObj = document.createElement("div");
  empContentObj.setAttribute("class", "d-flex align-items-start");
  for (var i = 0; i < empArr.length; i++) {
    createEmpElement(i);
  }
  empBodyObj.appendChild(empContentObj);
}

//dept에 맞는 사원 조회
function selectEmpElement(dept) {
  if (dept == "MSD") {
    if (empContentObj != null) {
      removeEmpElement(empContentObj);
    }
    createEmpElementBig();
  }
}

function deptClickHandler(e) {
  var target = e.target;
  empHeaderObj.innerText = e.target.innerHTML;
  selectEmpElement(e.target.id);
}

//부서 내비게이션 생성 함수
function createDeptElement(i) {
  var li = document.createElement("li");
  li.setAttribute("class", "sidebar-item");
  var a = document.createElement("a");
  a.setAttribute("class", "sidebar-link-js");
  a.setAttribute("id", deptIdArr[i]);
  a.innerHTML = deptArr[i] + "(" + deptCntArr[i] + ")";
  a.addEventListener("click", deptClickHandler);
  li.appendChild(a);
  ulDeptObj.appendChild(li);
}

function init() {
  for (var i = 0; i < deptArr.length; i++) {
    createDeptElement(i);
  }
}

init();
