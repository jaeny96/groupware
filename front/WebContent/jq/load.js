//main-form 에서 적용할 내용
$(function () {
  //sidebar menu Obj 찾기
  var $menuObj = $(
    "div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a"
  );

  //DOM트리에서 section객체 찾기
  var $content = $("div.wrapper>div.main>main.content");

  //main-form 로드 되자마자 main.html 페이지 불러오기
  $content.load("main.html");

  $menuObj.click(function () {
    //sidebar-item 활성화 모두 풀기
    $menuObj.closest("li").attr("class", "sidebar-item");
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    var href = $(this).attr("href");

    switch (href) {
      case "board.html":
      case "schedule.html":
      //			case './ui-alerts.html':
      case "employee.html":
      case "profile.html":
      case "pages-settings.html":
      // case "board-register.html":
      case "main.html":
        //클릭한 객체의 sidebar-item만 활성화 시키기
        $(this).closest("li").attr("class", "sidebar-item active");
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
});
