//main-form 에서 적용할 내용
$(function () {
  // var $content = $("div.wrapper>div.main>main.content");
  var $apMenuFirstDropDownItemObj = $("ul#docMenuFirst");
  var $postTypeMenuObj = $("#documentTypeSelect");
  console.log($postTypeMenuObj);

  $postTypeMenuObj.change(function (e) {
    var valueHref = e.target.value;
    e.preventDefault();
    switch (valueHref) {
      case "post.html":
      case "post-detail-spending.html":
      case "post-detail-circular.html":
      case "post-detail-business.html":
      case "post-detail-account.html":
      case "post-detail-leave.html":
      case "post-detail-contact.html":
        $('ul#docMenuFirst a[href="' + valueHref + '"]').trigger("click");
        $apMenuFirstDropDownItemObj.hide();
    }
    return false;
  });
});
