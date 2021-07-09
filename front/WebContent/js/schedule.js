//FullCalendar 라이브러리
$(document).ready(function () {
  $("#teamSkd").click(function () {
    $(this).toggleClass("active");
    console.log($(this));
    $("#personalSkd").not(this).removeClass("active");
  });
});
var calendar = null;
//스케쥴의 상세내역을 담고 있는 skdObj를 전역변수로 선언
var skdInputObj;

//html 로드 후 javascript 로드
document.addEventListener("DOMContentLoaded", function () {
  var calendarEl = document.getElementById("calendar");
  calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "ko",
    height: "100%",
    editable: true,
    eventLimit: true /*달력상에 셀 크기보다 많은 이벤트가 등록되어 있는 경우 more로 표기*/,
    dayMaxEventRows: true,
    navLinks: true,
    selectable: true,
    navLinkWeekClick: function (weekStart, jsEvent) {
      console.log("week start", weekStart.toISOString());
      console.log("coords", jsEvent.pageX, jsEvent.pageY);
    },
    customButtons: {
      addeventButton: {
        text: "일정 추가",
        id: "modalInputBtn",
        click: function () {
          //modal 띄우기
          const modal = document.getElementById("skdInput");
          const closeBtn = document.querySelector("button.cancel");
          modal.classList.remove("hidden");

          $(closeBtn).on("click", function () {
            modal.classList.add("hidden");
          });

          //저장버튼 클릭이벤트
          $("button.saveBtn").on("click", function () {
            var selectOption = document.getElementById("skdType");
            var skdInputType = selectOption.options[selectOption.selectedIndex]; //일정종류

            var skdInputTitle = $("#input_title"); //제목
            var skdInputContent = $("#input_content"); //내용
            var skdInputStartDate = $("#start_date"); //시작날짜
            var skdInputStartTime = $("#start_time"); //시작시간
            var skdInputEndDate = $("#end_date"); //종료날짜
            var skdInputEndTime = $("#end_Time"); //종료시간
            var skdInputShare = $('input[name="radio-3"]:checked');

            if (skdInputContent.val() == null || skdInputContent.val() == "") {
              alert("내용을 입력하세요.");
            } else if (
              skdInputStartDate.val() == "" ||
              skdInputEndDate.val() == ""
            ) {
              alert("날짜를 입력하세요.");
            } else if (
              new Date(skdInputEndDate.val()) -
                new Date(skdInputStartDate.val()) <
              0
            ) {
              alert("종료일이 시작일보다 먼저입니다.");
            } else {
            }

            skdInputObj = {
              title: skdInputTitle.val(),
              content: skdInputContent.val(),
              start: skdInputStartDate.val(),
              startTime: skdInputStartTime.val(),
              end: skdInputEndDate.val(),
              endTime: skdInputEndTime.val(),
              allDay: false,
              extendedProps: {
                calendarType: skdInputType.value,
                teamOrPersonal: skdInputShare.val(),
              },
            };

            //todo : 입력 해결
            // $('#calendar').fullCalendar('renderEvent', skdInputObj, true);
            console.log(skdInputObj);
          });
        },
      },
      teamSchedule: {
        text: "공유 캘린더",
        click: function () {
          teamSkdObj = calendarEl.querySelector(
            ".fc-teamSchedule-button.fc-button-primary"
          );
          console.log(teamSkdObj);
        },
      },
      personalSchedule: {
        text: "내 캘린더",
        click: function () {
          personalskdObj = calendarEl.querySelector(
            ".fc-personalSchedule-button.fc-button-primary"
          );
          console.log(personalskdObj);
        },
      },
      weekSchedule: {
        text: "주간",
        class: "weekSchedule",
      },
    },

    headerToolbar: {
      left: "addeventButton teamSchedule personalSchedule",
      center: "title",
      right: "dayGridWeek,dayGridMonth today prev,next",
    },
    //하루 스케쥴 추가, ajax로 작업하기
    events: [
      {
        //개인캘린더
        resource: "p",
        title: "회의",
        start: "2021-07-01 12:30",
        end: "2021-07-01 3:30",
        backgroundColor: "#28a745",
      },

      {
        //개인캘린더
        resource: "p",
        title: "업무",
        start: "2021-07-01 11:30",
        end: "2021-07-01 3:30",
        backgroundColor: "#ffc107",
      },

      {
        resource: "t",
        title: "부산출장",
        start: "2021-07-05 12:30",
        end: "2021-07-07 13:30",
        backgroundColor: "#ffc107",
        borderColor: "#ffc107",
      },
    ],

    //extendedProp 적용
    eventDidMount: function (info) {
      console.log(info.event.extendedProps);
      // {calendarType: 캘린더타입, teamOrPersonal: 팀or개인일정}
    },

    eventClick: function (info) {
      //일정 클릭했을 때
      createModal("skdDetail");
    },
  });

  calendar.render();
});

//일정검색 - 전체, 기간, 내용검색에서 검색버튼
var searchResultP = document.querySelector("button.searchPeriodBtn");
//내용,제목검색버튼
var searchResultC = document.querySelector("div>button.searchContentBtn");
//전체검색버튼
var searchResultA = document.querySelector("button.searchAllBtn");

//팀일정 개인일정 버튼
var skdTeamBtn = document.getElementById("teamSkd");
var skdPersonalBtn = document.getElementById("personalSkd");
console.log(skdTeamBtn);
console.log(skdPersonalBtn);

//모달창에서 팀일정 라디오 선택, 개인일정 라디오 선택
var skdOption = document.querySelectorAll("input.form-check-input");
var teamOption = skdOption[0];
var personalOption = skdOption[1];
console.log(skdOption);

//팀일정 개인일정 버튼 클릭
function selectSkdType(e) {
  console.log(e.target.id);
  if (e.target.id == "teamSkd") {
    alert("팀일정");
  } else if (e.target.id == "personalSkd") {
    alert("내일정");
  }
}
function initType() {
  skdTeamBtn.addEventListener("click", selectSkdType);
  skdPersonalBtn.addEventListener("click", selectSkdType);
}
initType();

//시작 날짜를 현재 날짜로
document.getElementById("start_date").value = new Date()
  .toISOString()
  .substring(0, 10);
//종료 날짜를 현재 날짜로
document.getElementById("end_date").value = new Date()
  .toISOString()
  .substring(0, 10);

//시작 시간을 현재 시간으로
document.getElementById("start_time").value = new Date(
  new Date()
).toTimeString();
//종료 시간을 현재 시간으로
document.getElementById("end_time").value = new Date(new Date()).toTimeString();

//기간으로 검색하기, 제목내용으로 검색하기 창 설정
var skdSearchObj = document.getElementById("skdSearch");
//검색 드롭다운메뉴
var categoryObj = document.querySelector("div.dropdown-menu");

function categoryHandler(e) {
  if (e.target.id == "skdCategoryPeriod") {
    createModal("skdSearchPeriod");
  } else if (e.target.id == "skdCategoryTitle") {
    createModal("skdSearchTitle");
  } else if (e.target.id == "skdCategoryAll") {
    createModal("skdSearchAll");
  }
}

function initSearchModal() {
  categoryObj.addEventListener("click", categoryHandler);
}

initSearchModal();

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
  xBoxBtn.addEventListener("click", closeModal);
  closeBtn.addEventListener("click", closeModal);

  //todo : 삭제 버튼 오류
  deleteBtn.addEventListener("click", deleteSKD);
}
