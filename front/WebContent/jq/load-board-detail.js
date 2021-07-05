$(function () {
  var $menuObj = $(
    "div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li"
  );
  var $modifyBtnObj = $(
    "div.wrapper>div.main>main.content div.row div.card-body button.modifyBtn"
  );
  var $deleteBtnObj = $(
    "div.wrapper>div.main>main.content div.row div.card-body button.deleteBtn"
  );

  var $cmDeleteBtnObj = $(
    "div.wrapper>div.main>main.content div.row div.card-body a.cmDeleteBtn"
  );
  console.log($cmDeleteBtnObj);

  var $content = $("main.content");
  console.log($content);
  // $div.load("main.html");
  $modifyBtnObj.click(function () {
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    var href = $(this).attr("href");
    console.log(href);
    switch (href) {
      case "board-modify.html":
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          $("#summernote").summernote({
            height: 600, // 에디터 높이
            minHeight: null, // 최소 높이
            maxHeight: null, // 최대 높이
            focus: true, // 에디터 로딩후 포커스를 맞출지 여부
            lang: "ko-KR", // 한글 설정
            placeholder: "최대 2048자까지 쓸 수 있습니다", //placeholder 설정
          });
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });

  $deleteBtnObj.click(function () {
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    var href = $(this).attr("href");
    console.log(href);
    switch (href) {
      case "board.html":
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
  $cmDeleteBtnObj.click(function () {
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    var href = $(this).attr("href");
    console.log(href);
    console.log($content);
    switch (href) {
      case "board.html":
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
});
