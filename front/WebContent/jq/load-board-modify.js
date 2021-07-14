$(function() {
	var originBdTitleInModi = localStorage.getItem('bdTitle');
	var originBdContentInModi = localStorage.getItem('bdContent');
	var bdTargetNoInModi = localStorage.getItem('bdNumber');

	var modifyBdFormObj = document.querySelector("form#modifyBdForm");
	var titleObjInModi = document.querySelector("input[name=title]");
	var contentObjInModi = document.querySelector("div.note-editable");
	var contentPlaceHolderObj = document.querySelector("div.note-placeholder");

	if (originBdContentInModi.innerHTML != "" || originBdContentInModi.innerHTML!= null) {
		contentPlaceHolderObj.innerHTML = "";
	}

	titleObjInModi.value = originBdTitleInModi;
	contentObjInModi.innerHTML = originBdContentInModi;

	var backurlModiBdDetail = '/back/changeboarddetail';

	function modifyBdSubmitHandler(e) {
		$.ajax({
			url: backurlModiBdDetail,
			method: "post",
			data: {
				modiBdTargetNo : bdTargetNoInModi,
				modiBdTitle: titleObjInModi.value,
				modiBdContent: contentObjInModi.innerText,
			},
			success: function() {
				if (titleObjInModi.value == "" || titleObjInModi.value == null) {
					alert("제목이 입력되지 않았습니다");
				} else {
					alert("게시글이 변경되었습니다");
					$(
						'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="board-detail.html"]'
					).trigger('click');
				}
			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});
		e.preventDefault();
	}


	modifyBdFormObj.addEventListener("submit", modifyBdSubmitHandler);


});
