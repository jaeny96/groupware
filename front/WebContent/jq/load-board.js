//board 페이지에서 적용할 내용
$(function () {
  var bdPageTitleObj = document.querySelector("h1.h1Title");
  var btnGroupObj = document.querySelector("div.btn-group");
  var categoryObj = btnGroupObj.querySelector("div.dropdown-menu");
  var bdTableObj = document.querySelector(
    "div.wrapper>div.main>main.content div.row div.table-responsive table"
  );
  var tBodyObj = null;
  var pageGroupObj = document.querySelector("ul.pageGroup");
  var prevBtnObj = document.querySelector(
    "ul.pageGroup>li.list-inline-item:first-child a"
  );

  prevBtnObj.removeAttribute("style");

  var bdSearchCategoryBtnObj = document.querySelector("button.searchBtn");
  var bdSearchFormObj = document.querySelector("form.searchForm");
  var bdSearchCategory = null;
  console.log(bdSearchFormObj);

  var pageli = null;
  var pagea = null;
  var bdNo = new Array();
  var bdTitle = new Array();
  var bdWriter = new Array();
  var bdDate = new Array();
  var pageGroup = new Array();
  var totalPage;
  //현재 페이지 기본 세팅 =1
  var currentPage = localStorage.getItem("bdCurrPage");
  //페이지 별 보여줄 게시글 갯수
  var cnt_per_page = 10;

  var $titleObj;

  var inputGroupObj = document.querySelector("div.input-group");
  var searchObj = inputGroupObj.querySelector("input[type=text]");

  var backurlBdPage = "/back/showbdpage";
  var backurlBdSearch = "/back/searchboard";
  var backurlPage = "/back/showpagegroup";

  //검색 카테고리 지정 시 이벤트 처리하는 핸들러
  function categoryHandler(e) {
    if (e.target.id == "categoryTitle") {
      bdSearchCategoryBtnObj.innerHTML = "제목";
      searchObj.setAttribute("placeholder", "제목으로 검색하기");
      bdSearchCategory = "bd_title";
    } else {
      bdSearchCategoryBtnObj.innerHTML = "작성자";
      searchObj.setAttribute("placeholder", "작성자로 검색하기");
      bdSearchCategory = "name";
    }
  }

  //검색 창 관련 submit 핸들러
  function bdSearchFormSubmitHandler(e) {
    if (bdSearchCategory == null) {
      alert("카테고리가 지정되지 않았습니다");
    } else {
      $.ajax({
        url: backurlBdSearch,
        method: "get",
        data: {
          bdSearchCategory: bdSearchCategory,
          bdSearchWord: searchObj.value,
        },
        success: function (responseData) {
          emptyBdElement(tBodyObj);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            bdNo[i] = e.bd_no;
            bdTitle[i] = e.bd_title;
            bdWriter[i] = e.writer.name;
            bdDate[i] = e.bd_date;
          });
          for (var i = 0; i < bdNo.length; i++) {
            createBdElement(i, 1);
          }
          $titleObj = $(
            "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
          );

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
      });
    }
    e.preventDefault();
  }

  categoryObj.addEventListener("click", categoryHandler);
  bdSearchFormObj.addEventListener("submit", bdSearchFormSubmitHandler);

  //prev버튼 클릭 핸들러
  function prevBtnClickHandler(e) {
    if (currentPage == 1) {
      alert("이전으로 돌아갈 페이지가 없습니다");
    } else {
      if (e.target.id == "") {
        currentPage = parseInt(bdPageTitleObj.innerText.split(" ")[0]) - 1;
      } else {
        currentPage = e.target.id - 1;
      }
      localStorage.setItem("bdCurrPage", currentPage);
      $.ajax({
        url: backurlBdPage,
        method: "get",
        data: {
          bdCurrentPage: currentPage,
        },
        success: function (responseData) {
          emptyBdElement(tBodyObj);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            bdNo[i] = e.bd_no;
            bdTitle[i] = e.bd_title;
            bdWriter[i] = e.writer.name;
            bdDate[i] = e.bd_date;
          });
          for (var i = 0; i < bdNo.length; i++) {
            createBdElement(i, currentPage);
          }
          $titleObj = $(
            "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
          );

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
        }, //
      }); //ajax 끝
      bdPageTitleObj.innerHTML = currentPage + " PAGE";
    } //else
  }

  prevBtnObj.addEventListener("click", prevBtnClickHandler);

  //next버튼 클릭 핸들러
  function nextBtnClickHandler(e) {
    if (totalPage == currentPage) {
      alert("다음으로 넘어갈 페이지가 없습니다");
    } else {
      if (e.target.id == "") {
        currentPage = parseInt(bdPageTitleObj.innerText.split(" ")[0]) + 1;
      } else {
        currentPage = e.target.id + 1;
      }

      localStorage.setItem("bdCurrPage", currentPage);
      $.ajax({
        url: backurlBdPage,
        method: "get",
        data: {
          bdCurrentPage: currentPage,
        },
        success: function (responseData) {
          emptyBdElement(tBodyObj);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            bdNo[i] = e.bd_no;
            bdTitle[i] = e.bd_title;
            bdWriter[i] = e.writer.name;
            bdDate[i] = e.bd_date;
          });
          for (var i = 0; i < bdNo.length; i++) {
            createBdElement(i, currentPage);
          }
          $titleObj = $(
            "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
          );

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
        }, //
      }); //ajax 끝
      bdPageTitleObj.innerHTML = currentPage + " PAGE";
    } //else
  }

  //nextPage 버튼 만들어주는 함수
  function createPageNext() {
    var nextli = document.createElement("li");
    nextli.setAttribute("class", "list-inline-item");
    var nexta = document.createElement("a");
    nexta.setAttribute("class", "text-muted");
    var nextstrong = document.createElement("strong");
    nextstrong.innerHTML = "next";
    nexta.appendChild(nextstrong);
    nextli.appendChild(nexta);
    pageGroupObj.appendChild(nextli);

    nextli.addEventListener("click", nextBtnClickHandler);
  }

  //페이지 클릭 핸들러
  function pageClickHandler(e) {
    currentPage = e.target.id;

    localStorage.setItem("bdCurrPage", currentPage);
    $.ajax({
      url: backurlBdPage,
      method: "get",
      data: {
        bdCurrentPage: currentPage,
      },
      success: function (responseData) {
        emptyBdElement(tBodyObj);
        createTbodyElement();
        $(responseData).each(function (i, e) {
          bdNo[i] = e.bd_no;
          bdTitle[i] = e.bd_title;
          bdWriter[i] = e.writer.name;
          bdDate[i] = e.bd_date;
        });
        for (var i = 0; i < bdNo.length; i++) {
          createBdElement(i, currentPage);
        }
        $titleObj = $(
          "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
        );

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
      }, //
    }); //ajax 끝
    bdPageTitleObj.innerHTML = currentPage + " PAGE";
  }

  //페이지 만들어 주는 함수
  function createPageNum(i) {
    pageli = document.createElement("li");
    pageli.setAttribute("class", "list-inline-item");
    pagea = document.createElement("a");
    pagea.setAttribute("class", "text-muted");
    pagea.innerHTML = i;
    pagea.setAttribute("id", i);
    pageli.appendChild(pagea);
    pageli.addEventListener("click", pageClickHandler);
    pageGroupObj.appendChild(pageli);
  }

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
  function emptyPageElement(target) {
    pagea = [];
    pageli = [];
  }

  //게시글 목록이 들어가는 tbody객체 만들어주는 함수
  function createTbodyElement() {
    tBodyObj = document.createElement("tbody");
    tBodyObj.setAttribute("class", "bdTbody");

    bdTableObj.appendChild(tBodyObj);
  }

  //게시글 목록 만들어주는 함수
  function createBdElement(i, j) {
    var tr = document.createElement("tr");
    var th = document.createElement("th");
    th.setAttribute("scope", "row");
    th.innerHTML = (j - 1) * cnt_per_page + i + 1;
    var tdTitle = document.createElement("td");
    var tdWriter = document.createElement("td");
    tdWriter.innerHTML = bdWriter[i];
    var tdDate = document.createElement("td");
    tdDate.innerHTML = bdDate[i];
    var a = document.createElement("a");
    a.setAttribute("class", "sidebar-link-js");
    a.setAttribute("style", "color: black");
    a.setAttribute("id", bdNo[i]);
    a.setAttribute("href", "board-detail.html");
    a.innerText = bdTitle[i];

    tdTitle.appendChild(a);
    tr.appendChild(th);
    tr.appendChild(tdTitle);
    tr.appendChild(tdWriter);
    tr.appendChild(tdDate);
    tBodyObj.appendChild(tr);
  }

  //게시판 첫 화면 조회하는 ajax
  $.ajax({
    url: backurlBdPage,
    method: "get",
    data: {
      bdCurrentPage: currentPage,
    },
    success: function (responseData) {
      emptyBdElement(tBodyObj);
      createTbodyElement();
      $(responseData).each(function (i, e) {
        bdNo[i] = e.bd_no;
        bdTitle[i] = e.bd_title;
        bdWriter[i] = e.writer.name;
        bdDate[i] = e.bd_date;
      });
      for (var i = 0; i < bdNo.length; i++) {
        createBdElement(i, currentPage);
      }
      $titleObj = $(
        "div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
      );

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
      bdPageTitleObj.innerHTML = currentPage + " PAGE";
    }, //
  }); //ajax 끝
  bdPageTitleObj.innerHTML = localStorage.getItem("bdCurrPage") + " PAGE";

  //총 페이지 수 반환 하는 ajax
  $.ajax({
    url: backurlPage,
    method: "get",
    success: function (responseData) {
      totalPage = responseData;
      console.log("page " + totalPage);
      for (var i = 1; i < totalPage + 1; i++) {
        createPageNum(i);
      }
      createPageNext();
    },
  });

  //글쓰기 버튼 클릭 시 글쓰기 페이지로 이동
  //글쓰기 버튼 찾는 Obj
  var $registerObj = $(
    "div.wrapper>div.main>main.content div.row div.card-header>a"
  );

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
});
