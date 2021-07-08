$(function() {
	//옆에 메뉴 객체
	var navObj = document.querySelector("li.navObj");
	//메뉴의 드롭다운 객체
	var ulDeptObj = navObj.querySelector("ul.sidebar-dropdown");
	//내용 담는 객체
	var contentObj = document.querySelector("div.deptContent");
	//내용담는 객체의 헤더 객체
	var empHeaderObj = contentObj.querySelector("h5.empHeader");
	//내용담는 객체의 내용
	var empBodyObj = contentObj.querySelector("div.empBody");
	//내용 담는 객체의 내용 한줄 감싸는 div
	var empContentObj = new Array();
	//모달 창
	var modalDetailObj = document.querySelector("div#modalDetail");
	//모달 창 내용
	var modalNameObj = modalDetailObj.querySelector("h4");
	var modalPositionObj = modalDetailObj.querySelector("div.cardBody div:first-child>div");
	var modalEmployeeIdObj = modalDetailObj.querySelector("div.cardBody div:nth-child(2)>div");
	var modalDepartmentObj = modalDetailObj.querySelector("div.cardBody div:nth-child(3)>div");
	var modalJobObj = modalDetailObj.querySelector("div.cardBody div:nth-child(4)>div");
	var modalPhoneObj = modalDetailObj.querySelector("div.cardBody div:nth-child(5)>div");
	var modalEmailObj = modalDetailObj.querySelector("div.cardBody div:last-child>div");

	//검색 form 태그
	var formObj = document.querySelector("form.searchEmp");
	var wordObj = formObj.querySelector("input.searchWord");

	//불러온 부서명
	var deptArr = new Array();
	//불러온 부서별 사원 수
	var deptCntArr = new Array();
	//불러온 부서번호
	var deptIdArr = new Array();
	//불러온 부서별 사원 이름명
	var empArr = new Array();
	//불러온 부서별 사원 직책명
	var positionArr = new Array();
	//불러온 사원의 사번
	var empIdArr = new Array;

	var detailName;
	var detailPosition;
	var detailEmployeeId;
	var detailDepartment;
	var detailJob;
	var detailPhone;
	var detailEmail;

	var backurlDept = '/back/showdept';
	var backurlAllEmp = '/back/showallemp'
	var backurlDeptEmp = '/back/showdeptemp';
	var backurlEmpDetail = '/back/showempdetail';
	var backurlSearchEmp = '/back/searchemp';

	function openTargetModal(modalId, modalObj) {
		modalNameObj.innerHTML = detailName;
		modalPositionObj.innerHTML = detailPosition;
		modalEmployeeIdObj.innerHTML = detailEmployeeId;
		modalDepartmentObj.innerHTML = detailDepartment;
		modalJobObj.innerHTML = detailJob;
		modalPhoneObj.innerHTML = detailPhone;
		modalEmailObj.innerHTML = detailEmail;

		var openBtn = document.querySelector(modalId);
		var modal = document.querySelector("#" + modalObj + ">.modal");
		var overlay = modal.querySelector(".modal_overlay");
		var xBoxBtn = modal.querySelector("button.xBox");
		var openModal = () => {
			modal.classList.remove("hidden");
		};
		var closeModal = () => {
			modal.classList.add("hidden");
		};
		if (openBtn != null) {
			//input에는 없는 overlay 클릭 -> close 기능
			overlay.addEventListener("click", closeModal);
			//모달창 열기 버튼
			openBtn.addEventListener("click", openModal);
			//모달창 닫기 버튼
			xBoxBtn.addEventListener("click", closeModal);
		}
	}

	function empClickHandler(e) {
		var empInfoArr = (e.target.id).split("/");
		$.ajax({
			url: backurlEmpDetail,
			method: 'get',
			data: {
				empId: empInfoArr[0],
				empName: empInfoArr[1],
			},
			success: function(responseData) {
				detailName = responseData.name;
				detailPosition = responseData.position.position_title;
				detailEmployeeId = responseData.employee_id;
				detailDepartment = responseData.department.department_title;
				detailJob = responseData.job.job_title;
				detailPhone = responseData.phone_number;
				detailEmail = responseData.email;
			},
		});
		openTargetModal("." + empInfoArr[0] + "openDetail", "modalDetail");
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
		var divIcon = document.createElement("div");
		divIcon.setAttribute("class", "fa fa-user fa-3x mr-3");
		var divContent = document.createElement("div");
		divContent.setAttribute(
			"class",
			"flex-grow-1 mr-2 " + empIdArr[i] + "openDetail"
		);
		divContent.setAttribute("id", (empIdArr[i] + "/" + empArr[i]));
		//		var span = document.createElement("span");
		//		span.setAttribute("id", (empIdArr[i] + "/" + empArr[i]));
		//		span.innerHTML = empArr[i] + "<br/>";
		var small = document.createElement("small");
		small.setAttribute("class", "text-muted");
		small.innerHTML = positionArr[i];
		small.setAttribute("id", (empIdArr[i] + "/" + empArr[i]));
		divContent.innerHTML = empArr[i] + "<br/>";

		//		span.addEventListener("click", empClickHandler);
		//		divContent.appendChild(span);
		divContent.appendChild(small);
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
		emptyElement();
		$.ajax({
			url: backurlDeptEmp,
			method: 'get',
			data: {
				deptId: dept
			},
			success: function(responseData) {
				$(responseData).each(function(i, e) {
					empArr[i] = e.name;
					positionArr[i] = e.position.position_title;
					empIdArr[i] = e.employee_id;
				});
				createCardBody();

				for (var i = 0; i < empArr.length; i++) {
					if (i % 3 == 0) {
						createEmpElementBig();
					}
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
		var li = document.createElement("li");
		li.setAttribute("class", "sidebar-item");
		var a = document.createElement("a");
		a.setAttribute("class", "sidebar-link-js");
		a.setAttribute("id", deptIdArr[i]);
		a.innerHTML = deptArr[i] + "(" + deptCntArr[i] + ")";
		a.addEventListener("click", deptClickHandler);
		li.appendChild(a);
		ulDeptObj.appendChild(li);
	}


	$.ajax({
		url: backurlDept,
		method: 'get',
		success: function(responseData) {
			$(responseData).each(function(i, e) {
				deptArr[i] = e.department_title;
				deptCntArr[i] = e.count;
				deptIdArr[i] = e.department_id;
			});
			for (var i = 0; i < deptArr.length; i++) {
				createDeptElement(i);
			}
		},
	});

	$.ajax({
		url: backurlAllEmp,
		method: 'get',
		success: function(responseData) {
			$(responseData).each(function(i, e) {
				empArr[i] = e.name;
				positionArr[i] = e.position.position_title;
				empIdArr[i] = e.employee_id;
			});
			for (var i = 0; i < empArr.length; i++) {
				if (i % 3 == 0) {
					createEmpElementBig();
				}
				createEmpElement(i);
			}
		},
	});

	function searchSubmitHandler(e) {
		emptyElement();
		empHeaderObj.innerText = "'" + wordObj.value + "'의 검색 결과";
		$.ajax({
			url: backurlSearchEmp,
			method: "post",
			data: {
				word: wordObj.value,
			},
			success: function(responseData) {
				$(responseData).each(function(i, e) {
					empArr[i] = e.name;
					positionArr[i] = e.position.position_title;
					empIdArr[i] = e.employee_id;
				});
				createCardBody();

				for (var i = 0; i < empArr.length; i++) {
					if (i % 3 == 0) {
						createEmpElementBig();
					}
					createEmpElement(i);
				}
			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});

		e.preventDefault();
	}

	formObj.addEventListener("submit", searchSubmitHandler);
});
