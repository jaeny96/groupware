$(function () {
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  var loginInfoNameObj = document.querySelector(
    "div.profileDropdown span.loginName"
  );
  var apBtn = document.querySelector("button#draftComplete");
  //결재문서 작성폼
  var apFormObj = document.querySelector("form#addApform");
  //결재문서 종류
  var apDocsTypeObj = document.querySelector(
    "select#documentTypeSelect>option"
  );
  //결재문서 제목
  var apDocsTitleObj = document.querySelector("input#inputtitle");
  //결재문서 내용
  var apDocsContentObj = document.querySelector("div.note-editable");
  //결재문서 작성 사원번호
  var apDocsEmpIdObj = localStorage.getItem("loginInfo");
  //0단계 결재자 이름칸
  var apZeroStepName = document.querySelector("td#apApprovalStepName0");
  //1단계 결재자 이름칸
  var apOneStepName = document.querySelector("td#apApprovalStepName1");
  //2단계 결재자 이름칸
  var apTwoStepName = document.querySelector("td#apApprovalStepName2");
  //3단계 결재자 이름칸
  var apThreeStepName = document.querySelector("td#apApprovalStepName3");
  //합의자 이름칸
  var apAgreementStepName = document.querySelector("td#apAgreementName");
  //참조자 이름칸
  var apReferenceStepName = document.querySelector("td#apReferenceName");

  console.log(apFormObj);
  console.log(apDocsTypeObj);
  console.log(apDocsTitleObj);
  console.log(apDocsContentObj);
  console.log(apDocsEmpIdObj);
  console.log(apZeroStepName);
  console.log(apOneStepName);
  console.log(apTwoStepName);
  console.log(apThreeStepName);
  console.log(apAgreementStepName);
  console.log(apReferenceStepName);

  var backurlAddDocs = "/back/addapprovaldocs";

  function addApFormSubmitHandler(e) {
    console.log(apDocsTypeObj.innerHTML);
    console.log(apDocsTitleObj.value);
    console.log(apDocsContentObj.value);
    console.log(apDocsEmpIdObj.innerText);
    $.ajax({
      url: backurlAddDocs,
      method: "post",
      data: {
        addApDocsType: apDocsTypeObj.innerHTML,
        addApDocsTitle: apDocsTitleObj.value,
        addApDocsContent: apDocsContentObj.value,
        addApWriterId: apDocsEmpIdObj.innerText,
      },
      //제목, 상세입력을 모두 입력해야 기안이 되도록
      success: function () {
        if (ApDocsTitleObj.value == "") {
          alert("제목을 입력해주세요");
        }
        if (ApDocsContentObj.value == "") {
          alert("내용을 입력해주세요");
        }
        if (ApDocsTitleObj.value != "" && ApDocsContentObj.value != "") {
          $('ul#docMenuFirst a[href="post.html"]').trigger("click");
        }
      },
      error: function (request, status, error) {
        alert(
          "code:" +
            request.status +
            "\n" +
            "message:" +
            request.responseText +
            "\n" +
            "error:" +
            error
        );
      },
    });
  }
  function addApFormClickHandler(e) {
    console.log(apDocsTypeObj.innerHTML);
    console.log(apDocsTitleObj.value);
    console.log(apDocsContentObj.innerText);
    console.log(loginInfoIdObj.innerText);
    $.ajax({
      url: backurlAddDocs,
      method: "post",
      data: {
        addApDocsNo: "지출 001",
        addApDocsType: apDocsTypeObj.innerHTML,
        addApDocsTitle: apDocsTitleObj.value,
        addApDocsContent: apDocsContentObj.innerText,
        addApWriterId: loginInfoIdObj.innerText,
      },
      //제목, 상세입력을 모두 입력해야 기안이 되도록
      success: function () {
        if (ApDocsTitleObj.value == "") {
          alert("제목을 입력해주세요");
        }
        if (ApDocsContentObj.value == "") {
          alert("내용을 입력해주세요");
        }
        if (ApDocsTitleObj.value != "" && ApDocsContentObj.value != "") {
          $('ul#docMenuFirst a[href="post.html"]').trigger("click");
        }
      },
      error: function (request, status, error) {
        alert(
          "code:" +
            request.status +
            "\n" +
            "message:" +
            request.responseText +
            "\n" +
            "error:" +
            error
        );
      },
    });
  }

  //   apFormObj.addEventListener("submit", addApFormSubmitHandler);
  apBtn.addEventListener("click", addApFormClickHandler);
});
