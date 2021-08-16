$(function () {
  //옆에 메뉴 객체
  var navObj = document.querySelector("li.navObj");
  //메뉴의 드롭다운 객체
  var ulDeptObj = navObj.querySelector("ul.sidebar-dropdown");

  // var $ulDeptObj = $(
  //   "body > div > div > main > div.container-fluid.p-0 > div > div.col-md-4.col-xl-3 > div > div > ul > li > a"
  // );
  // var $dropdownItem = $("#forms");
  // $ulDeptObj.click(function () {
  //   $dropdownItem.slideToggle(300);
  // });

  //내용 담는 객체
  var contentObj = document.querySelector("div.deptContent");
  //내용담는 객체의 헤더 객체
  var empHeaderObj = contentObj.querySelector("h5.empHeader");
  //내용담는 객체의 내용
  var empBodyObj = contentObj.querySelector("div.empBody");
  //내용 담는 객체의 각 내용들을 감싸는 div arr
  var empContentObj = new Array();
  //사원 상세 정보 모달 창
  var modalDetailObj = document.querySelector("div#modalDetail");
  //모달 창 내용
  //사원 이름 객체
  var modalNameObj = modalDetailObj.querySelector("h4");
  //사원 직급 객체
  var modalPositionObj = modalDetailObj.querySelector(
    "div.cardBody div:first-child>div"
  );
  //사원 사번 객체
  var modalEmployeeIdObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(2)>div"
  );
  //사원 부서 객체
  var modalDepartmentObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(3)>div"
  );
  //사원 직무 객체
  var modalJobObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(4)>div"
  );
  //사원 핸드폰번호 객체
  var modalPhoneObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(5)>div"
  );
  //사원 이메일 객체
  var modalEmailObj = modalDetailObj.querySelector(
    "div.cardBody div:last-child>div"
  );

  //검색 form 객체
  var formObj = document.querySelector("form.searchEmp");
  //검색 단어 input 객체
  var wordObj = formObj.querySelector("input.searchWord");

  //불러온 부서명 arr
  var deptArr = new Array();
  //불러온 부서별 사원 수 arr
  var deptCntArr = new Array();
  //불러온 부서번호 arr
  var deptIdArr = new Array();
  //불러온 부서별 사원 이름명 arr
  var empArr = new Array();
  //불러온 부서별 사원 직책명 arr
  var positionArr = new Array();
  //불러온 사원의 사번 arr
  var empIdArr = new Array();

  //사원 상세 사원명 변수
  var detailName;
  //사원 상세 직급 변수
  var detailPosition;
  //사원 상세 사번 변수
  var detailEmployeeId;
  //사원 상세 부서 변수
  var detailDepartment;
  //사원 상세 직무 변수
  var detailJob;
  //사원 상세 핸드폰 번호 변수
  var detailPhone;
  //사원 상세 이메일 변수
  var detailEmail;

  //employee에서 사용할 backurl
  //부서불러오기
  var backurlDept = "/back/showdept";
  //전체 사원 불러오기
  var backurlAllEmp = "/back/showallemp";
  //부서별 사원 불러오기
  var backurlDeptEmp = "/back/showdeptemp";
  //사원 상세 불러오기
  var backurlEmpDetail = "/back/showempdetail";
  //사원명으로 검색한 결과 불러오기
  var backurlSearchEmp = "/back/searchemp";

  //사원 클릭 시 클릭 이벤트 핸들러
  function empClickHandler(e) {
    console.log(e.target.id);
    var empInfoArr = e.target.id.split("/");
    $.ajax({
      url: backurlEmpDetail,
      method: "get",
      data: {
        empId: empInfoArr[0],
        empName: empInfoArr[1],
      },
      success: function (responseData) {
        console.log(responseData.name);
        detailName = responseData.name;
        detailPosition = responseData.position.position_title;
        detailEmployeeId = responseData.employee_id;
        detailDepartment = responseData.department.department_title;
        detailJob = responseData.job.job_title;
        detailPhone = responseData.phone_number;
        detailEmail = responseData.email;
        openTargetModal("." + empInfoArr[0] + "openDetail", "modalDetail");
      },
    });
    //사원 상세 정보 모달 창 열기
  }

  //타겟 모달 열기
  function openTargetModal(modalId, modalObj) {
    console.log("opend", detailName);
    //모달 이름 객체에 사원명 대입
    modalNameObj.innerHTML = detailName;
    //모달 직급 객체에 사원의 직급 대입
    modalPositionObj.innerHTML = detailPosition;
    //모달 사번 객체에 사원의 사번 대입
    modalEmployeeIdObj.innerHTML = detailEmployeeId;
    //모달 부서 객체에 사원의 부서 대입
    modalDepartmentObj.innerHTML = detailDepartment;
    //모달 직무 객체에 사원의 직무 대입
    modalJobObj.innerHTML = detailJob;
    //모달 핸드폰번호 객체에 사원의 핸드폰 번호 대입
    modalPhoneObj.innerHTML = detailPhone;
    //모달 이메일 객체에 사원의 이메일 대입
    modalEmailObj.innerHTML = detailEmail;

    //모달창 열기 버튼
    var openBtn = document.querySelector(modalId);
    //모달 객체
    var modal = document.querySelector("#" + modalObj + ">.modal");
    //모달의 뒷 배경
    var overlay = modal.querySelector(".modal_overlay");
    //모달 닫기 버튼
    var xBoxBtn = modal.querySelector("button.xBox");
    modal.classList.remove("hidden");
    var closeModal = () => {
      modal.classList.add("hidden");
    };
    if (openBtn != null) {
      //input에는 없는 overlay 클릭 -> close 기능
      overlay.addEventListener("click", closeModal);
      //모달창 닫기 버튼
      xBoxBtn.addEventListener("click", closeModal);
    }
  }

  //해당 객체 제거
  function removeEmpElement(target) {
    target.remove();
  }

  //cardBody 객체 생성
  function createCardBody() {
    empBodyObj = document.createElement("div");
    empBodyObj.setAttribute("class", "card-body h-100 ");
    contentObj.appendChild(empBodyObj);
  }

  //부서별 사원 내용 생성
  function createEmpElement(i) {
    //사용자 아이콘 div 객체 생성
    var divIcon = document.createElement("div");
    //div객체에 class속성 부여
    divIcon.setAttribute("class", "fa fa-user fa-3x mr-3");

    //사원 정보 내용 div 객체 생성
    var divContent = document.createElement("div");
    //div 객체에 class 속성 부여
    divContent.setAttribute(
      "class",
      "flex-grow-1 mr-2 " + empIdArr[i] + "openDetail"
    );
    //div에 id 속성 부여
    divContent.setAttribute("id", empIdArr[i] + "/" + empArr[i]);

    //직급 담겨있는 samll 객체 생성
    var small = document.createElement("small");
    //small객체 스타일 적용
    small.setAttribute("class", "text-muted");
    //값 대입
    small.innerHTML = positionArr[i];
    //small 객체에 id 속성 부여
    small.setAttribute("id", empIdArr[i] + "/" + empArr[i]);

    //사원정보 내용 div 객체에 값 대입
    divContent.innerHTML = empArr[i] + "<br/>";

    //차례대로 각 부모에 append
    divContent.appendChild(small);
    //사원 정보 div 클릭 이벤트 등록
    divContent.addEventListener("click", empClickHandler);

    empContentObj.appendChild(divIcon);
    empContentObj.appendChild(divContent);
  }

  //내용 감싸주는 div 만들기
  function createEmpElementBig() {
    empContentObj = document.createElement("div");
    empContentObj.setAttribute("class", "d-flex align-items-start mb-4");
    empBodyObj.appendChild(empContentObj);
  }

  //생성한 배열 초기화
  function emptyElement() {
    removeEmpElement(empBodyObj);
    empArr = [];
    positionArr = [];
    empIdArr = [];
  }

  //dept에 맞는 사원 조회
  function selectEmpElement(dept) {
    //배열 초기화
    emptyElement();
    $.ajax({
      url: backurlDeptEmp,
      method: "get",
      data: {
        deptId: dept,
      },
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          empArr[i] = e.name;
          positionArr[i] = e.position.position_title;
          empIdArr[i] = e.employee_id;
        });
        //cardBody 생성
        createCardBody();

        //한줄에는 3개씩
        for (var i = 0; i < empArr.length; i++) {
          if (i % 3 == 0) {
            //3개의 emp카드 감싸는 객체 생성
            createEmpElementBig();
          }
          //emp요소 생성 함수 호출
          createEmpElement(i);
        }
      },
    });
  }

  //부서명 클릭 handler
  function deptClickHandler(e) {
    empHeaderObj.innerText = e.target.innerHTML;
    selectEmpElement(e.target.id);
  }

  //부서 내비게이션 생성 함수
  function createDeptElement(i) {
    //li 객체 생성
    var li = document.createElement("li");
    //li객체에 스타일 적용
    li.setAttribute("class", "sidebar-item");

    //해당 내비게이션 클릭 시 부서에 해당하는 사원들 보여줘야 하므로 a 객체 생성
    var a = document.createElement("a");
    //a객체에 스타일 적용
    a.setAttribute("class", "sidebar-link-js");
    //a객체에 아이디 속성 dept아이디로 부여
    a.setAttribute("id", deptIdArr[i]);
    //값 대입
    a.innerHTML = deptArr[i] + "(" + deptCntArr[i] + ")";
    //a객체에 클릭 이벤트 등록
    a.addEventListener("click", deptClickHandler);

    //차례대로 각 부모에 append
    li.appendChild(a);
    ulDeptObj.appendChild(li);
  }

  //부서 + 부서별 사원수 get
  $.ajax({
    url: backurlDept,
    method: "get",
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        deptArr[i] = e.department_title;
        deptCntArr[i] = e.count;
        deptIdArr[i] = e.department_id;
      });
      //부서 li생성하는 함수 호출
      for (var i = 0; i < deptArr.length; i++) {
        createDeptElement(i);
      }
    },
  });

  //전체 사원 정보 get
  $.ajax({
    url: backurlAllEmp,
    method: "get",
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        empArr[i] = e.name;
        positionArr[i] = e.position.position_title;
        empIdArr[i] = e.employee_id;
      });
      for (var i = 0; i < empArr.length; i++) {
        //사원 3명씩 감싸는 객체 생성
        if (i % 3 == 0) {
          createEmpElementBig();
        }
        //사원 객체 생성
        createEmpElement(i);
      }
    },
  });

  //검색 submit 이벤트 핸들러
  function searchSubmitHandler(e) {
    //배열 초기화
    emptyElement();
    //'검색한 단어'의 검색 결과
    empHeaderObj.innerText = "'" + wordObj.value + "'의 검색 결과";
    $.ajax({
      url: backurlSearchEmp,
      method: "post",
      data: {
        word: wordObj.value,
      },
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          empArr[i] = e.name;
          positionArr[i] = e.position.position_title;
          empIdArr[i] = e.employee_id;
        });
        //cardBody 생성
        createCardBody();

        for (var i = 0; i < empArr.length; i++) {
          //사원 3명씩 감싸는 객체 생성
          if (i % 3 == 0) {
            createEmpElementBig();
          }
          //사원 객체 생성
          createEmpElement(i);
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

    e.preventDefault();
  }

  //검색  form 객체에 submit 이벤트 등록
  formObj.addEventListener("submit", searchSubmitHandler);
});
