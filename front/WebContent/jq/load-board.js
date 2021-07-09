//board 페이지에서 적용할 내용
$(function() {
	var bdPageTitleObj = document.querySelector("h1.h1Title");
	var btnGroupObj = document.querySelector("div.btn-group");
	var categoryObj = btnGroupObj.querySelector("div.dropdown-menu");
	var tBodyObj = document.querySelector("tbody.bdTbody");
	var pageGroupObj = document.querySelector("ul.pageGroup");

	var pageli = null;
	var pagea = null;
	var bdNo = new Array();
	var bdTitle = new Array();
	var bdWriter = new Array();
	var bdDate = new Array();
	var pageGroup = [1, 2, 3, 4];
	//	var anotherGroup = [5, 6, 7, 8];

	var $titleObj;

	var inputGroupObj = document.querySelector("div.input-group");
	var searchObj = inputGroupObj.querySelector("input[type=text]");

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
	}

	function pageClickHandler(e) {
		bdPageTitleObj.innerHTML = e.target.id +  " PAGE";
	}

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

	function createBdElement(i) {
		var tr = document.createElement("tr");
		var th = document.createElement("th");
		th.setAttribute("scope", "row");
		th.innerHTML = i + 1;
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

	function categoryHandler(e) {
		if (e.target.id == "categoryTitle") {
			searchObj.setAttribute("placeholder", "제목으로 검색하기");
		} else {
			searchObj.setAttribute("placeholder", "작성자로 검색하기");
		}
	}

	var backurlBdPage = '/back/showbdpage';
	$.ajax({
		url: backurlBdPage,
		method: 'get',
		data: {
			bdCurrentPage: bdPageTitleObj.innerText.split(" ")[0],
		},
		success: function(responseData) {
			$(responseData).each(function(i, e) {
				bdNo[i] = e.bd_no;
				bdTitle[i] = e.bd_title;
				bdWriter[i] = e.writer.name;
				bdDate[i] = e.bd_date;
			});
			for (var i = 0; i < bdNo.length; i++) {
				createBdElement(i);
			}
			$titleObj = $(
				"div.wrapper>div.main>main.content div.row div.table-responsive tbody tr td:nth-child(2) a"
			);

			$titleObj.click(function(e) {
				localStorage.setItem('bdNumber',e.target.id);
				//클릭된현재객체의 href속성값 얻기 : .attr('href');
				var href = $(this).attr("href");
				switch (href) {
					case "board-detail.html":
						$content.load(href, function(responseTxt, statusTxt, xhr) {
							if (statusTxt == "error")
								alert("Error: " + xhr.status + ": " + xhr.statusText);
						});
						break;
				}
				return false;
			});
		},
	});


	categoryObj.addEventListener("click", categoryHandler);

	//글쓰기 버튼 클릭 시 글쓰기 페이지로 이동
	//글쓰기 버튼 찾는 Obj
	var $registerObj = $(
		"div.wrapper>div.main>main.content div.row div.card-header>a"
	);
	// console.log($registerObj);

	//제목 클릭 시 게시글 상세 페이지로 이동
	//제목 찾는 Obj

	// console.log($titleObj);

	//main.content Obj 찾기
	var $content = $("main.content");
	// console.log($content);
	$registerObj.click(function() {
		var href = $(this).attr("href");
		// console.log(href);
		switch (href) {
			case "board-register.html":
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

});
