//main-form 에서 적용할 내용
$(function () {
  var $content = $("div.wrapper>div.main>main.content");

  var $postTypeMenuObj = $("#documentTypeSelect");
  console.log($postTypeMenuObj);

  $postTypeMenuObj.change(function (e) {
    var valueHref = e.target.value;
    console.log(valueHref);
    e.preventDefault();
    switch (valueHref) {
      case "post.html":
      case "post-detail-spending.html":
      case "post-detail-circular.html":
      case "post-detail-business.html":
      case "post-detail-account.html":
      case "post-detail-leave.html":
      case "post-detail-contact.html":
        $(
          'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="' +
            valueHref +
            '"]'
        ).trigger("click");
    }
    return false;
  });
});
