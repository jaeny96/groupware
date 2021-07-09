var skdTBodyObj = document.querySelector("tbody.skdTbody");
console.log(skdTBodyObj);

//임시데이터
var skdDate = ["21.06.16(수)", "21.07.16(수)", "21.08.16(수)", "21.08.16(수)"];
var skdTime = [
  "오전 6:00~오후 2:00",
  "오전 9:00~오전 11:00",
  "오전 01:00~오후 03:00",
  "오전 01:00~오후 03:00",
];
var skdContent = ["요구사항정의서 제출", "팀회의", "외부미팅", "아무개"];

//표만들기
function createSkdElement(i) {
  var tr = document.createElement("tr");
  var tdDate = document.createElement("td");
  tdDate.innerHTML = skdDate[i];
  var tdTime = document.createElement("td");
  tdTime.innerHTML = skdTime[i];
  var tdContent = document.createElement("td");
  //내용클릭시 모달띄워야함...
  var a = document.createElement("a");
  a.setAttribute("href", "#");
  a.addEventListener("click", function () {
    createModal("skdDetail");
  });
  a.innerText = skdContent[i];
  //자식에 부모달아주기
  tdContent.appendChild(a);
  tr.appendChild(tdDate);
  tr.appendChild(tdTime);
  tr.appendChild(tdContent);
  skdTBodyObj.appendChild(tr);
}
//일정개수만큼 표를 생성하여 데이터 넣는 함수
function init() {
  for (var i = 0; i < skdDate.length; i++) {
    createSkdElement(i);
  }
}
init();

//modal 만드는 함수
//파라미터 : class="modal" 의 id
function createModal(id) {
  var modal = document.getElementById(id);
  modal.classList.remove("hidden"); //모달열기
  var closeBtn = modal.querySelector("button.cancel");

  var overlay = modal.querySelector(".modal_overlay");
  var deleteBtn = modal.querySelector("button.deleteBtn");
  var xBoxBtn = modal.querySelector("button.xBox");

  //함수
  var openModal = () => {
    modal.classList.remove("hidden");
  };
  var deleteSKD = () => {
    window.alert("일정을 삭제하시겠습니까?");
  };
  var closeModal = () => {
    modal.classList.add("hidden");
  };

  //클릭이벤트
  //오버레이 부분 클릭 닫기
  overlay.addEventListener("click", closeModal);
  //모달창 닫기 버튼
  if (xBoxBtn != null) {
    xBoxBtn.addEventListener("click", closeModal);
  }

  if (closeBtn != null) {
    closeBtn.addEventListener("click", closeModal);
  }

  //todo : 삭제 버튼
  if (deleteBtn != null) {
    deleteBtn.addEventListener("click", deleteSKD);
  }
}
