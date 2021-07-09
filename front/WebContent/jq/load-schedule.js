// function getDBData() {
//   var arr = [{ title: "event1", start: "ssss", end: "ssss", content: "ddddd" }];

//   $a.jax({
//     url: "schedule.html",
//     type: "post",
//     data: {},
//   });
// }

$(function () {
  //기간검색버튼
  var $searchResultP = $("button.searchPeriodBtn");
  //내용,제목검색버튼
  var $searchResultC = $("button.searchContentBtn");
  //전체검색버튼
  var $searchResultA = $("button.searchAllBtn");
  console.log($searchResultP);
  console.log($searchResultC);
  console.log($searchResultA);

  var $content = $("main.content");
  console.log($content);

  $searchResultP.click(function () {
    var href = $(this).attr("href");
    console.log(href);
    switch (href) {
      case "schedule-detail-search.html":
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
  $searchResultC.click(function () {
    var href = $(this).attr("href");
    console.log(href);
    switch (href) {
      case "schedule-detail-search.html":
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
  $searchResultA.click(function () {
    var href = $(this).attr("href");
    console.log(href);
    switch (href) {
      case "schedule-detail-search.html":
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
});
