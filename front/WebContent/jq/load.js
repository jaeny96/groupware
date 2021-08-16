$(function () {
  //메뉴 이동 시 변경 될 부분
  var $content = $("div.wrapper>div.main>main.content");
  //로그인 여부 확인
  var backurlCheckLogined = "/back/checkedlogined";
  $.ajax({
    url: backurlCheckLogined,
    method: "get",
    success: function (responseData) {
      //로그인 되어있을 경우
      if (responseData.status == 1) {
        //게시판 관련 로컬 정보 미리 저장
        //현재 페이지, 현재 페이지 그룹 관련
        localStorage.setItem("bdCurrPage", 1);
        localStorage.setItem("bdCurrPageGroup", 1);

        //우측 상단 프로필 관련 객체
        var $dropDownProfileAObj = $("a.dropdownProfile");
        var $dropDownProfileItemObj = $("div.profileDropdown");
        $dropDownProfileItemObj.hide();
        //프로필 클릭 시 슬라이드 토글 이벤트 발생
        $dropDownProfileAObj.click(function (e) {
          $dropDownProfileItemObj.slideToggle(300);
        });

        //전자결재 메뉴 첫번째 드롭다운 메뉴 관련 객체
        var $apMenuFirstDropDownAObj = $("a.apMenu");
        var $apMenuFirstDropDownItemObj = $("ul#docMenuFirst");
        //전자결재 메뉴 클릭 시 슬라이드 토글 이벤트 발생
        $apMenuFirstDropDownAObj.click(function (e) {
          $apMenuFirstDropDownItemObj.slideToggle(300);
        });

        //전자결재 문서함 관련 드롭다운 메뉴 관련 객체
        var $docListDropDownMenuAObj = $("a.docListDropDownMenu");
        var $docListDropDownMenuItemObj = $("ul#docList");
        //전자결재 문서함 메뉴 클릭 시 슬라이드 토글 이벤트 발생
        $docListDropDownMenuAObj.click(function (e) {
          $docListDropDownMenuItemObj.slideToggle(300);
        });

        //html내 메인 태그
        var mainObj = document.querySelector("div.wrapper>div.main");
        //프로필 내용 담겨있는 객체
        var mainProfileObj = mainObj.querySelector("div.profileDropdown");
        //로그인한 사원의 아이디가 들어갈 위치
        var mainLoginIdObj = mainProfileObj.querySelector("span.loginId");
        //로그인한 사원의 이름이 들어갈 위치
        var mainLoginNameObj = mainProfileObj.querySelector("span.loginName");

        var mainContentObj = mainObj.querySelector("main.content");
        //최근 게시글 관련 tbody 객체
        var mainBdTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainBdTbody"
        );
        //결재예정 문서 관련 tbody 객체
        var mainApTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainApTbody"
        );
        //오늘 일정 관련 tbody 객체
        var mainSkdTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainSkdTbody"
        );
        //나의 휴가 정보 관련 tbody객체
        var mainLeaveTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainLeaveTbody"
        );
        //부여된 휴가 일수 들어갈 위치
        var mainGrantDaysObj =
          mainLeaveTBodyObj.querySelector("td.mainGrantDays");
        //사용한 휴가 일수 들어갈 위치
        var mainUseDaysObj = mainLeaveTBodyObj.querySelector("td.mainUseDays");
        //남은 휴가 일수 들어갈 위치
        var mainRemainDaysObj =
          mainLeaveTBodyObj.querySelector("td.mainRemainDays");

        //로그인한 사원의 아이디값이 들어갈 변수
        var mainLoginId;
        //로그인한 사원의 이름값이 들어갈 변수
        var mainLoginName;

        //최근게시글 아이디값이 들어갈 arr
        var mainBdId = new Array();
        //최근게시글 제목값이 들어갈 arr
        var mainBdTitle = new Array();
        //최근게시글 작성자값이 들어갈 arr
        var mainBdWriter = new Array();
        //최근게시글 날짜값이 들어갈 arr
        var mainBdDate = new Array();

        //결재예정문서 문서번호값이 들어갈 arr
        var mainApId = new Array();
        //결재예정문서 제목값이 들어갈 arr
        var mainApTitle = new Array();
        //결재예정문서 날짜값이 들어갈 arr
        var mainApDate = new Array();

        //로그인한 사원의 부여된 휴가일값이 들어갈 변수
        var mainGrantLeave;
        //로그인한 사원의 남은 휴가일값이 들어갈 변수
        var mainRemainLeave;

        //오늘의 일정 일정번호값이 들어갈 arr
        var mainSkdId = new Array();
        //오늘의 일정 시간값이 들어갈 arr
        var mainSkdDate = new Array();
        //오늘의 일정 일정 제목값이 들어갈 arr
        var mainSkdTitle = new Array();

        //프로필 정보를 채우는 역할의 함수
        function insertProfileInfo() {
          mainLoginIdObj.innerHTML = mainLoginId;
          mainLoginNameObj.innerHTML = mainLoginName;
        }

        //결재예정문서의 정보 및 tr/td 객체들을 생성해주는 역할의 함수
        function createMainApElement(i) {
          //tr객체 생성
          var tr = document.createElement("tr");
          //결재예정문서 문서번호 값이 들어갈 td객체 생성
          var tdApId = document.createElement("td");
          //값 대입
          tdApId.innerHTML = mainApId[i];

          //결재예정문서 제목값이 들어갈 td객체 생성
          var tdApTitle = document.createElement("td");
          //제목 클릭 시 해당 상세 페이지로 넘어가야하므로 a 객체 생성
          var tdApTitleA = document.createElement("a");
          //값 대입
          tdApTitleA.innerHTML = mainApTitle[i];
          //a태그에 클래스 속성 부여함으로 스타일 적용
          tdApTitleA.setAttribute("class", "sidebar-link-js");
          //a태그에 id 속성 부여
          tdApTitleA.setAttribute("id", mainApId[i]);
          //a태그 이동 페이지 설정
          tdApTitleA.setAttribute("href", "approval-detail.html");
          //a태그 상세 스타일 적용
          tdApTitleA.setAttribute("style", "color:black");
          tdApTitle.setAttribute("style", "width:60%");
          //생성해놓은 제목td객체에 append
          tdApTitle.appendChild(tdApTitleA);

          //결재예정문서 날짜값 들어갈 td객체 생성
          var tdApDate = document.createElement("td");
          //값 대입
          tdApDate.innerHTML = mainApDate[i];

          //tr 객체에 순서대로 append
          tr.appendChild(tdApId);
          tr.appendChild(tdApTitle);
          tr.appendChild(tdApDate);

          //결재예정문서 tbody객체에 해당 tr append
          mainApTBodyObj.appendChild(tr);
        }

        //최근게시글 관련 정보 및 tr/td 객체들을 생성해주는 역할의 함수
        function createMainBdElement(i) {
          //tr객체 생성
          var tr = document.createElement("tr");
          //결재예정문서 제목값이 들어갈 td객체 생성
          var tdBdTitle = document.createElement("td");
          //제목 클릭 시 해당 상세 페이지로 넘어가야하므로 a 객체 생성
          var tdBdTitleA = document.createElement("a");
          //값 대입
          tdBdTitleA.innerHTML = mainBdTitle[i];
          //a태그에 클래스 속성 부여함으로 스타일 적용
          tdBdTitleA.setAttribute("class", "sidebar-link-js");
          //a태그에 id 속성 부여
          tdBdTitleA.setAttribute("id", mainBdId[i]);
          //a태그 이동 페이지 설정
          tdBdTitleA.setAttribute("href", "board-detail.html");
          //a태그 상세 스타일 적용
          tdBdTitleA.setAttribute("style", "color:black");
          tdBdTitle.setAttribute("style", "width:60%");
          //생성해놓은 제목td객체에 append
          tdBdTitle.appendChild(tdBdTitleA);

          //결재예정문서 날짜값 들어갈 td객체 생성
          var tdBdDate = document.createElement("td");
          //값 대입
          tdBdDate.innerHTML = mainBdDate[i];

          //tr 객체에 순서대로 append
          tr.appendChild(tdBdTitle);
          tr.appendChild(tdBdDate);

          //결재예정문서 tbody객체에 해당 tr append
          mainBdTBodyObj.appendChild(tr);
        }

        //로그인한 사원의 휴가 정보를 채우는 역할의 함수
        function insertMainLeaveElement() {
          //부여된 휴가일 값 대입
          mainGrantDaysObj.innerHTML = mainGrantLeave + "일";
          //사용한 휴가일 값 대입
          mainUseDaysObj.innerHTML = mainGrantLeave - mainRemainLeave + "일";
          //남은 휴가일 값 대입
          mainRemainDaysObj.innerHTML = mainRemainLeave + "일";
        }

        //오늘의일정 관련 정보 및 tr/td 객체들을 생성해주는 역할의 함수
        function createMainSkdElement(i) {
          //tr객체 생성
          var tr = document.createElement("tr");
          //오늘의일정 시간값이 들어갈 td객체 생성
          var tdSkdDate = document.createElement("td");
          //오늘의일정 시간값을 가운데로 맞춰주는 center 태그 생성
          var tdSkdDateCenter = document.createElement("center");
          //값 대입
          tdSkdDateCenter.innerHTML = mainSkdDate[i];
          //생성해놓은 center 태그 시간값td태그에 append
          tdSkdDate.appendChild(tdSkdDateCenter);

          //오늘의일정 제목값이 들어갈 td객체 생성
          var tdSkdTitle = document.createElement("td");
          //값 대입
          tdSkdTitle.innerHTML = mainSkdTitle[i];
          //스타일 적용
          tdSkdTitle.setAttribute("style", "width:80%");

          //tr 객체에 순서대로 append
          tr.appendChild(tdSkdDate);
          tr.appendChild(tdSkdTitle);

          //결재예정문서 tbody객체에 해당 tr append
          mainSkdTBodyObj.appendChild(tr);
        }

        //로그아웃 버튼 객체
        var logoutBtnObj = document.querySelector("a.logoutBtn");

        //ajax요청 시 사용할 backurl 선언
        //프로필 정보
        var backurlProfile = "/back/showmainpageprofile";
        //결재예정문서
        var backurlAp = "/back/showmainpageap";
        //최근게시글
        var backurlBd = "/back/showmainpagebd";
        //휴가정보
        var backurlLeave = "/back/showmainpageleave";
        //오늘의일정
        var backurlSkd = "/back/showmainpageskd";
        //로그아웃
        var backurlLogout = "/back/logout";

        //프로필 정보 get
        $.ajax({
          url: backurlProfile,
          method: "get",
          success: function (responseData) {
            mainLoginId = responseData.employee_id;
            mainLoginName = responseData.name;
            //함수 호출
            insertProfileInfo();
          },
        });

        //결재예정문서 정보 get
        $.ajax({
          url: backurlAp,
          method: "get",
          success: function (responseData) {
            $(responseData).each(function (i, e) {
              mainApId[i] = e.document_no;
              mainApTitle[i] = e.document_title;
              mainApDate[i] = e.draft_date;
            });
            //함수 호출
            for (var i = 0; i < mainApId.length; i++) {
              createMainApElement(i);
            }
            //결재예정문서 제목 객체
            $titleObj = $(".card-body tbody.mainApTbody tr td:nth-child(2) a");

            //결재예정문서 제목 객체 클릭 시 이벤트 발생
            $titleObj.click(function (e) {
              e.preventDefault();
              //클릭시 a링크에 담겨있는 문서값 저장
              localStorage.setItem("apDocumentNum", e.target.id);
              var href = $(this).attr("href");

              switch (href) {
                case "approval-detail.html":
                  //컨텐트에 로드
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

        //최근게시글 정보 get
        $.ajax({
          url: backurlBd,
          method: "get",
          success: function (responseData) {
            $(responseData).each(function (i, e) {
              mainBdId[i] = e.bd_no;
              mainBdTitle[i] = e.bd_title;
              mainBdWriter[i] = e.writer.name;
              mainBdDate[i] = e.bd_date;
            });

            //함수 호출
            for (var i = 0; i < mainBdId.length; i++) {
              createMainBdElement(i);
            }

            //최근게시글 제목 객체
            $mainBdTitleObj = $(
              'body > div > div > main > div > div:nth-child(1) > div.col-12.col-md-6.d-flex.order-2.order-xxl-3 > div > div.card-body.d-flex > div > table > tbody > tr > td >a[href="board-detail.html"]'
            );

            //최근게시글 제목 객체 클릭 시 이벤트 발생
            $mainBdTitleObj.click(function (e) {
              e.preventDefault();
              //클릭시 a링크에 담겨있는 문서값 저장
              localStorage.setItem("bdNumber", e.target.id);
              var href = $(this).attr("href");

              switch (href) {
                case "board-detail.html":
                  //컨텐트에 로드
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

        //휴가정보 get
        $.ajax({
          url: backurlLeave,
          method: "get",
          success: function (responseData) {
            mainGrantLeave = responseData.grant_days;
            mainRemainLeave = responseData.remain_days;
            //함수 호출
            insertMainLeaveElement();
          },
        });

        //오늘의일정 정보 get
        $.ajax({
          url: backurlSkd,
          method: "get",
          success: function (responseData) {
            $(responseData).each(function (i, e) {
              mainSkdId[i] = e.skd_no;
              mainSkdTitle[i] = e.skd_title;
              mainSkdDate[i] = e.skd_date;
            });
            //함수 호출
            for (var i = 0; i < mainSkdId.length; i++) {
              createMainSkdElement(i);
            }
          },
        });

        //로그아웃 관련 클릭 이벤트 핸들러
        function logoutBtnClickHandler() {
          $.ajax({
            url: backurlLogout,
            method: "get",
            success: function (responseData) {
              //로그인 페이지로 이동
              location.href = "http://localhost:8888/front";
            },
          });
        }

        //로그아웃 버튼 클릭 이벤트 등록
        logoutBtnObj.addEventListener("click", logoutBtnClickHandler);

        //sidebar menu Obj 찾기
        var $menuObj = $(
          "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li > a"
        );
        //전자결재 메뉴 객체 찾기
        var $apMenuObj = $("ul#docMenuFirst a");
        //전자결재 문서함 메뉴 객체 찾기
        var $apDocMenuObj = $("ul#docList a");
        //일정공유 메뉴 객체 찾기
        var $scheduleBtnObj = $(
          "div.wrapper>div.main>main.content div.row div.scheduleBtn"
        );

        //메뉴 객체 클릭 이벤트 발생 시
        $menuObj.click(function (e) {
          //sidebar-item 활성화 모두 풀기(없애기)
          $menuObj.closest("li").attr("class", "sidebar-item");

          //클릭된현재객체의 href속성값 얻기 : .attr('href');
          var href = $(this).attr("href");

          switch (href) {
            case "board.html":
            case "board-detail.html":
            case "schedule.html":
            case "employee.html":
            case "mypage.html":
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

        //작성하기 메뉴 클릭 이벤트
        $apMenuObj.click(function (e) {
          //sidebar-item 활성화 모두 풀기
          $apMenuObj.closest("li").attr("class", "sidebar-item");
          //클릭된현재객체의 href속성값 얻기 : .attr('href');
          var href = $(this).attr("href");

          switch (href) {
            case "post.html":
            case "post-detail-spending.html":
            case "post-detail-circular.html":
            case "post-detail-business.html":
            case "post-detail-account.html":
            case "post-detail-leave.html":
            case "post-detail-contact.html":
              //클릭한 객체의 sidebar-item만 활성화 시키기
              if (href == "post.html") {
                $(this).closest("li").attr("class", "sidebar-item active");
                $content.load(href, function (responseTxt, statusTxt, xhr) {
                  if (statusTxt == "error")
                    alert("Error: " + xhr.status + ": " + xhr.statusText);
                });
              } else {
                //작성하기에 select 옵션 '선택' 아닐 때 summernote미리 등록
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
              }
              break;
          }
          return false;
        });

        //전자결재 문서함 메뉴 클릭 이벤트
        $apDocMenuObj.click(function (e) {
          $apDocMenuObj.closest("li").attr("class", "sidebar-item");
          //클릭된현재객체의 href속성값 얻기 : .attr('href');
          var href = $(this).attr("href");

          switch (href) {
            case "approval-board.html":
            case "approval-board-wait.html":
            case "approval-board-ok.html":
            case "approval-board-no.html":
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

        //오늘의 일정 우측 +버튼 클릭 이벤트
        $scheduleBtnObj.click(function () {
          var href = $(this).find("a").attr("href");
          switch (href) {
            case "schedule.html":
              $content.load(href, function (responseTxt, statusTxt, xhr) {
                if (statusTxt == "error")
                  alert("Error: " + xhr.status + ": " + xhr.statusText);
              });
              break;
          }
          return false;
        });
      } else {
        alert("접근하기 위해 로그인이 필요합니다.");
        location.href = "http://localhost:8888/front";
      }
    },
    //로그인 x 면 로그인 페이지로 이동
    error: function (request, status, error) {
      alert("접근하기 위해 로그인이 필요합니다.");
      location.href = "http://localhost:8888/front";
    },
  });
});
