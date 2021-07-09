$(function() {
	var loginInfoIdObj = document.querySelector("div.profileDropdown span.loginId");
	var loginInfoNameObj = document.querySelector("div.profileDropdown span.loginName");

	var bdDetailObj = document.querySelector("div.bdDetailTitle");
	var detailTitleObj = bdDetailObj.querySelector("h2");
	var detailInfoObj = bdDetailObj.querySelector("div.flex-grow-1");
	var infoWriterObj = detailInfoObj.querySelector("small.bdDetailWriter");
	var infoDateObj = detailInfoObj.querySelector("small.bdDetailDate");
	var detailContentObj = document.querySelector("div.bdDetailContent");
	var bdBtnGroupObj = document.querySelector("div.bdBtnGroup");
	var cmCardBodyObj = document.querySelector("div.cmCardBody");
	var cmWriterObj = document.querySelector("h5.currentLoginId");
	var cmTextAreaObj = document.querySelector("textarea");
	var cmRegisterFormObj = document.querySelector("div.col-md-12 form");
	console.log(cmRegisterFormObj);

	var bdDetailBdNo = localStorage.getItem('bdNumber');
	var bdDetailTitle;
	var bdDetailWriter;
	var bdDetailWriterId;
	var bdDetailDate;
	var bdDetailContent;

	var currentLoginEmp = loginInfoNameObj.innerText;
	var currentLoginId = loginInfoIdObj.innerText;

	var cmNo = new Array();
	var cmWriter = new Array();
	var cmWrtierId = new Array();
	var cmDate = new Array();
	var cmContent = new Array();

	function createCmElement(i) {
		var cmBodyDiv = document.createElement("div");
		cmBodyDiv.setAttribute("class", "d-flex align-items-start cmBody mb-2");
		var profileImg = document.createElement("img");
		profileImg.setAttribute("src", "img/avatars/avatar-5.jpg");
		profileImg.setAttribute("width", "36");
		profileImg.setAttribute("height", "36");
		profileImg.setAttribute("class", "rounded-circle mr-2 mb-2");
		profileImg.setAttribute("alt", cmWriter[i]);

		var cmContentDiv = document.createElement("div");
		cmContentDiv.setAttribute("class", "flex-grow-1");

		var cmDeleteSmall = document.createElement("small");
		cmDeleteSmall.setAttribute("class", "float-right text-navy");
		var cmDeleteA = document.createElement("a");
		cmDeleteA.setAttribute("class", "cmDeleteBtn");
		cmDeleteA.setAttribute("href", "board-detail.html");
		cmDeleteA.innerHTML = "X";
		cmDeleteSmall.appendChild(cmDeleteA);

		var cmDateSmall = document.createElement("small");
		cmDateSmall.setAttribute("class", "float-right text-navy mr-3 cmDate");
		cmDateSmall.innerHTML = cmDate[i];
		var cmContentSpan = document.createElement("span");
		cmContentSpan.setAttribute("class", "cmContnet");
		cmContentSpan.innerHTML = cmContent[i] + "<br />";
		var cmWriterSmall = document.createElement("small");
		cmWriterSmall.setAttribute("class", "text-muted cmWriter");
		cmWriterSmall.innerHTML = cmWriter[i] + " " + cmWrtierId[i] + "<br />";

		cmContentDiv.appendChild(cmDeleteSmall);
		cmContentDiv.appendChild(cmDateSmall);
		cmContentDiv.appendChild(cmContentSpan);
		cmContentDiv.appendChild(cmWriterSmall);

		cmBodyDiv.append(profileImg);
		cmBodyDiv.appendChild(cmContentDiv);

		cmCardBodyObj.appendChild(cmBodyDiv);

		if (currentLoginId != cmWrtierId[i]) {
			cmDeleteSmall.setAttribute("style", "display:none");
		} else {
			cmDeleteSmall.removeAttribute("style");
		}
	}

	function createBdDatailElement() {
		detailTitleObj.innerHTML = bdDetailTitle;
		infoWriterObj.innerHTML = bdDetailWriter;
		infoDateObj.innerHTML = bdDetailDate;
		detailContentObj.innerHTML = bdDetailContent;

		if (currentLoginId != bdDetailWriterId) {
			bdBtnGroupObj.setAttribute("style", "display:none");
		} else {
			bdBtnGroupObj.removeAttribute("style");
		}
		cmWriterObj.innerHTML = currentLoginEmp;
	}

	var backurlBdDetail = '/back/showbddetail';
	var backurlCm = '/back/showboardcomment';
	var backurlAddCm = '/back/addboardcomment';

	$.ajax({
		url: backurlBdDetail,
		method: 'get',
		data: {
			bdNo: bdDetailBdNo,
		},
		success: function(responseData) {
			bdDetailBdNo = responseData.bd_no;
			bdDetailTitle = responseData.bd_title;
			bdDetailWriter = responseData.writer.name;
			bdDetailWriterId = responseData.writer.employee_id;
			bdDetailDate = responseData.bd_date;
			bdDetailContent = responseData.bd_content;
			createBdDatailElement();
		},
	});

	$.ajax({
		url: backurlCm,
		method: 'get',
		data: {
			bdNo: bdDetailBdNo,
		},
		success: function(responseData) {
			$(responseData).each(function(i, e) {
				cmNo[i] = e.cm_no;
				cmContent[i] = e.cm_content;
				cmWriter[i] = e.cm_writer.name;
				cmWrtierId[i] = e.cm_writer.employee_id;
				cmDate[i] = e.cm_date;
			});

			for (var i = 0; i < cmContent.length; i++) {
				createCmElement(i);
			}
		},
	});
	function cmRegisterClickHandler(e) {
		$.ajax({
			url: backurlAddCm,
			method: 'post',
			data: {
				addTargetBdNo: bdDetailBdNo,
				addCmWriter: currentLoginEmp,
				addCmWriterId: currentLoginId,
				addCmContent: cmTextAreaObj.value

			},
			success: function(responseData) {
				alert("댓글이 추가되었습니다!");
			},
		});
		e.preventDefault();
	}
	cmRegisterFormObj.addEventListener("submit", cmRegisterClickHandler);



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

	var $content = $("main.content");
	// $div.load("main.html");
	$modifyBtnObj.click(function() {
		//클릭된현재객체의 href속성값 얻기 : .attr('href');
		var href = $(this).attr("href");
		switch (href) {
			case "board-modify.html":
				$content.load(href, function(responseTxt, statusTxt, xhr) {
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

	$deleteBtnObj.click(function() {
		//클릭된현재객체의 href속성값 얻기 : .attr('href');
		var href = $(this).attr("href");
		switch (href) {
			case "board.html":
				$content.load(href, function(responseTxt, statusTxt, xhr) {
					if (statusTxt == "error")
						alert("Error: " + xhr.status + ": " + xhr.statusText);
				});
				break;
		}
		return false;
	});
	$cmDeleteBtnObj.click(function() {
		//클릭된현재객체의 href속성값 얻기 : .attr('href');
		var href = $(this).attr("href");
		console.log(href);
		console.log($content);
		switch (href) {
			case "board-detail.html":
				$content.load(href, function(responseTxt, statusTxt, xhr) {
					alert("안녕");
					if (statusTxt == "error")
						alert("Error: " + xhr.status + ": " + xhr.statusText);
				});
				break;
		}
		return false;
	});
});
