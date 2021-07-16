//main-form 에서 적용할 내용
$(function () {
  console.log("load.js document ready");
  document.addEventListener("DOMContentLoaded", function () {
    console.log("load.js DOMContentLoaded 로드!");
  });
  var backurlCheckLogined = "/back/checkedlogined";
  var $content = $("div.wrapper>div.main>main.content");
  $.ajax({
    url: backurlCheckLogined,
    method: "get",
    success: function (responseData) {
      console.log(responseData.status);
      if (responseData.status == 1) {
        localStorage.setItem("bdCurrPage", 1);
        localStorage.setItem("bdCurrPageGroup", 1);

        var $dropDownProfileAObj = $("a.dropdownProfile");
        var $dropDownProfileItemObj = $("div.profileDropdown");
        $dropDownProfileItemObj.hide();
        $dropDownProfileAObj.click(function (e) {
          $dropDownProfileItemObj.slideToggle(300);
        });

        var $apMenuFirstDropDownAObj = $("a.apMenu");
        var $apMenuFirstDropDownItemObj = $("ul#docMenuFirst");
        $apMenuFirstDropDownAObj.click(function (e) {
          $apMenuFirstDropDownItemObj.slideToggle(300);
        });

        var $docListDropDownMenuAObj = $("a.docListDropDownMenu");
        var $docListDropDownMenuItemObj = $("ul#docList");
        $docListDropDownMenuAObj.click(function (e) {
          $docListDropDownMenuItemObj.slideToggle(300);
        });

        var mainObj = document.querySelector("div.wrapper>div.main");
        var mainProfileObj = mainObj.querySelector("div.profileDropdown");
        var mainLoginIdObj = mainProfileObj.querySelector("span.loginId");
        var mainLoginNameObj = mainProfileObj.querySelector("span.loginName");

        var mainContentObj = mainObj.querySelector("main.content");
        var mainBdTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainBdTbody"
        );
        var mainApTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainApTbody"
        );
        var mainSkdTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainSkdTbody"
        );
        var mainLeaveTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainLeaveTbody"
        );
        var mainGrantDaysObj =
          mainLeaveTBodyObj.querySelector("td.mainGrantDays");
        var mainUseDaysObj = mainLeaveTBodyObj.querySelector("td.mainUseDays");
        var mainRemainDaysObj =
          mainLeaveTBodyObj.querySelector("td.mainRemainDays");

        var mainLoginId;
        var mainLoginName;

        var mainBdId = new Array();
        var mainBdTitle = new Array();
        var mainBdWriter = new Array();
        var mainBdDate = new Array();

        var mainApId = new Array();
        var mainApTitle = new Array();
        var mainApDate = new Array();

        var mainGrantLeave;
        var mainRemainLeave;

        var mainSkdId = new Array();
        var mainSkdDate = new Array();
        var mainSkdTitle = new Array();

        function insertProfileInfo() {
          mainLoginIdObj.innerHTML = mainLoginId;
          mainLoginNameObj.innerHTML = mainLoginName;
        }

        function createMainApElement(i) {
          var tr = document.createElement("tr");
          var tdApId = document.createElement("td");
          tdApId.innerHTML = mainApId[i];
          var tdApTitle = document.createElement("td");
          var tdApTitleA = document.createElement("a");
          tdApTitleA.innerHTML = mainApTitle[i];
          tdApTitleA.setAttribute("class", "sidebar-link-js");
          tdApTitleA.setAttribute("id", mainApId[i]);
          tdApTitleA.setAttribute("href", "approval-detail.html");
          tdApTitleA.setAttribute("style", "color:black");
          tdApTitle.setAttribute("style", "width:60%");
          tdApTitle.appendChild(tdApTitleA);
          var tdApDate = document.createElement("td");
          tdApDate.innerHTML = mainApDate[i];

          tr.appendChild(tdApId);
          tr.appendChild(tdApTitle);
          tr.appendChild(tdApDate);

          mainApTBodyObj.appendChild(tr);
        }

        function createMainBdElement(i) {
          var tr = document.createElement("tr");
          var tdBdTitle = document.createElement("td");
          var tdBdTitleA = document.createElement("a");
          tdBdTitleA.innerHTML = mainBdTitle[i];
          tdBdTitleA.setAttribute("class", "sidebar-link-js");
          tdBdTitleA.setAttribute("id", mainBdId[i]);
          //		tdBdTitleA.setAttribute("href", "board-detail.html");
          tdBdTitleA.setAttribute("style", "color:black");
          tdBdTitle.setAttribute("style", "width:60%");
          tdBdTitle.appendChild(tdBdTitleA);

          /*		var tdBdWriter = document.createElement("td");
				tdBdWriter.innerHTML = mainBdWriter[i];*/
          var tdBdDate = document.createElement("td");
          tdBdDate.innerHTML = mainBdDate[i];

          tr.appendChild(tdBdTitle);
          /*		tr.appendChild(tdBdWriter);*/
          tr.appendChild(tdBdDate);

          mainBdTBodyObj.appendChild(tr);
        }

        function insertMainLeaveElement() {
          mainGrantDaysObj.innerHTML = mainGrantLeave + "일";
          mainUseDaysObj.innerHTML = mainGrantLeave - mainRemainLeave + "일";
          mainRemainDaysObj.innerHTML = mainRemainLeave + "일";
        }

        function createMainSkdElement(i) {
          var tr = document.createElement("tr");
          var tdSkdDate = document.createElement("td");
          var tdSkdDateCenter = document.createElement("center");
          tdSkdDateCenter.innerHTML = mainSkdDate[i];
          tdSkdDate.appendChild(tdSkdDateCenter);
          var tdSkdTitle = document.createElement("td");
          tdSkdTitle.innerHTML = mainSkdTitle[i];
          tdSkdTitle.setAttribute("style", "width:80%");
          // var tdSkdTitleA = document.createElement("a");
          // tdSkdTitleA.innerHTML = mainSkdTitle[i];
          // tdSkdTitleA.setAttribute("class", "sidebar-link-js");
          // tdSkdTitleA.setAttribute("id", mainSkdId[i]);
          // tdSkdTitleA.setAttribute("href", "schedule-detail.html");
          // tdSkdTitleA.setAttribute("style", "color:black");
          // tdSkdTitle.setAttribute("style", "width:60%");
          // tdSkdTitle.appendChild(tdSkdTitleA);

          tr.appendChild(tdSkdDate);
          tr.appendChild(tdSkdTitle);

          mainSkdTBodyObj.appendChild(tr);
        }

        var logoutBtnObj = document.querySelector("a.logoutBtn");

        var backurlProfile = "/back/showmainpageprofile";
        var backurlAp = "/back/showmainpageap";
        var backurlBd = "/back/showmainpagebd";
        var backurlLeave = "/back/showmainpageleave";
        var backurlSkd = "/back/showmainpageskd";
        var backurlLogout = "/back/logout";

        $.ajax({
          url: backurlProfile,
          method: "get",
          success: function (responseData) {
            mainLoginId = responseData.employee_id;
            mainLoginName = responseData.name;
            insertProfileInfo();
          },
        });

        $.ajax({
          url: backurlAp,
          method: "get",
          success: function (responseData) {
            $(responseData).each(function (i, e) {
              mainApId[i] = e.document_no;
              mainApTitle[i] = e.document_title;
              mainApDate[i] = e.draft_date;
            });
            for (var i = 0; i < mainApId.length; i++) {
              createMainApElement(i);
            }
            $titleObj = $(".card-body tbody.mainApTbody tr td:nth-child(2) a");

            console.log($titleObj);

            $titleObj.click(function (e) {
              console.log($(this));
              e.preventDefault();
              localStorage.setItem("apDocumentNum", e.target.id); //클릭시 a링크에 담겨있는 문서값 저장
              var href = $(this).attr("href");

              console.log(href);
              switch (href) {
                case "approval-detail.html":
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

        function mainBdTitleClickHandler(e) {
          localStorage.setItem("bdNumber", e.target.id);
          $(
            'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="board-detail.html"]'
          ).trigger("click");
        }

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
            for (var i = 0; i < mainBdId.length; i++) {
              createMainBdElement(i);
            }
            mainBdTitleObj = mainBdTBodyObj.querySelectorAll(
              "tr td:nth-child(1) a"
            );
            for (var j = 0; j < mainBdTitleObj.length; j++) {
              mainBdTitleObj[j].addEventListener(
                "click",
                mainBdTitleClickHandler
              );
            }
          },
        });

        $.ajax({
          url: backurlLeave,
          method: "get",
          success: function (responseData) {
            mainGrantLeave = responseData.grant_days;
            mainRemainLeave = responseData.remain_days;
            insertMainLeaveElement();
          },
        });

        $.ajax({
          url: backurlSkd,
          method: "get",
          success: function (responseData) {
            $(responseData).each(function (i, e) {
              mainSkdId[i] = e.skd_no;
              mainSkdTitle[i] = e.skd_title;
              mainSkdDate[i] = e.skd_date;
            });
            for (var i = 0; i < mainSkdId.length; i++) {
              createMainSkdElement(i);
            }
          },
        });

        function logoutBtnClickHandler() {
          $.ajax({
            url: backurlLogout,
            method: "get",
            success: function (responseData) {
              location.href = "http://localhost:8888/front";
              console.log(responseData.status);
            },
          });
        }

        logoutBtnObj.addEventListener("click", logoutBtnClickHandler);

        //sidebar menu Obj 찾기
        var $menuObj = $(
          "div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a"
        );
        var $apMenuObj = $("ul#docMenuFirst a");
        // var apMenuObj = document.querySelector("ul#docMenuFirst a");
        // console.log(apMenuObj);
        var $apDocMenuObj = $("ul#docList a");
        // var apDocMenuObj = document.querySelector("ul#docList a");
        // console.log(apDocMenuObj);
        var $scheduleBtnObj = $(
          "div.wrapper>div.main>main.content div.row div.scheduleBtn"
        );
        $menuObj.click(function (e) {
          //sidebar-item 활성화 모두 풀기
          $menuObj.closest("li").attr("class", "sidebar-item");
          // console.log(e.target);
          //클릭된현재객체의 href속성값 얻기 : .attr('href');
          var href = $(this).attr("href");

          switch (href) {
            case "board.html":
            case "board-detail.html":
            case "schedule.html":
              $(this).closest("li").attr("class", "sidebar-item active");
              $content.load(href, function (responseTxt, statusTxt, xhr) {
                if (statusTxt == "error")
                  alert("Error: " + xhr.status + ": " + xhr.statusText);
              });
              // $.ajax({
              //   url: href,
              //   method: "get",
              //   success: function (responseData) {
              //     $content.html(responseData);
              //   },
              //   error: function (xhr) {
              //     alert(xhr.status);
              //   },
              // });
              break;
            // case "post.html":
            // case "post-detail-spending.html":
            // case "post-detail-circular.html":
            // case "post-detail-business.html":
            // case "post-detail-account.html":
            // case "post-detail-leave.html":
            // case "post-detail-contact.html":
            case "employee.html":
            case "mypage.html":
              //클릭한 객체의 sidebar-item만 활성화 시키기
              // if (
              //   href == "post-detail-spending.html" ||
              //   href == "post-detail-circular.html" ||
              //   href == "post-detail-business.html" ||
              //   href == "post-detail-account.html" ||
              //   href == "post-detail-leave.html" ||
              //   href == "post-detail-contact.html"
              // ) {
              //   $content.load(href, function (responseTxt, statusTxt, xhr) {
              //     $("#summernote").summernote({
              //       height: 600, // 에디터 높이
              //       minHeight: null, // 최소 높이
              //       maxHeight: null, // 최대 높이
              //       focus: true, // 에디터 로딩후 포커스를 맞출지 여부
              //       lang: "ko-KR", // 한글 설정
              //       placeholder: "최대 2048자까지 쓸 수 있습니다", //placeholder 설정
              //     });
              //     if (statusTxt == "error")
              //       alert("Error: " + xhr.status + ": " + xhr.statusText);
              //   });
              // } else {
              $(this).closest("li").attr("class", "sidebar-item active");
              $content.load(href, function (responseTxt, statusTxt, xhr) {
                if (statusTxt == "error")
                  alert("Error: " + xhr.status + ": " + xhr.statusText);
              });
              // }
              break;
          }
          return false;
        });

        $apMenuObj.click(function (e) {
          //sidebar-item 활성화 모두 풀기
          $apMenuObj.closest("li").attr("class", "sidebar-item");
          // console.log(e.target);
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

        $apDocMenuObj.click(function (e) {
          $apDocMenuObj.closest("li").attr("class", "sidebar-item");
          // console.log(e.target);
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

        $scheduleBtnObj.click(function () {
          var href = $(this).find("a").attr("href");
          // console.log(href);
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
      }
      // else {
      //   alert("접근하기 위해 로그인이 필요합니다.");
      //   location.href = "http://localhost:8888/front";
      // }
    },
    // error: function (request, status, error) {
    //   alert("접근하기 위해 로그인이 필요합니다.");
    //   location.href = "http://localhost:8888/front";
    // },
  });
});
