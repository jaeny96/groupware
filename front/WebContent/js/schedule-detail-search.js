var skdTBodyObj = document.querySelector("tbody.skdTbody");
//상세내역 모달에 데이터
var shareObj = document.getElementById("skdDetailShare");
//console.log(shareObj);
var shareValue = shareObj.querySelector("td.skdDetailInputData");
//console.log(shareValue);
var typeObj = document.getElementById("skdDetailType");
var typeValue = typeObj.querySelector("td.skdDetailInputData");
//console.log(typeValue);
var titleObj = document.getElementById("skdDetailTitle");
var titleValue = titleObj.querySelector("td.skdDetailInputData");

var StartTimeObj = document.getElementById("skdDetailStartTime");
var StartTimeValue = StartTimeObj.querySelector("td.skdDetailInputData");

//console.log(StartTimeValue);
var EndTimeObj = document.getElementById("skdDetailEndTime");
var EndTimeValue = EndTimeObj.querySelector("td.skdDetailInputData");

//console.log(EndTimeValue);
var ContentObj = document.getElementById("skdDetailContent");
var ContentValue = ContentObj.querySelector("td.skdDetailInputData");

var type = typeValue.innerHTML;
//typeValue.innerHTML = "회의";
var title = titleValue.innerText;
var start = StartTimeValue.innerText;
var end = EndTimeValue.innerText;
var content = ContentValue.innerText;
console.log(typeValue.innerHTML);
console.log(title + "22222");
console.log(start + "33333");
console.log(end);
console.log(content);

//표만들기
function createSkdElement(skdDate, skdTime, skdContent) {
  var tr = document.createElement("tr");
  var tdDate = document.createElement("td");
  tdDate.innerHTML = skdDate;
  var tdTime = document.createElement("td");
  tdTime.innerHTML = skdTime;
  var tdContent = document.createElement("td");
  //내용클릭시 모달띄우기
  var a = document.createElement("a");
  a.setAttribute("href", "#");
  a.addEventListener("click", function () {
    createModal("skdDetail");
  });
  a.innerText = skdContent;
  //자식에 부모달아주기
  tdContent.appendChild(a);
  tr.appendChild(tdDate);
  tr.appendChild(tdTime);
  tr.appendChild(tdContent);
  skdTBodyObj.appendChild(tr);
}

//일정개수만큼 표를 생성하여 데이터 넣는 함수
function init(result) {
  console.log({ result });

  var skdDate = [];
  var skdTime = [];
  var skdContent = [];

  result.forEach((item) => {
    start = new Date(item.skd_start_date);
    end = new Date(item.skd_end_date);
    title = item.skd_title;

    skdContent.push(title);
    skdDate.push(moment(start).format("YY-MM-DD"));
    skdTime.push(moment(start).format("LT") + " ~ " + moment(end).format("LT"));
  });
  for (var i = 0; i < skdDate.length; i++) {
    createSkdElement(skdDate[i], skdTime[i], skdContent[i]);
  }
}

function createModalValue(result) {
  console.log({ result });
  let jsonStr = JSON.stringify({ result });
  console.log(jsonStr);
  type = result.skd_type;
  title = result[7];
  start = result[6];
  end = result[2];
  content = result.skd_content;
}

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

//내용,제목 검색
document.addEventListener("DOMContentLoaded", function () {
  var id = "MSD002";
  //var dept = "MSD";
  const urlSearchParams = new URLSearchParams(window.location.search);
  const urlParams = Object.fromEntries(urlSearchParams.entries());
  urlParams.id = id;
  // urlParams.dept = dept;
  // const encoded = encodeURIComponent(urlParams);

  console.log({ urlParams });
  $.ajax({
    url: "/back/showbycontent",
    dataType: "json",
    data: urlParams,
    success: function (responseData) {
      init(responseData);
      createModalValue(responseData);
    },
  });
});

//기간검색
document.addEventListener("DOMContentLoaded", function () {
  var id = "MSD002";
  var dept = "MSD";
  const urlSearchParams = new URLSearchParams(window.location.search);
  const urlParams = Object.fromEntries(urlSearchParams.entries());
  urlParams.id = id;
  urlParams.dept_id = dept;

  console.log({ urlParams });
  $.ajax({
    url: "/back/showbydate",
    dataType: "json",
    data: urlParams,
    success: function (responseData) {
      init(responseData);
      createModalValue(responseData);
    },
  });
});
