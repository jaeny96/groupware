$(function() {
	var loginInfoIdObj = document.querySelector("div.profileDropdown span.loginId");
	var loginInfoNameObj = document.querySelector("div.profileDropdown span.loginName");

	var addBdFormObj = document.querySelector("form#addBdForm");
	var titleObjInAdd = document.querySelector("input[name=title]#insertTitle");
	var contentObjInAdd = document.querySelector("div.note-editable");

	var backurlAddBoard = '/back/addboard';
	function addBdFormSubmitHandler(e) {

		$.ajax({
			url: backurlAddBoard,
			method: "post",
			data: {
				addBdWriter : loginInfoNameObj.innerText,
				addBdWriterId :loginInfoIdObj.innerText,
				addBdTitle: titleObjInAdd.value,
				addBdContent: contentObjInAdd.innerText,
			},
			success: function() {
				if (titleObjInAdd.value == "" || titleObjInAdd.value == null) {
					alert("제목이 입력되지 않았습니다");
				} else {
					alert("게시글이 등록되었습니다");
					$(
						'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="board.html"]'
					).trigger('click');
				}
			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});
		e.preventDefault();
	}

	addBdFormObj.addEventListener("submit", addBdFormSubmitHandler);

});

