var calendar = null;
var $content = $("div.wrapper>div.main>main.content");

//팀캘린더, 개인캘린더 지정
var teamOrPersonalOption = "p";
var skdInputShare = $('input[name="radio-3"]');
skdInputShare.change(function () {
  //팀 = t, 개인 = p로 인식하도록 하는 함수
  teamOrPersonalOption = this.value;
  console.log(teamOrPersonalOption);
});

//업무, 출장 등 스케쥴 종류 지정
var skdInputTypeValue = "업무";
var skdInputTypeObj = $("#skdType");
skdInputTypeObj.change(function () {
  //드롭다운에서 선택한 값으로 인식하도록 하는 함수
  console.log(this.value);
  skdInputTypeValue = this.value + "";
});

/**
 * 일정 압력 유효성 검사 후 일정 추가
 */
function inputTimeValidation() {
  var skdInputStartDate = $("#start_date"); //시작날짜
  var skdInputStartTime = $("#start_time"); //시작시간
  var skdInputEndDate = $("#end_date"); //종료날짜
  var skdInputEndTime = $("#end_time"); //종료시간

  const startDateforCount = String(skdInputStartDate.val()).replace(/-/gi, "");
  const endDateforCount = String(skdInputEndDate.val()).replace(/-/gi, "");
  const startTimeforCount = String(skdInputStartTime.val()).replace(/:/gi, "");
  const endTimeforCount = String(skdInputEndTime.val()).replace(/:/gi, "");

  //test
  // console.log(startDateforCount);
  // console.log(endDateforCount);
  // console.log(startTimeforCount);
  // console.log(endTimeforCount);

  //시간을 입력하지 않았을 경우 넘어가지 못함
  if (skdInputStartTime.val() == "" || skdInputEndTime.val() == "") {
    alert("시간을 입력하세요.");
    return false;

    //종료일이 시작일보다 먼저일 경우 넘어가지 못함
  } else if (startDateforCount - endDateforCount > 0) {
    alert("날짜를 다시 입력하세요.");
    return false;

    //날짜가 같을 때 시작시간이 먼저일 경우 넘어가지 못함
  } else if (
    startDateforCount === endDateforCount &&
    startTimeforCount - endTimeforCount > 0
  ) {
    alert("시간을 다시 입력하세요.");
    return false;
  } else {
    //개인 일정일 경우
    if (teamOrPersonalOption == "p") {
      insertSchedule("p", "#ffc107", skdInputTypeValue);
      e.preventDefault();

      //팀 일정일 경우
    } else if (teamOrPersonalOption == "t") {
      insertSchedule("t", "#28a745", skdInputTypeValue);
      e.preventDefault();
    }
  }
}

/**
 * 일정 추가 함수
 * @param {*} teamOrPersonalOption : 팀일정 or 개인일정
 * @param {*} backgroundColorSchedule : 일정 색깔
 * @param {*} skdInputTypeValue  : 업무 출장 등 일정타입
 */
function insertSchedule(
  teamOrPersonalOption,
  backgroundColorSchedule,
  skdInputTypeValue
) {
  //schedule.html의 스케쥴 입력 모달 창의 input 객체를 각 변수에 주입
  var skdInputTitle = $("#input_title"); //제목
  var skdInputContent = $("#input_content"); //내용
  var skdInputStartDate = $("#start_date"); //시작날짜
  var skdInputStartTime = $("#start_time"); //시작시간
  var skdInputEndDate = $("#end_date"); //종료날짜
  var skdInputEndTime = $("#end_time"); //종료시간

  var skdInsertObj = document.querySelector(
    "#skdInput > div.modal_content > div > form"
  );
  //console.log("스케쥴 입력 객체" + skdInsertObj);

  /**
   * title : 제목
   * content : 내용
   * start : 일정 시작 시간
   * end: 일정 종료 시간
   * backgroundColor : 개인 or 팀 일정에 따른 색깔 지정
   * calendarType : 출장, 업무 등 일정종류
   * teamOrPersonal : 개인 or 팀 일정
   */
  var skdInsertUrl = "/back/addschedule";
  $.ajax({
    url: skdInsertUrl,
    method: "post",
    datatype: "json",
    data: {
      title: skdInputTitle.val(),
      content: skdInputContent.val(),
      start: skdInputStartDate.val() + " " + skdInputStartTime.val(),
      end: skdInputEndDate.val() + " " + skdInputEndTime.val(),
      allDay: false,
      backgroundColor: backgroundColorSchedule,
      calendarType: skdInputTypeValue,
      teamOrPersonal: teamOrPersonalOption,
    },
    success: function () {
      alert("일정이 추가되었습니다");
      loadSchedule();
    },
    error: function (request, status, error) {
      alert(
        "code:" +
          request.status +
          "\n" +
          "message:" +
          request.responseText +
          "\n" +
          "error:" +
          error
      );
    },
  });
}

function loadSchedule() {
  var href = "schedule.html";
  $content.load(href, function (responseTxt, statusTxt, xhr) {
    if (statusTxt == "error")
      alert("Error: " + xhr.status + ": " + xhr.statusText);
  });
}

/**
 * 캘린더 함수 시작
 */
$(function () {
  var Calendar = FullCalendar.Calendar;
  var calendarEl = document.getElementById("calendar");

  /**
   * 캘린더 설정
   */
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
      var loginedId = localStorage.getItem("loginInfo");
      var loginedDept = loginedId.substring(0, 3);
      var id = loginedId;
      var dept = loginedDept;
      /**
       * 캘린더에 일정 보여주기 type = 팀일정, 개인일정
       */
      $.ajax({
        url: "/back/showskddetail",
        dataType: "json",
        data: { id: id, dept: dept },
        success: function (result) {
          var events = [];
          if (result != null) {
            $.each(result, function (i, e) {
              var type = $.trim(e.skd_share);
              //컴퓨터마다 t와 p에 띄어쓰기가 있거나 없어서 trim으로 잘라줌
              var enddate = e.skd_end_date;
              var startdate = moment(e.skd_start_date).format(
                "YYYY-MM-DD hh:mm"
              );
              var enddate = moment(enddate).format("YYYY-MM-DD hh:mm");
              if (type == "t") {
                //캘린더가 가지고 있는 객체에 DB값 세팅
                events.push({
                  title: e.skd_title,
                  start: startdate,
                  end: enddate,
                  resource: type,
                  id: e.skd_no,
                  extendedProps: { content: e.skd_content },
                  color: "#28a745",
                });
              } else if (type == "p") {
                events.push({
                  title: e.skd_title,
                  start: startdate,
                  end: enddate,
                  resource: type,
                  id: e.skd_no,
                  extendedProps: { content: e.skd_content },
                  color: "#ffc107",
                });
              }
              // console.log(events);
            });
            successCallback(events);
          }
        },
      });
    },
    customButtons: {
      /**
       * 일정 추가 버튼
       */
      addeventButton: {
        text: "일정 추가",
        id: "modalInputBtn",
        click: function () {
          //modal 띄우기
          const modal = document.getElementById("skdInput");
          const closeBtn = document.querySelector("button.cancel");
          modal.classList.remove("hidden");

          //모달 닫기
          $(closeBtn).on("click", function () {
            modal.classList.add("hidden");
          });

          //저장버튼 클릭이벤트
          $("button.skdInsertSaveBtn").on("click", function (e) {
            //유효성 검사 후 일정 추가
            inputTimeValidation();
          });
        },
      },
      /**
       * 전체캘린더 버튼 클릭시 전체 일정 보여주기
       */
      allSchedule: {
        text: "전체 캘린더",
        click: function (info, successCallback, failureCallback) {
          var loginedId = localStorage.getItem("loginInfo");
          var loginedDept = loginedId.substring(0, 3);
          var id = loginedId;
          var dept = loginedDept;
          $.ajax({
            url: "/back/showskddetail",
            dataType: "json",
            data: { id: id, dept: dept },
            success: function (result) {
              calendar.removeAllEventSources();
              var events = [];
              if (result != null) {
                $.each(result, function (i, e) {
                  // var type = e.skd_share;
                  var type = $.trim(e.skd_share);
                  var startdate = moment(e.skd_start_date).format(
                    "YYYY-MM-DD hh:mm"
                  );
                  var enddate = moment(e.skd_end_date).format(
                    "YYYY-MM-DD hh:mm"
                  );
                  if (type == "t") {
                    events.push({
                      title: e.skd_title,
                      start: startdate,
                      end: enddate,
                      resource: type,
                      color: "#28a745",
                    });
                  } else if (type == "p") {
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
      /**
       * 공유캘린더 버튼 클릭시 팀 일정 보여주기
       */
      teamSchedule: {
        text: "공유 캘린더",
        click: function () {
          var loginedId = localStorage.getItem("loginInfo");
          var loginedDept = loginedId.substring(0, 3);
          var dept = loginedDept;
          $.ajax({
            url: "/back/showteamskd",
            dataType: "json",
            data: { dept_id: dept },
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
                  //console.log("팀스케줄" + events);
                });
                calendar.addEventSource(events);
              }
            },
          });
        },
      },
      /**
       * 내캘린더 버튼 클릭시 개인 일정 보여주기
       */
      personalSchedule: {
        text: "내 캘린더",
        click: function () {
          var loginedId = localStorage.getItem("loginInfo");
          var id = loginedId;

          $.ajax({
            url: "/back/showpersonalskd",
            dataType: "json",
            data: { skd_id: id },
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
                  // console.log(events);
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
    eventDidMount: function (info) {},
    eventClick: function (info) {
      //일정 클릭했을 때
      localStorage.setItem("skdNo", info.event.id);
      console.log("a " + info.event.id);

      createModal("skdDetail");
    },
  }); //New calendar끝
  calendar.render();
  //돔이벤트끝

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
  document.getElementById("end_time").value = new Date(
    new Date()
  ).toTimeString();

  //기간으로 검색하기, 제목내용으로 검색하기 창 설정
  //var skdSearchObj = document.getElementById("skdSearch");
  //검색 드롭다운메뉴
  //var categoryObj = document.querySelector("div.dropdown-menu");
  //기간으로 검색하기, 제목내용으로 검색하기 창 설정
  var $searchBtnSkdObj = $("button#searchBtnSKD");
  //검색 드롭다운메뉴
  var $categoryObj = $("div#skdSearchDropDown");

  //기간으로검색 id
  var $searchPeriod = $("#skdCategoryPeriod");
  //제목으로 검색 id
  var $searchTitle = $("#skdCategoryTitle");

  function categoryHandler() {
    // if (e.target.id == "skdCategoryPeriod") {
    //   createModal("skdSearchPeriod");
    // } else if (e.target.id == "skdCategoryTitle") {
    //   console.log("내용검색" + e.target.id);
    //   createModal("skdSearchTitle");
    // }
    $searchPeriod.on("click", function () {
      createModal("skdSearchPeriod");
    });
    $searchTitle.on("click", function () {
      createModal("skdSearchTitle");
    });
  }

  $searchBtnSkdObj.click(function () {
    $categoryObj.slideToggle(300);
  });

  function initSearchModal() {
    $categoryObj.click(function () {
      //categoryHandler();
    });
  }

  initSearchModal();
  categoryHandler();
  ////////////////////////////////////////////일정검색 드롭다운메뉴
  //modal 만드는 함수
  //파라미터 : class="modal" 의 id

  /**
   *
   * @param {*} id
   * 모달창 제작 함수
   */
  var skdno;
  function createModal(id) {
    //로컬스토리지에 있는 skdNo 불러오기
    skdno = localStorage.getItem("skdNo");

    var modal = document.getElementById(id);
    modal.classList.remove("hidden"); //모달열기

    //모달 버튼
    var closeBtn = modal.querySelector("button.cancel");
    var overlay = modal.querySelector(".modal_overlay");
    var deleteBtn = modal.querySelector("button.deleteBtn");
    var xBoxBtn = modal.querySelector("button.xBox");
    var modifyBtn = modal.querySelector("button.modifyBtn");

    var openModal = () => {
      modal.classList.remove("hidden");
    };
    var closeModal = () => {
      modal.classList.add("hidden");
    };

    //클릭이벤트
    //오버레이 부분 클릭 닫기
    overlay.addEventListener("click", closeModal);
    //모달창 닫기 버튼
    //엑스박스 닫기
    if (xBoxBtn != null) {
      xBoxBtn.addEventListener("click", closeModal);
    }
    //취소, 닫기 버튼
    if (closeBtn != null) {
      closeBtn.addEventListener("click", closeModal);
    }
    //수정 버튼
    if (modifyBtn != null) {
      modifyBtn.addEventListener("click", function () {
        modal.classList.add("hidden");
        //수정 버튼 클릭 시 수정 모달 뜸
        createModal("skdModifyDetail");
      });
    }
    if (deleteBtn != null) {
      var backurlDeleteSkd = "/back/deleteschedule";
      //SQL에서 일정번호만 있으면 삭제되는 구조라 skd_no만 보냄
      deleteBtn.addEventListener("click", function () {
        $.ajax({
          url: backurlDeleteSkd,
          method: "get",
          data: {
            skd_no: skdno,
          },
          success: function () {
            alert("일정이 삭제되었습니다!");
            loadSchedule();
          },
        });
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

          //ms 2021-07-31
          localStorage.setItem("title", titleValue.innerHTML);
          localStorage.setItem(
            "startDate",
            StartTimeValue.innerHTML.slice(0, 10)
          );
          localStorage.setItem(
            "startTime",
            StartTimeValue.innerHTML.slice(11, 16)
          );
          localStorage.setItem("endDate", EndTimeValue.innerHTML.slice(0, 10));
          localStorage.setItem("endTime", EndTimeValue.innerHTML.slice(11, 16));
          localStorage.setItem("content", ContentValue.innerHTML);
        });
      },
    });
  }

  //상세내역 모달에 데이터
  //일정 상세내역
  var shareObj = document.getElementById("skdDetailShare");
  var shareValue = shareObj.querySelector("td.skdDetailInputData");
  //console.log(shareValue);
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

  /**
   *
   *
   *
   * 일정 수정
   *
   *
   *
   */

  //var modifySkdFormObj = $("#modifySkdContent");
  var modifySkdSubmitBtn = $("button.modifySkdSubmit"); //수정 버튼
  var skdUpdateTypeObj = $("#skdUpdateTypeSelect"); //일정 종류 변수

  var skdUpdateTypeValue = "업무"; // 일정 종류 변경 함수
  skdUpdateTypeObj.change(function () {
    console.log(this.value);
    skdUpdateTypeValue = this.value + "";
  });

  //변경 내용을 담은 input 객체를 변수에 할당
  var skdUpdateTitle = $("#update_title"); //제목
  var skdUpdateContent = $("#input_content_update"); //내용
  var skdUpdateStartDate = $("#start_date_update"); // 일정 시작 날자
  var skdUpdateStartTime = $("#start_time_update"); // 일정 시작 시간
  var skdUpdateEndDate = $("#end_date_update"); //종료날짜
  var skdUpdateEndTime = $("#end_time_update"); //종료시간
  var skdUpdateShare2 = $('input[name="radio-2"]'); //일정 종류인데.. 적용이 안 됨 ㅠㅠ

  //skd_no, 일정번호를 localStorage에서 받아온다
  //var currentSkdNo = localStorage.getItem("skdNo");

  //팀, 개인 일정 선택
  var teamOrPersonalOption = "p";
  skdUpdateShare2.change(function () {
    console.log(this.value);
    teamOrPersonalOption = this.value;
    console.log(teamOrPersonalOption);
  });

  var UpdatePreTitleValue;
  var UpdatePreStartDate;
  var UpdatePreStartTime;
  var skdOriginEndDate;
  var skdOriginEndTime;
  var UdpatePreContentValue;

  //모달창에서 수정 버튼 클릭 시 이벤트
  $("#skdModifyBtn").click(function () {
    //일정 상세 내역 클릭 시 localStorage에 저장된 내역들을 불러와 변수에 지정
    UpdatePreTitleValue = localStorage.getItem("title");
    UpdatePreStartDate = localStorage.getItem("startDate");
    UpdatePreStartTime = localStorage.getItem("startTime");
    skdOriginEndDate = localStorage.getItem("endDate");
    skdOriginEndTime = localStorage.getItem("endTime");
    UdpatePreContentValue = localStorage.getItem("content");

    //test용 프린트
    // console.log(UpdatePreTitleValue);
    // console.log(UpdatePreStartDate);
    // console.log(UpdatePreStartTime);
    // console.log(skdOriginEndDate);
    // console.log(skdOriginEndTime);
    // console.log(UdpatePreContentValue);

    //기존 상세내역에 있었던 내용을 input에 넣기
    skdUpdateTitle.attr("value", UpdatePreTitleValue);
    skdUpdateStartDate.val(UpdatePreStartDate);
    skdUpdateStartTime.val(UpdatePreStartTime);
    skdUpdateEndDate.val(skdOriginEndDate);
    skdUpdateEndTime.val(skdOriginEndTime);
    skdUpdateContent.val(UdpatePreContentValue);
    // skdUpdateType.val(UdpatePreTypeValue);
  });

  /**
   * 일정 수정 ajax 함수
   * @param {*} teamOrPersonalOption
   * @param {*} backgroundColorUpdate
   */
  function updateSchedule(teamOrPersonalOption, backgroundColorUpdate) {
    var backSkdModify = "/back/modifyschedule";

    $.ajax({
      url: backSkdModify,
      method: "post",
      data: {
        title: skdUpdateTitle.val(),
        content: skdUpdateContent.val(),
        start: skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
        end: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
        allDay: false,
        backgroundColor: backgroundColorUpdate,
        calendarType: skdUpdateTypeValue,
        teamOrPersonal: teamOrPersonalOption,
        skd_no: skdno,
      },
      success: function () {
        console.log("success 이후 " + skdno);
        window.alert("일정이 변경되었습니다");
        //scheduleMenu로 돌아가는 트리거 이벤트
        loadSchedule();
      },
      error: function (request, status, error) {
        alert(
          "code:" +
            request.status +
            "\n" +
            "message:" +
            request.responseText +
            "\n" +
            "error:" +
            error
        );
      },
    });
  }

  //저장 버튼 클릭 시 이벤트
  function modifySkdSubmitHandler(e) {
    //일정번호를 기준으로 수정하는 것이기 때문에 skd_no가 반드시 필요하다
    console.log("저장 클릭" + skdno);

    if (teamOrPersonalOption == "p") {
      updateSchedule(teamOrPersonalOption, "#ffc107");
      e.preventDefault();
      //팀일정일 경우
    } else if (teamOrPersonalOption == "t") {
      updateSchedule(teamOrPersonalOption, "#28a745");
      e.preventDefault();
    }
  }
  //저장 버튼을 클릭하면 위 함수가 실행
  modifySkdSubmitBtn.click(modifySkdSubmitHandler);
});
