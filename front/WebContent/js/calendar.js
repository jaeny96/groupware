var calendar = null;
document.addEventListener("DOMContentLoaded", function () {
  var Calendar = FullCalendar.Calendar;
  var calendarEl = document.getElementById("calendar");

  //   var all_events = new Array();
  //   all_events = loadingEvents();
  calendar = new Calendar(calendarEl, {
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
    events: function (info, successCallback, failureCallback) {
      var id = "MSD002";
      var dept = "MSD";
      $.ajax({
        url: "/back/showskddetail",
        dataType: "json",
        data: { id: id, dept: dept },
        async: false,
        success: function (result) {
          var events = [];
          if (result != null) {
            $.each(result, function (i, e) {
              var type = e.skd_share;
              var enddate = e.skd_end_date;
              var startdate = moment(e.skd_start_date).format(
                "YYYY-MM-DD hh:mm"
              );
              var enddate = moment(enddate).format("YYYY-MM-DD hh:mm");
              if (type == "t ") {
                events.push({
                  title: e.skd_title,
                  start: startdate,
                  end: enddate,
                  resource: type,
                  color: "#28a745",
                });
              } else if (type == "p ") {
                events.push({
                  title: e.skd_title,
                  start: startdate,
                  end: enddate,
                  resource: type,
                  color: "#ffc107",
                });
              }
              console.log(events);
            });
            successCallback(events);
          }
        },
      });
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
            var skdInputEndTime = $("#end_time"); //종료시간
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
      allSchedule: {
        text: "전체 캘린더",
        click: function (info, successCallback, failureCallback) {
          var id = "MSD002";
          var dept = "MSD";
          $.ajax({
            url: "/back/showskddetail",
            dataType: "json",
            data: { id: id, dept: dept },
            async: false,
            success: function (result) {
              calendar.removeAllEventSources();
              var events = [];

              if (result != null) {
                $.each(result, function (i, e) {
                  var type = e.skd_share;
                  var startdate = moment(e.skd_start_date).format(
                    "YYYY-MM-DD hh:mm"
                  );
                  var enddate = moment(e.skd_end_date).format(
                    "YYYY-MM-DD hh:mm"
                  );
                  if (type == "t ") {
                    events.push({
                      title: e.skd_title,
                      start: startdate,
                      end: enddate,
                      resource: type,
                      color: "#28a745",
                    });
                  } else if (type == "p ") {
                    events.push({
                      title: e.skd_title,
                      start: startdate,
                      end: enddate,
                      resource: type,
                      color: "#ffc107",
                    });
                  }
                  console.log("전체버튼" + events);
                });
                calendar.addEventSource(events);
              }
            },
          });
        },
      },
      teamSchedule: {
        text: "공유 캘린더",
        click: function (info, successCallback, failureCallback) {
          var dept = "MSD";
          $.ajax({
            url: "/back/showteamskd",
            dataType: "json",
            data: { dept_id: dept },
            async: false,
            success: function (result) {
              calendar.removeAllEventSources();
              var events = [];
              if (result != null) {
                $.each(result, function (i, e) {
                  var enddate = e.skd_end_date;
                  var startdate = moment(e.skd_start_date).format(
                    "YYYY-MM-DD hh:mm"
                  );
                  var enddate = moment(enddate).format("YYYY-MM-DD hh:mm");

                  events.push({
                    title: e.skd_title,
                    start: startdate,
                    end: enddate,
                    color: "#28a745",
                  });
                  console.log(events);
                });
                calendar.addEventSource(events);
              }
            },
          });
        },
      },
      personalSchedule: {
        text: "내 캘린더",
        click: function (info, successCallback, failureCallback) {
          var id = "MSD002";
          $.ajax({
            url: "/back/showpersonalskd",
            dataType: "json",
            data: { skd_id: id },
            async: false,
            success: function (result) {
              calendar.removeAllEventSources();
              var events = [];
              if (result != null) {
                $.each(result, function (i, e) {
                  var enddate = e.skd_end_date;
                  var startdate = moment(e.skd_start_date).format(
                    "YYYY-MM-DD hh:mm"
                  );
                  var enddate = moment(enddate).format("YYYY-MM-DD hh:mm");

                  events.push({
                    title: e.skd_title,
                    start: startdate,
                    end: enddate,
                    color: "#ffc107",
                  });
                  console.log(events);
                });
                calendar.addEventSource(events);
              }
            },
          });
        },
      },
      weekSchedule: {
        text: "주간",
        class: "weekSchedule",
      },
    },
    headerToolbar: {
      left: "addeventButton allSchedule teamSchedule personalSchedule",
      center: "title",
      right: "dayGridWeek,dayGridMonth today prev,next",
    },

    //extendedProp 적용
    eventDidMount: function (info) {
      //   console.log(info.event.extendedProps);
      // {calendarType: 캘린더타입, teamOrPersonal: 팀or개인일정}
    },
    eventClick: function (info) {
      //일정 클릭했을 때
      createModal("skdDetail");
    },
  }); //New calendar끝

  calendar.render();
  // console.log({ calendar });
  // window.asdf = calendar;
}); //돔이벤트끝

//모달 관련
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

/////////////////////////////////////////////////////일정검색 드롭다운메뉴시작
//전체 검색 모달 창
//var modalSearchAll = document.querySelector("div#skdSearchAll");
//모달 창 내용(제목, 내용, 시작일, 종료일)
// var modalSearchAllTitle = modalSearchAll.querySelector("input.allTitle");
// var modalSearchAllContent = modalSearchAll.querySelector("input.allContent");
// var modalSearchAllSDate = modalSearchAll.querySelector("input.allSDate");
// var modalSerchAllEDate = modalSearchAll.querySelector("input.allEDate");

//기간으로 검색하기, 제목내용으로 검색하기 창 설정
var skdSearchObj = document.getElementById("skdSearch");
//검색 드롭다운메뉴
var categoryObj = document.querySelector("div.dropdown-menu");
console.log(categoryObj + "카테고리오브젝트");

function categoryHandler(e) {
  if (e.target.id == "skdCategoryPeriod") {
    console.log("기간검색" + e.target.id);
    createModal("skdSearchPeriod");
  } else if (e.target.id == "skdCategoryTitle") {
    console.log("내용검색" + e.target.id);
    createModal("skdSearchTitle");
    // } else if (e.target.id == "skdCategoryAll") {
    //   createModal("skdSearchAll");
    // }
  }
}
function initSearchModal() {
  categoryObj.addEventListener("click", categoryHandler);
}

initSearchModal();
////////////////////////////////////////////일정검색 드롭다운메뉴

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
  //   deleteBtn.addEventListener("click", deleteSKD);
}

//상세내역 모달에 데이터
//일정 상세내역
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

var allEvent = calendar.getEvents();
console.log(allEvent);

var type = typeValue.innerHTML;
var title = titleValue.innerText;
var start = StartTimeValue.innerText;
var end = EndTimeValue.innerText;
var content = ContentValue.innerText;

$.ajax({
  url: s,
});