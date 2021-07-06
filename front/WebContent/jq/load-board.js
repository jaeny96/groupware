//board 페이지에서 적용할 내용
$(function () {
  //글쓰기 버튼 클릭 시 글쓰기 페이지로 이동
  //글쓰기 버튼 찾는 Obj
  var $registerObj = $(
    "div.wrapper>div.main>main.content div.row div.card-header>a"
  );
  // console.log($registerObj);

  //제목 클릭 시 게시글 상세 페이지로 이동
  //제목 찾는 Obj
  var $titleObj = $(
    "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2)>a"
  );

  // console.log($titleObj);

  //main.content Obj 찾기
  var $content = $("main.content");
  // console.log($content);
  $registerObj.click(function () {
    var href = $(this).attr("href");
    // console.log(href);
    switch (href) {
      case "board-register.html":
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

  $titleObj.click(function () {
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    var href = $(this).attr("href");
    switch (href) {
      case "board-detail.html":
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    return false;
  });
});
