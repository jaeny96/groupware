$(function () {
  //DOM트리에서 nav>ol>li>a객체들 찾기
  var $clickObj = $(
    "div.wrapper>div.main>main.content div.row div.card-header>a"
  );
  console.log($clickObj);
  //DOM트리에서 section객체 찾기
  var $div = $("div.wrapper>div.main>main.content");
  console.log($div);
  // $div.load("main.html");
  $clickObj.click(function () {
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    var href = $(this).attr("href");
    console.log(href);
    switch (href) {
      case "board-register.html":
        $div.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
});
