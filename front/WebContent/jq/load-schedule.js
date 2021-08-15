$(function () {
  //전체 검색 모달 창
  //기간검색모달창
  //내용검색 모달창
  var modalSearchAll = document.querySelector("div#skdSearchAll");
  var modalPeriodSearch = document.querySelector("div#skdSearchPeriod");
  var modalContentSearch = document.querySelector("div#skdSearchTitle");

  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );

  //전체검색 모달 창 내용(제목, 내용, 시작일, 종료일)
  // var modalSearchAllTitle = modalSearchAll.querySelector("input.allTitle");
  // var modalSearchAllContent = modalSearchAll.querySelector("input.allContent");
  // var modalSearchAllSDate = modalSearchAll.querySelector("input.allSDate");
  // var modalSerchAllEDate = modalSearchAll.querySelector("input.allEDate");

  //기간검색버튼
  //내용,제목검색버튼
  //전체검색버튼
  //검색결과창
  var $searchResultP = $(".searchPeriodBtn");
  var $searchResultC = $(".searchContentBtn");
  // var $searchResultA = $(".searchAllBtn");
  var $content = $("#skdDetailShare");

  //기간검색 데이터 받아오기
  $searchResultP.on("click", function () {
    var href = $(this).attr("href");
    // var id = loginInfoIdObj.innerHTML;
    // var dept = id.substring(0, 3);
    var loginedId = localStorage.getItem("loginInfo");
    var loginedDept = loginedId.substring(0, 3);
    var id = loginedId;
    var dept = loginedDept;
    //input날짜 값
    var sdatev = modalPeriodSearch.querySelector("input.cSdate");
    var sdate = sdatev.value;
    var edatev = modalPeriodSearch.querySelector("input.cEdate");
    var edate = edatev.value;
    console.log(id);
    console.log(dept);
    if (sdate == null || sdate == "" || edate == null || edate == "") {
      alert("기간을 입력하세요.");
    } else if (new Date(edate) - new Date(sdate) < 0) {
      alert("종료일이 시작일보다 먼저입니다.");
    } else {
      var queryString =
        "id=" +
        id +
        "&" +
        "dept_id=" +
        dept +
        "&" +
        "start_date=" +
        sdate +
        "&" +
        "end_date=" +
        edate;

      window.location.replace("schedule-detail-search.html?" + queryString);
      switch (href) {
        case "schedule-detail-search.html":
          $content.load(href, function (responseTxt, statusTxt, xhr) {
            if (statusTxt == "error")
              alert("Error: " + xhr.status + ": " + xhr.statusText);
          });
          break;
      }
      return false;
    }
  });

  //내용검색 데이터 받아오기
  $searchResultC.on("click", function () {
    var href = $(this).attr("href");
    // var id = loginInfoIdObj.innerHTML;
    var loginedId = localStorage.getItem("loginInfo");
    var id = loginedId;

    var titlev = modalContentSearch.querySelector("input.pTitle");
    var title = titlev.value;
    var contentv = modalContentSearch.querySelector("input.pContent");
    var content = contentv.value;
    console.log(id);
    if (title == null || title == "") {
      alert("검색어를 입력하세요.");
    } else if (content == null || content == "") {
      alert("검색어를 입력하세요");
    } else {
      var queryString =
        "id=" +
        id +
        "&" +
        "skd_title=" +
        title +
        "&" +
        "skd_content=" +
        content;
      // console.log(queryString);
      window.location.replace("schedule-detail-search.html?" + queryString);
      switch (href) {
        case "schedule-detail-search.html":
          $content.load(href, function (responseTxt, statusTxt, xhr) {
            if (statusTxt == "error")
              alert("Error: " + xhr.status + ": " + xhr.statusText);
          });
          break;
      }
      return false;
    }
  });
});
