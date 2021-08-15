//board 페이지에서 적용할 내용
$(function () {
  //현재 몇페이지인지 나타내주는 객체
  var bdPageTitleObj = document.querySelector("h1.h1Title");
  //카테고리 버튼 객체
  var $dropDownCategoryBtnObj = $("button.searchBtn");
  //상세 카테고리 객체
  var $categoryObj = $("div.bdCategory");
  //카테고리 버튼 객체 클릭 시 상세 카테고리 slideToggle 이벤트 적용
  $dropDownCategoryBtnObj.click(function (e) {
    $categoryObj.slideToggle(300);
  });
  //게시글 목록 테이블 객체
  var bdTableObj = document.querySelector(
    "div.wrapper>div.main>main.content div.row div.table-responsive table"
  );

  //게시글 목록 테이블 객체 내 tbody 객체
  var tBodyObj = null;

  //페이지 목록 감싸고 있는 div 객체
  var pageGroupDivObj = document.querySelector("div.pageGroupDiv");
  //페이지 번호 목록 적용할 객체
  var pageGroupObj = document.querySelector("ul.pageGroup");

  //검색 form 객체
  var bdSearchFormObj = document.querySelector("form.searchForm");
  //검색할 카테고리 값
  var bdSearchCategory = null;

  //
  var pageli = null;
  var pagea = null;
  var bdNo = new Array();
  var bdTitle = new Array();
  var bdWriter = new Array();
  var bdDate = new Array();
  var pageGroup = new Array();
  //현재 페이지 기본 세팅 =1
  var currentPage = localStorage.getItem("bdCurrPage");
  //페이지 별 보여줄 게시글 갯수
  var cnt_per_page = 10;
  //페이지 그룹 당 보여주는 페이지 수
  var cnt_per_page_group = 4;

  var $titleObj;

  //검색 input 감싸고 있는 div 객체
  var inputGroupObj = document.querySelector("div.input-group");
  //검색 input 객체
  var searchObj = inputGroupObj.querySelector("input[type=text]");

  //게시글 목록 페이지에서 사용할 backurl
  //게시글 목록 보여주기
  var backurlBdPage = "/back/showbdpage";
  //게시글 검색 결과 보여주기
  var backurlBdSearch = "/back/searchboard";
  //현재 페이지그룹 보여주기
  var backurlPage = "/back/showpagegroup";
  //총 페이지 수 보여주기
  var backurlTotalPage = "/back/showtotalpage";

  //검색 카테고리 지정 시 이벤트 처리하는 핸들러
  function categoryHandler(e) {
    $categoryObj.hide();
    if (e.target.id == "categoryTitle") {
      $dropDownCategoryBtnObj.html("제목");
      searchObj.setAttribute("placeholder", "제목으로 검색하기");
      bdSearchCategory = "bd_title";
    } else {
      $dropDownCategoryBtnObj.html("작성자");
      searchObj.setAttribute("placeholder", "작성자로 검색하기");
      bdSearchCategory = "name";
    }
  }

  //검색 창 관련 submit 핸들러
  function bdSearchFormSubmitHandler(e) {
    //카테고리 지정하지 않았을 경우
    if (bdSearchCategory == null) {
      alert("카테고리가 지정되지 않았습니다");
    } else {
      //카테고리 지정 후 데이터 전송
      $.ajax({
        url: backurlBdSearch,
        method: "get",
        //카테고리 종류와 검색 단어 전송
        data: {
          bdSearchCategory: bdSearchCategory,
          bdSearchWord: searchObj.value,
        },
        success: function (responseData) {
          //현재 있는 tbody 제거 및 arr 값 초기화
          emptyBdElement(tBodyObj);
          //tbody 객체 재 생성
          createTbodyElement();
          $(responseData).each(function (i, e) {
            bdNo[i] = e.bd_no;
            bdTitle[i] = e.bd_title;
            bdWriter[i] = e.writer.name;
            bdDate[i] = e.bd_date;
          });
          //게시글 생성하는 함수 호출
          //검색은 페이지 빈 설정하지 않았으므로 1페이지로 설정
          for (var i = 0; i < bdNo.length; i++) {
            createBdElement(i, 1);
          }

          //게시글 제목 객체
          $titleObj = $(
            "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
          );

          //게시글 제목 클릭 시 이벤트 발생
          $titleObj.click(function (e) {
            localStorage.setItem("bdNumber", e.target.id);
            //클릭된현재객체의 href속성값 얻기 : .attr('href');
            var href = $(this).attr("href");
            switch (href) {
              case "board-detail.html":
                //게시글 상세 페이지로 이동
                $content.load(href, function (responseTxt, statusTxt, xhr) {
                  if (statusTxt == "error")
                    alert("Error: " + xhr.status + ": " + xhr.statusText);
                });
                break;
            }
            return false;
          });
        },
      });
    }
    e.preventDefault();
  }

  //카테고리 클릭 시 이벤트 발생
  $categoryObj.click(categoryHandler);
  //검색 form submit 이벤트 발생
  bdSearchFormObj.addEventListener("submit", bdSearchFormSubmitHandler);

  //prev버튼 클릭 핸들러
  function prevBtnClickHandler(e) {
    //초기 페이지 : 1
    if (currentPage == 1) {
      alert("이전으로 돌아갈 페이지가 없습니다");
    } else {
      //id가 설정되지 않은 경우에는 게시글 목록 타이틀에 있는 숫자 이용
      if (e.target.id == "") {
        currentPage = parseInt(bdPageTitleObj.innerText.split(" ")[0]) - 1;
      } else {
        //id 설정되어있는 경우 id-1
        currentPage = e.target.id - 1;
      }
      //로컬스토리지에 현재 페이지 저장
      localStorage.setItem("bdCurrPage", currentPage);

      //현재 페이지가 (저장되어있는 페이지그룹-1)*cnt_per_page_group = 즉 이전 페이지 그룹의 마지막 페이지 일때
      if (
        currentPage ==
        cnt_per_page_group *
          (parseInt(localStorage.getItem("bdCurrPageGroup")) - 1)
      ) {
        //로컬스토리지에 현재 페이지 그룹-1 저장
        localStorage.setItem(
          "bdCurrPageGroup",
          parseInt(localStorage.getItem("bdCurrPageGroup")) - 1
        );
        //페이지 그룹 반환 하는 ajax
        $.ajax({
          url: backurlPage,
          method: "get",
          //현재 페이지 그룹 전송
          data: { nthPageGroup: localStorage.getItem("bdCurrPageGroup") },
          success: function (responseData) {
            //페이지 그룹 객체 삭제
            removeElement(pageGroupObj);
            //페이지 요소 비우기
            emptyPageElement();
            //페이지 그룹 재 생성
            createPageNumBigContainer();
            //반환된 페이지 저장
            $(responseData).each(function (i, e) {
              pageGroup[i] = e;
            });
            //prev 버튼 생성
            createPagePrev();
            //받아온 페이지 번호 생성하는 함수 호출
            for (var i = 0; i < pageGroup.length; i++) {
              createPageNum(pageGroup[i]);
            }
            //next 버튼 생성
            createPageNext();
          },
        });
      }
      //현재 페이지에 해당하는 게시글 불러오는 함수 호출
      loadCurrentBoard();
    }
  }

  //next버튼 클릭 핸들러
  function nextBtnClickHandler(e) {
    //총 페이지와 현재 페이지가 같은 경우
    if (totalPage == currentPage) {
      alert("다음으로 넘어갈 페이지가 없습니다");
    } else {
      //아이디 설정되지 않은 경우 게시글 제목에 있는 페이지 수 이용
      if (e.target.id == "") {
        currentPage = parseInt(bdPageTitleObj.innerText.split(" ")[0]) + 1;
      } else {
        //아이디 설정되어 있는 경우 아이디+1
        currentPage = e.target.id + 1;
      }
      //로컬스토리지에 현재 페이지 저장
      localStorage.setItem("bdCurrPage", currentPage);
      //현재페이지가 다음 페이지그룹+1과 같은 경우
      if (
        currentPage ==
        cnt_per_page_group * parseInt(localStorage.getItem("bdCurrPageGroup")) +
          1
      ) {
        //로컬스토리지에 새로운 페이지 그룹 정보 저장
        localStorage.setItem(
          "bdCurrPageGroup",
          parseInt(localStorage.getItem("bdCurrPageGroup")) + 1
        );
        //페이지 그룹 반환 하는 ajax
        $.ajax({
          url: backurlPage,
          method: "get",
          data: { nthPageGroup: localStorage.getItem("bdCurrPageGroup") },
          success: function (responseData) {
            //페이지 그룹 객체 제거
            removeElement(pageGroupObj);
            //페이지 배열 초기화
            emptyPageElement();
            //페이지 그룹 재 생성
            createPageNumBigContainer();
            //페이지 그룹 저장
            $(responseData).each(function (i, e) {
              pageGroup[i] = e;
            });
            //prev 버튼 생성
            createPagePrev();
            //페이지 번호 생성하여 적용하는 함수 호출
            for (var i = 0; i < pageGroup.length; i++) {
              createPageNum(pageGroup[i]);
            }
            //next 버튼 생성
            createPageNext();
          },
        });
      }
      //현재 페이지에 해당하는 게시글 불러오는 함수 호출
      loadCurrentBoard();
    }
  }

  //현재 페이지에 해당하는 게시글 불러오는 함수
  function loadCurrentBoard() {
    //해당 페이지에 해당하는 게시글 목록 불러오는 ajax
    $.ajax({
      url: backurlBdPage,
      method: "get",
      data: {
        //현재 페이지 전송
        bdCurrentPage: currentPage,
      },
      success: function (responseData) {
        //게시글 목록 tbody 제거 및 배열 초기화
        emptyBdElement(tBodyObj);
        //tbody 재생성
        createTbodyElement();
        $(responseData).each(function (i, e) {
          bdNo[i] = e.bd_no;
          bdTitle[i] = e.bd_title;
          bdWriter[i] = e.writer.name;
          bdDate[i] = e.bd_date;
        });
        //게시글 목록 생성하는 함수 호출
        for (var i = 0; i < bdNo.length; i++) {
          createBdElement(i, currentPage);
        }
        //게시글 제목 객체
        $titleObj = $(
          "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
        );
        //게시글 제목 객체 클릭 시 이벤트 발생
        $titleObj.click(function (e) {
          localStorage.setItem("bdNumber", e.target.id);
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
      },
    }); //ajax 끝
    //현재 페이지 값 대입
    bdPageTitleObj.innerHTML = currentPage + " PAGE";
  }

  //페이지 그룹 생성하는 역할의 함수
  function createPageNumBigContainer() {
    pageGroupObj = document.createElement("ul");
    pageGroupObj.setAttribute("class", "list-inline pageGroup");
    pageGroupDivObj.appendChild(pageGroupObj);
  }

  //prev 버튼 만들어주는 함수
  function createPagePrev() {
    //prev 버튼 들어갈 li 객체 생성
    var prevli = document.createElement("li");
    //li객체에 스타일 적용
    prevli.setAttribute("class", "list-inline-item");

    //클릭하면 이전 페이지로 이동해야하므로 a 객체 생성
    var preva = document.createElement("a");
    //a객체에 스타일 적용
    preva.setAttribute("class", "text-muted");

    //"prev" 글자 bold 처리하기 위한 strong 객체 생성
    var prevstrong = document.createElement("strong");
    //값 대입
    prevstrong.innerHTML = "prev";

    //차례대로 각 부모에 append
    preva.appendChild(prevstrong);
    prevli.appendChild(preva);
    pageGroupObj.appendChild(prevli);

    //prev 버튼에 클릭 이벤트 등록
    prevli.addEventListener("click", prevBtnClickHandler);
  }
  //next 버튼 만들어주는 함수
  function createPageNext() {
    //next 버튼 들어갈 li 객체 생성
    var nextli = document.createElement("li");
    //li객체에 스타일 적용
    nextli.setAttribute("class", "list-inline-item");

    //클릭하면 다음 페이지로 이동해야하므로 a 객체 생성
    var nexta = document.createElement("a");
    //a객체에 스타일 적용
    nexta.setAttribute("class", "text-muted");

    //"next" 글자 bold 처리하기 위한 strong 객체 생성
    var nextstrong = document.createElement("strong");
    //값 대입
    nextstrong.innerHTML = "next";

    //차례대로 각 부모에 append
    nexta.appendChild(nextstrong);
    nextli.appendChild(nexta);
    pageGroupObj.appendChild(nextli);

    //next 버튼에 클릭 이벤트 등록
    nextli.addEventListener("click", nextBtnClickHandler);
  }

  //페이지 클릭 핸들러
  function pageClickHandler(e) {
    //현재 페이지 = 지금 클릭한 숫자
    currentPage = e.target.id;
    //로컬스토리지에 현재 페이지 저장
    localStorage.setItem("bdCurrPage", currentPage);
    loadCurrentBoard();
  }

  //페이지 만들어 주는 함수
  function createPageNum(i) {
    //페이지 번호 들어갈 li 생성
    pageli = document.createElement("li");
    //li객체에 스타일 적용
    pageli.setAttribute("class", "list-inline-item");

    //페이지 클릭시 해당 페이지로 이동해야하므로 a 태그 생성
    pagea = document.createElement("a");
    //a태그에 스타일 적용
    pagea.setAttribute("class", "text-muted");
    //값 대입
    pagea.innerHTML = i;
    //id 해당 페이지 수로 등록
    pagea.setAttribute("id", i);

    //li객체에 a객체 append
    pageli.appendChild(pagea);

    //pageli 클릭 시 이벤트 발생
    pageli.addEventListener("click", pageClickHandler);

    //페이지 그룹 객체에 li객체 append
    pageGroupObj.appendChild(pageli);
  }

  //target이 null이 아닐때 제거
  function removeElement(target) {
    if (target != null) {
      target.remove();
    }
  }

  //페이지 이동 시 기존 객체 및 배열 지워주기 위함
  function emptyBdElement(target) {
    removeElement(target);
    bdNo = [];
    bdTitle = [];
    bdWriter = [];
    bdDate = [];
  }

  //페이지 그룹 변경 시 기존 페이지 지워주기 위함
  function emptyPageElement() {
    pageGroup = [];
  }

  //게시글 목록이 들어가는 tbody객체 만들어주는 함수
  function createTbodyElement() {
    tBodyObj = document.createElement("tbody");
    tBodyObj.setAttribute("class", "bdTbody");

    bdTableObj.appendChild(tBodyObj);
  }

  //게시글 목록 만들어주는 함수
  function createBdElement(i, j) {
    //tr객체 생성
    var tr = document.createElement("tr");
    //th객체 생성
    var th = document.createElement("th");
    //th객체 scope 속성 등록
    th.setAttribute("scope", "row");
    //게시글 번호 대입
    //(현재페이지-1)*페이지최대게시글수 + i(0~)+1
    th.innerHTML = (j - 1) * cnt_per_page + i + 1;

    //제목들어갈 td 객체 생성
    var tdTitle = document.createElement("td");
    //제목 클릭 시 게시글 상세 페이지로 이동해야하므로 a 태그 생성
    var a = document.createElement("a");
    //a객체 스타일 적용
    a.setAttribute("class", "sidebar-link-js");
    //a객체 상세 스타일 적용
    a.setAttribute("style", "color: black");
    //a객체 게시글 번호로 아이디 설정
    a.setAttribute("id", bdNo[i]);
    //이동 페이지 지정
    a.setAttribute("href", "board-detail.html");
    //값 대입
    a.innerText = bdTitle[i];
    //td객체에 a 태그 append
    tdTitle.appendChild(a);

    //작성자등러갈 td 객체 생성
    var tdWriter = document.createElement("td");
    //값 대입
    tdWriter.innerHTML = bdWriter[i];
    //게시글 날짜 들어갈 td객체 생성
    var tdDate = document.createElement("td");
    //값 대입
    tdDate.innerHTML = bdDate[i];

    //순서대로 각 부모에 append
    tr.appendChild(th);
    tr.appendChild(tdTitle);
    tr.appendChild(tdWriter);
    tr.appendChild(tdDate);

    tBodyObj.appendChild(tr);
  }

  //게시판 첫 화면 조회하는 ajax
  loadCurrentBoard();

  //페이지 그룹 반환 하는 ajax
  $.ajax({
    url: backurlPage,
    method: "get",
    //첫 화면 페이지 그룹 : 1
    data: { nthPageGroup: 1 },
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        pageGroup[i] = e;
      });
      //prev 버튼 생성
      createPagePrev();
      //페이지 번호 생성하는 함수 호출
      for (var i = 1; i < pageGroup.length + 1; i++) {
        createPageNum(i);
      }
      //next 버튼 생성
      createPageNext();
    },
  });
  //총 페이지 반환하는 ajax
  $.ajax({
    url: backurlTotalPage,
    method: "get",
    success: function (responseData) {
      totalPage = responseData;
    },
  });

  //글쓰기 버튼 클릭 시 글쓰기 페이지로 이동
  //글쓰기 버튼 찾는 Obj
  var $registerObj = $(
    "div.wrapper>div.main>main.content div.row div.card-header>a"
  );

  //main.content Obj 찾기
  var $content = $("main.content");
  //글쓰기 버튼 클릭 시 이벤트 발생
  $registerObj.click(function () {
    var href = $(this).attr("href");
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
});
