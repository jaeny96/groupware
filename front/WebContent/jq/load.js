$(function () {
  //DOM트리에서 nav>ol>li>a객체들 찾기
  var $menuObj = $(
    "div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a"
  );

  //DOM트리에서 section객체 찾기
  var $div = $("div.wrapper>div.main>main.content");

  $div.load("main.html");
  $menuObj.click(function () {
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    $menuObj.closest("li").attr("class", "sidebar-item");
    var href = $(this).attr("href");
	console.log(href);
    switch (href) {
      case "board.html":
      case "schedule.html":
      //			case './ui-alerts.html':
      case "employee.html":
      case "profile.html":
      case "pages-settings.html":
      case "board-register.html":
      case "main.html":
        $(this).closest("li").attr("class", "sidebar-item active");
        $div.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
});
