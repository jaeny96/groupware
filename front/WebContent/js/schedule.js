var calendar = null;
var loginInfoIdObj = document.querySelector("div.profileDropdown span.loginId");
// document.addEventListener("DOMContentLoaded", function () {
//   console.log("로드!");
//   var Calendar = FullCalendar.Calendar;
//   var calendarEl = document.getElementById("calendar");

//   //   var all_events = new Array();
//   //   all_events = loadingEvents();
//   calendar = new Calendar(calendarEl, {
//     initialView: "dayGridMonth",
//     locale: "ko",
//     height: "100%",
//     editable: true,
//     eventLimit: true /*달력상에 셀 크기보다 많은 이벤트가 등록되어 있는 경우 more로 표기*/,
//     dayMaxEventRows: true,
//     navLinks: true,
//     selectable: true,

//     navLinkWeekClick: function (weekStart, jsEvent) {
//       console.log("week start", weekStart.toISOString());
//       console.log("coords", jsEvent.pageX, jsEvent.pageY);
//     },
//     events: function (info, successCallback, failureCallback) {
//       // var id = loginInfoIdObj.innerHTML;
//       // var dept = id.substring(0, 3);
//       var id = "MSD002";
//       var dept = "MSD";
//       $.ajax({
//         url: "/back/showskddetail",
//         dataType: "json",
//         data: { id: id, dept: dept },
//         async: false,
//         success: function (result) {
//           var events = [];
//           if (result != null) {
//             $.each(result, function (i, e) {
//               var type = e.skd_share;
//               var enddate = e.skd_end_date;
//               var startdate = moment(e.skd_start_date).format(
//                 "YYYY-MM-DD hh:mm"
//               );
//               var enddate = moment(enddate).format("YYYY-MM-DD hh:mm");
//               if (type == "t ") {
//                 events.push({
//                   title: e.skd_title,
//                   start: startdate,
//                   end: enddate,
//                   resource: type,
//                   id: e.skd_no,
//                   extendedProps: { content: e.skd_content },

//                   color: "#28a745",
//                 });
//               } else if (type == "p ") {
//                 events.push({
//                   title: e.skd_title,
//                   start: startdate,
//                   end: enddate,
//                   resource: type,
//                   id: e.skd_no,
//                   extendedProps: { content: e.skd_content },
//                   color: "#ffc107",
//                 });
//               }
//               console.log(events);
//             });
//             successCallback(events);
//           }
//         },
//       });
//     },
//     customButtons: {
//       addeventButton: {
//         text: "일정 추가",
//         id: "modalInputBtn",
//         click: function () {
//           //modal 띄우기
//           const modal = document.getElementById("skdInput");
//           const closeBtn = document.querySelector("button.cancel");
//           modal.classList.remove("hidden");

//           $(closeBtn).on("click", function () {
//             modal.classList.add("hidden");
//           });
//           //캘린더 타입, 일정 종류 지정
//           var test = "p";
//           var skdInputShare = $('input[name="radio-3"]');
//           console.log(skdInputShare);
//           skdInputShare.change(function () {
//             test = this.value;
//             console.log(test);
//           });
//           var skdInputTypeValue = "업무";
//           var skdInputTypeObj = $("#skdType");
//           skdInputTypeObj.change(function () {
//             console.log(this.value);
//             skdInputTypeValue = this.value + "";
//           });
//           //저장버튼 클릭이벤트
//           $("button.skdInsertSaveBtn").on("click", function (e) {
//             var selectOption = document.getElementById("skdType");
//             var skdInputTitle = $("#input_title"); //제목
//             var skdInputContent = $("#input_content"); //내용
//             var skdInputStartDate = $("#start_date"); //시작날짜
//             var skdInputStartTime = $("#start_time"); //시작시간
//             var skdInputEndDate = $("#end_date"); //종료날짜
//             var skdInputEndTime = $("#end_time"); //종료시간

//             if (skdInputStartTime.val() == "" || skdInputEndTime.val() == "") {
//               alert("시간을 입력하세요.");
//             } else if (
//               new Date(skdInputEndDate.val()) -
//                 new Date(skdInputStartDate.val()) <
//               0
//             ) {
//               alert("종료일이 시작일보다 먼저입니다.");
//             } else {
//               if (test == "p") {
//                 //  calendar.addEvent(
//                 //    {
//                 //      title: skdInputTitle.val(),
//                 //      content: skdInputContent.val(),
//                 //      start: skdInputStartDate.val() + " " + skdInputStartTime.val(),
//                 //      end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
//                 //      allDay: false,
//                 //      backgroundColor: '#ffc107',
//                 //      calendarType: skdInputTypeValue,
//                 //      teamOrPersonal: test

//                 //    })

//                 var skdInsertObj = document.querySelector(
//                   "#skdInput > div.modal_content > div > form"
//                 ); //
//                 console.log(skdInsertObj);
//                 //일정 저장 버튼
//                 var skdInsertBtn = skdInsertObj.querySelector(
//                   "button.skdInsertSaveBtn"
//                 );
//                 console.log(skdInsertBtn);
//                 var skdInsertUrl = "/back/addschedule";

//                 $.ajax({
//                   url: skdInsertUrl,
//                   method: "post",
//                   datatype: "json",
//                   data: {
//                     title: skdInputTitle.val(),
//                     content: skdInputContent.val(),
//                     start:
//                       skdInputStartDate.val() + " " + skdInputStartTime.val(),
//                     end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
//                     allDay: false,
//                     backgroundColor: "#ffc107",
//                     calendarType: skdInputTypeValue,
//                     teamOrPersonal: test,
//                   },
//                   success: function () {
//                     alert("일정이 추가되었습니다");

//                     $(
//                       "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(4) > a"
//                     ).trigger("click");
//                   },
//                   error: function (request, status, error) {
//                     alert(
//                       "code:" +
//                         request.status +
//                         "\n" +
//                         "message:" +
//                         request.responseText +
//                         "\n" +
//                         "error:" +
//                         error
//                     );
//                   },
//                 });
//                 e.preventDefault(); //이걸 해줘야 하나?
//               } else if (test == "t") {
//                 //  calendar.addEvent({
//                 //    title: skdInputTitle.val(),
//                 //    content: skdInputContent.val(),
//                 //    start: skdInputStartDate.val() + " " + skdInputStartTime.val(),
//                 //    end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
//                 //    allDay: false,
//                 //    backgroundColor: '#28a745',
//                 //    extendedProps: {
//                 //      calendarType: skdInputTypeValue,
//                 //      teamOrPersonal: test
//                 //    }
//                 //  })
//                 console.log(skdInputTitle.val() + skdInputContent.val());

//                 $.ajax({
//                   url: skdInsertUrl,
//                   method: "post",
//                   datatype: "json",
//                   data: {
//                     title: skdInputTitle.val(),
//                     content: skdInputContent.val(),
//                     start:
//                       skdInputStartDate.val() + " " + skdInputStartTime.val(),
//                     end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
//                     allDay: false,
//                     backgroundColor: "#28a745",
//                     extendedProps: {
//                       calendarType: skdInputTypeValue,
//                       teamOrPersonal: test,
//                     },
//                   },
//                   success: function () {
//                     alert("일정이 추가되었습니다");
//                     $(
//                       "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li.sidebar-item.active > a"
//                     ).trigger("click");
//                   },
//                   error: function (request, status, error) {
//                     alert(
//                       "code:" +
//                         request.status +
//                         "\n" +
//                         "message:" +
//                         request.responseText +
//                         "\n" +
//                         "error:" +
//                         error
//                     );
//                   },
//                 });
//                 e.preventDefault(); //이걸 해줘야 하나?
//                 // $("#demo-calendar").fullCalendar('addEventSource', JSON, true);
//               }
//             }
//           });
//         },
//       },
//       allSchedule: {
//         text: "전체 캘린더",
//         click: function (info, successCallback, failureCallback) {
//           // var id = loginInfoIdObj.innerHTML;
//           // var dept = id.substring(0, 3);
//           var id = "MSD002";
//           var dept = "MSD";
//           $.ajax({
//             url: "/back/showskddetail",
//             dataType: "json",
//             data: { id: id, dept: dept },
//             async: false,
//             success: function (result) {
//               calendar.removeAllEventSources();
//               var events = [];

//               if (result != null) {
//                 $.each(result, function (i, e) {
//                   var type = e.skd_share;
//                   var startdate = moment(e.skd_start_date).format(
//                     "YYYY-MM-DD hh:mm"
//                   );
//                   var enddate = moment(e.skd_end_date).format(
//                     "YYYY-MM-DD hh:mm"
//                   );

//                   if (type == "t ") {
//                     events.push({
//                       title: e.skd_title,
//                       start: startdate,
//                       end: enddate,
//                       resource: type,

//                       color: "#28a745",
//                     });
//                   } else if (type == "p ") {
//                     events.push({
//                       title: e.skd_title,
//                       start: startdate,
//                       end: enddate,
//                       resource: type,
//                       color: "#ffc107",
//                     });
//                   }
//                   console.log("전체버튼" + events);
//                 });
//                 calendar.addEventSource(events);
//               }
//             },
//           });
//         },
//       },
//       teamSchedule: {
//         text: "공유 캘린더",
//         click: function (info, successCallback, failureCallback) {
//           // var id = loginInfoIdObj.innerHTML;
//           // var dept = id.substring(0, 3);
//           var dept = "MSD";
//           $.ajax({
//             url: "/back/showteamskd",
//             dataType: "json",
//             data: { dept_id: dept },
//             async: false,
//             success: function (result) {
//               calendar.removeAllEventSources();
//               var events = [];
//               if (result != null) {
//                 $.each(result, function (i, e) {
//                   var enddate = e.skd_end_date;
//                   var startdate = moment(e.skd_start_date).format(
//                     "YYYY-MM-DD hh:mm"
//                   );
//                   var enddate = moment(enddate).format("YYYY-MM-DD hh:mm");
//                   events.push({
//                     title: e.skd_title,
//                     start: startdate,
//                     end: enddate,
//                     color: "#28a745",
//                   });
//                   //console.log("팀스케줄" + events);
//                 });
//                 calendar.addEventSource(events);
//               }
//             },
//           });
//         },
//       },
//       personalSchedule: {
//         text: "내 캘린더",
//         click: function (info, successCallback, failureCallback) {
//           var id = "MSD002";
//           $.ajax({
//             url: "/back/showpersonalskd",
//             dataType: "json",
//             data: { skd_id: id },
//             async: false,
//             success: function (result) {
//               calendar.removeAllEventSources();
//               var events = [];
//               if (result != null) {
//                 $.each(result, function (i, e) {
//                   var enddate = e.skd_end_date;
//                   var startdate = moment(e.skd_start_date).format(
//                     "YYYY-MM-DD hh:mm"
//                   );
//                   var enddate = moment(enddate).format("YYYY-MM-DD hh:mm");

//                   events.push({
//                     title: e.skd_title,
//                     start: startdate,
//                     end: enddate,
//                     color: "#ffc107",
//                   });
//                   // console.log(events);
//                 });
//                 calendar.addEventSource(events);
//               }
//             },
//           });
//         },
//       },
//       weekSchedule: {
//         text: "주간",
//         class: "weekSchedule",
//       },
//     },
//     headerToolbar: {
//       left: "addeventButton allSchedule teamSchedule personalSchedule",
//       center: "title",
//       right: "dayGridWeek,dayGridMonth today prev,next",
//     },

//     //extendedProp 적용
//     eventDidMount: function (info) {},
//     eventClick: function (info) {
//       //일정 클릭했을 때
//       localStorage.setItem("skdNo", info.event.id);
//       createModal("skdDetail");
//     },
//   }); //New calendar끝
//   calendar.render();
// }); //돔이벤트끝
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

//기간으로 검색하기, 제목내용으로 검색하기 창 설정
var skdSearchObj = document.getElementById("skdSearch");
//검색 드롭다운메뉴
var categoryObj = document.querySelector("div.dropdown-menu");
function categoryHandler(e) {
  if (e.target.id == "skdCategoryPeriod") {
    createModal("skdSearchPeriod");
  } else if (e.target.id == "skdCategoryTitle") {
    console.log("내용검색" + e.target.id);
    createModal("skdSearchTitle");
  }
}
function initSearchModal() {
  categoryObj.addEventListener("click", categoryHandler);
}
initSearchModal();
////////////////////////////////////////////일정검색 드롭다운메뉴
//modal 만드는 함수
//파라미터 : class="modal" 의 id
var skdno;
function createModal(id) {
  skdno = localStorage.getItem("skdNo");
  // var skdid = "MSD002";
  // var skddept = "MSD";
  var modal = document.getElementById(id);
  modal.classList.remove("hidden"); //모달열기
  var closeBtn = modal.querySelector("button.cancel");
  var overlay = modal.querySelector(".modal_overlay");
  var deleteBtn = modal.querySelector("button.deleteBtn");
  var xBoxBtn = modal.querySelector("button.xBox");
  var modifyBtn = modal.querySelector("button.modifyBtn");
  var modifySubmitBtnObj = modal.querySelector("button.modifySubmit");
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
  if (modifyBtn != null) {
    modifyBtn.addEventListener("click", function () {
      modal.classList.add("hidden");
      createModal("skdModifyDetail");
    });
  }
  $.ajax({
    url: "/back/showbydetail",
    dataType: "json",
    data: { skd_no: skdno },
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        titleValue.innerHTML = e.skd_title;
        typeValue.innerHTML = e.skd_type.skd_type;
        StartTimeValue.innerHTML = e.skd_start_date;
        EndTimeValue.innerHTML = e.skd_end_date;
        ContentValue.innerHTML = e.skd_content;
      });
    },
  });
}
//상세내역 모달에 데이터
//일정 상세내역
var shareObj = document.getElementById("skdDetailShare");
var shareValue = shareObj.querySelector("td.skdDetailInputData");
console.log(shareValue);
var typeObj = document.getElementById("skdDetailType");
var typeValue = typeObj.querySelector("td.skdDetailInputData");
var titleObj = document.getElementById("skdDetailTitle");
var titleValue = titleObj.querySelector("td.skdDetailInputData");
var StartTimeObj = document.getElementById("skdDetailStartTime");
var StartTimeValue = StartTimeObj.querySelector("td.skdDetailInputData");
var EndTimeObj = document.getElementById("skdDetailEndTime");
var EndTimeValue = EndTimeObj.querySelector("td.skdDetailInputData");
var ContentObj = document.getElementById("skdDetailContent");
var ContentValue = ContentObj.querySelector("td.skdDetailInputData");

function resultSkdDetail() {
  // shareValue.innerHTML = skdShareDetail;
  // titleValue.innerHTML = skdTitleDetail;
  // typeValue.innerHTML = skdTypeDetail;
  // StartTimeValue.innerHTML = skdStartTimeDetail;
  // EndTimeValue.innerHTML = skdStartEndTimeDetail;
  // ContentValue.innerHTML = skdContentDetail;
  // localStorage.setItem("skd_share", skdShareDetail);
  // localStorage.setItem("skd_title", skdTitleDetail);
  // localStorage.setItem("skd_type", skdTypeDetail);
  // localStorage.setItem("skd_start_date", skdStartDateDetail);
  // localStorage.setItem("skd_start_time", skdStartTimeDetail);
  // localStorage.setItem("skd_end_date", skdStartEndDateDetail);
  // localStorage.setItem("skd_end_time", skdStartEndTimeDetail);
  // localStorage.setItem("skd_content", skdContentDetail);
}
function init() {
  resultSkdDetail();
}

init();
