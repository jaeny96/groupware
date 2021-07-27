$(function () {
  //테이블 객체
  var DocumentTableObj = document.getElementById("apDocumentTable");
  //table바디 객체
  var tBodyObject = null;
  //table에 적용시킬 변수들 : list 형식
  var apMyStatus = new Array();
  var apBdNo = new Array();
  var apBdTitle = new Array();
  var apBdEmp = new Array();
  var apBdEmpName = new Array();
  var apBdDate = new Array();
  var apBdStatus = new Array();
  var apBdCheck = new Array();
  var tBodyObject = document.getElementById("apDocumentTbody");
  var $content = $("main.content");
  //tbody객체 만들어주는 함수
  function createTbodyElement() {
    tBodyObject = document.createElement("tbody");
    tBodyObject.setAttribute("id", "apDocumentTbody");
    DocumentTableObj.appendChild(tBodyObject);
  }

  function removeElement(target) {
    if (target != null) {
      target.remove();
    }
  }
  //기존 객체 및 배열 지워주기
  function emptyBdElement(target) {
    removeElement(target);
    apMyStatus = [];
    apBdNo = [];
    apBdTitle = [];
    apBdEmp = [];
    apBdEmpName = [];
    apBdDate = [];
    apBdStatus = [];
    apBdCheck = [];
  }

  var apBtnGroupObj = document.querySelector("div.btn-group-sm");
  console.log(apBtnGroupObj);
  var apCategoryObj = apBtnGroupObj.querySelector("div.searchDropdown");
  console.log(apCategoryObj);
  var apSearchCategoryBtnObj = document.querySelector("button.searchBtn");
  console.log(apSearchCategoryBtnObj);
  var apSearchFormObj = document.querySelector("form.searchForm");
  console.log(apSearchFormObj);
  var apSearchCategory = null;

  var apInputGroupObj = document.querySelector("div.input-group");
  console.log(apInputGroupObj);
  var apSearchObj = apInputGroupObj.querySelector("input[type=text]");
  console.log(apSearchObj);

  function categoryHandler(e) {
    if (e.target.id == "apCategoryTitle") {
      apSearchCategoryBtnObj.innerHTML = "제목";
      apSearchObj.setAttribute("placeholder", "제목으로 검색하기");
      apSearchCategory = "title";
    } else if (e.target.id == "apCategoryWriter") {
      apSearchCategoryBtnObj.innerHTML = "내용";
      apSearchObj.setAttribute("placeholder", "내용으로 검색하기");
      apSearchCategory = "content";
    }
  }

  function apSearchFormSubmitHandler(e) {
    if (apSearchCategory == null) {
      alert("카테고리가 지정되지 않았습니다");
    } else {
      $.ajax({
        url: "/back/usersearchdocs",
        method: "get",
        data: {
          searchCategory: apSearchCategory,
          searchWord: apSearchObj.value,
          status: "반려",
        },
        success: function (responseData) {
          emptyBdElement(tBodyObject);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            apMyStatus[i] = e.state;
            apBdNo[i] = e.document_no;
            apBdTitle[i] = e.document_title;
            apBdEmp[i] = e.employee.employee_id;
            apBdEmpName[i] = e.employee.name;
            apBdDate[i] = e.draft_date;
            apBdStatus[i] = e.document_type.document_type;
            apBdCheck[i] = e.approval.ap_type.apStatus_type;
          });

          for (var i = 0; i < apBdTitle.length; i++) {
            createApBdElement(i);
          }
          $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");

          console.log($titleObj);

          $titleObj.click(function (e) {
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
    }
    e.preventDefault();
  }

  apCategoryObj.addEventListener("click", categoryHandler);
  apSearchFormObj.addEventListener("submit", apSearchFormSubmitHandler);

  function createApBdElement(i) {
    var tBodyObject = document.getElementById("apDocumentTbody");
    var apDocsNo = document.getElementById("apDocsNo");
    var tr = document.createElement("tr");
    var th = document.createElement("th");
    th.setAttribute("scope", "row");
    if (apMyStatus[i] != null) {
      th.innerHTML = apMyStatus[i];
      apDocsNo.innerHTML = "서류상태";
    } else {
      th.innerHTML = i + 1;
      apDocsNo.innerHTML = "확인";
    }
    var tdTitle = document.createElement("td");

    var a = document.createElement("a");
    a.setAttribute("class", "sidebar-link-js");
    a.setAttribute("style", "color: black");
    a.setAttribute("id", apBdNo[i]);
    a.setAttribute("href", "approval-detail.html");
    a.innerText = apBdTitle[i];

    var tdDocsNo = document.createElement("td");
    tdDocsNo.innerHTML = apBdNo[i];
    var tdEmployee = document.createElement("td");
    tdEmployee.innerHTML = apBdEmp[i];
    var tdWriter = document.createElement("td");
    tdWriter.innerHTML = apBdEmpName[i];
    var tdDate = document.createElement("td");
    tdDate.innerHTML = apBdDate[i];
    var tdStatus = document.createElement("td");
    tdStatus.innerHTML = apBdStatus[i];
    var tdCheck = document.createElement("td");
    if (apBdCheck[i] == "대기" || apBdCheck[i] == null) {
      console.log(tdCheck[i]);
      tdCheck.innerHTML = "미확인";
    } else if (apBdCheck[i] != "대기") {
      tdCheck.innerHTML = "확인";
    }

    tdTitle.appendChild(a);
    tr.appendChild(th);
    tr.appendChild(tdDocsNo);
    tr.appendChild(tdTitle);
    tr.appendChild(tdEmployee);
    tr.appendChild(tdWriter);
    tr.appendChild(tdDate);
    tr.appendChild(tdStatus);
    tr.appendChild(tdCheck);
    tBodyObject.appendChild(tr);
  }

  //tbody내용 생성
  function createApBdElement(i) {
    var tr = document.createElement("tr");
    var th = document.createElement("th");
    th.setAttribute("scope", "row");
    th.innerHTML = i + 1;
    var tdTitle = document.createElement("td");

    var a = document.createElement("a");
    a.setAttribute("class", "sidebar-link-js");
    a.setAttribute("style", "color: black");
    a.setAttribute("id", apBdNo[i]);
    a.setAttribute("href", "approval-detail.html");
    a.innerText = apBdTitle[i];

    var tdDocsNo = document.createElement("td");
    tdDocsNo.innerHTML = apBdNo[i];
    var tdEmployee = document.createElement("td");
    tdEmployee.innerHTML = apBdEmp[i];
    var tdWriter = document.createElement("td");
    tdWriter.innerHTML = apBdEmpName[i];
    var tdDate = document.createElement("td");
    tdDate.innerHTML = apBdDate[i];
    var tdStatus = document.createElement("td");
    tdStatus.innerHTML = apBdStatus[i];
    var tdCheck = document.createElement("td");
    if (apBdCheck[i] == "대기" || apBdCheck[i] == null) {
      console.log(tdCheck[i]);
      tdCheck.innerHTML = "미확인";
    } else if (apBdCheck[i] != "대기") {
      tdCheck.innerHTML = "확인";
    }

    tdTitle.appendChild(a);
    tr.appendChild(th);
    tr.appendChild(tdDocsNo);
    tr.appendChild(tdTitle);
    tr.appendChild(tdEmployee);
    tr.appendChild(tdWriter);
    tr.appendChild(tdDate);
    tr.appendChild(tdStatus);
    tr.appendChild(tdCheck);
    tBodyObject.appendChild(tr);
  }
  //확인 미확인 하는 변수
  var nowStatus = "";
  var $categoryObj = $("div.apCategoryDropdown");

  //확인 미확인값 가지고 오는 함수
  var apChangeStatusBtnObj = document.getElementById("apDropDownStatusGroup");
  function statusHandler(e) {
    //  console.log(e.target);
    //   console.log(e.target.id);
    if (e.target.id == "apDocumentStatusOk") {
      apChangeStatusBtnObj.innerHTML = "확인";
      nowStatus = "확인";
      //  console.log(nowStatus);
      bdSearchFromSubmitHandler(e);
    }
    if (e.target.id == "apDocumentStatusNo") {
      apChangeStatusBtnObj.innerHTML = "미확인";
      nowStatus = "미확인";
      bdSearchFromSubmitHandler(e);
      //    console.log(nowStatus);
    }
    if (e.target.id == "apDocumentStatusAll") {
      apChangeStatusBtnObj.innerHTML = "모든문서";
      nowStatus = "모든문서";
      bdSearchFromSubmitHandler(e);

      //   console.log(nowStatus);
    }
  }
  apChangeStatusBtnObj.addEventListener("click", function () {
    $categoryObj.toggle();
  });
  $categoryObj.click(statusHandler);

  //확인 미확인 서버핸들러
  function bdSearchFromSubmitHandler(e) {
    if (e.target.id == "apDocumentStatusAll") {
      $.ajax({
        url: "/back/showapdocsstatus",
        method: "get",
        data: {
          status: "반려",
        },
        success: function (responseData) {
          emptyBdElement(tBodyObject);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            //   console.log(i + "," + e);
            apBdNo[i] = e.document_no;
            apBdTitle[i] = e.document_title;
            apBdEmp[i] = e.employee.employee_id;
            apBdEmpName[i] = e.employee.name;
            apBdDate[i] = e.draft_date;
            apBdStatus[i] = e.document_status.document_type;
            apBdCheck[i] = e.approval.ap_type.apStatus_type;
            // console.log(apBdCheck[i]);
          });
          for (var i = 0; i < apBdTitle.length; i++) {
            createApBdElement(i);
          }
          $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");
          //  console.log($titleObj);
          $titleObj.click(function (e) {
            // e.preventDefault();
            localStorage.setItem("apDocumentNum", e.target.id);
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
    } else {
      $.ajax({
        url: "/back/selectnopick",
        method: "get",
        data: {
          check: nowStatus,
        },
        success: function (responseData) {
          emptyBdElement(tBodyObject);
          createTbodyElement();
          $(responseData).each(function (i, e) {
            apBdNo[i] = e.document_no;
            apBdTitle[i] = e.document_title;
            apBdEmp[i] = e.employee.employee_id;
            apBdEmpName[i] = e.employee.name;
            apBdDate[i] = e.draft_date;
            apBdStatus[i] = e.document_type.document_type;
            apBdCheck[i] = e.approval.ap_type.apStatus_type;
          });

          for (var i = 0; i < apBdTitle.length; i++) {
            createApBdElement(i);
          }
          $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");
          // console.log($titleObj);
          $titleObj.click(function (e) {
            localStorage.setItem("apDocumentNum", e.target.id);
            var href = $(this).attr("href");
            //console.log(href);
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
    }
    e.preventDefault();
  }
  //첫 화면 조회하는 ajax
  $.ajax({
    url: "/back/showapdocsstatus",
    method: "get",
    data: {
      status: "반려",
    },
    success: function (responseData) {
      emptyBdElement(tBodyObject);
      createTbodyElement();
      $(responseData).each(function (i, e) {
        console.log(i + "," + e);
        apBdNo[i] = e.document_no;
        apBdTitle[i] = e.document_title;
        apBdEmp[i] = e.employee.employee_id;
        apBdEmpName[i] = e.employee.name;
        apBdDate[i] = e.draft_date;
        apBdStatus[i] = e.document_status.document_type;
        apBdCheck[i] = e.approval.ap_type.apStatus_type;
      });

      for (var i = 0; i < apBdTitle.length; i++) {
        createApBdElement(i);
      }
      $titleObj = $("#apDocumentTbody tr td:nth-child(3) a");

      console.log($titleObj);

      $titleObj.click(function (e) {
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
});
