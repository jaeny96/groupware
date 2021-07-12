$(function(){

var backSkdModify= '/back/modifyschedule';
var modifySkdFormObj = $('#modifySkdContent');
 console.log(modifySkdFormObj);
var modifySkdSubmitBtn = $('button.modifySkdSubmit');
console.log("mmmmmmmmmmmm"+modifySkdSubmitBtn);
var skdUpdateType = $("#skdUpdateTypeSelect option:selected");
console.log(skdUpdateType.val());
var skdUpdateTitle = $('#update_title');
console.log('야호2');
console.log(skdUpdateTitle.val());
var skdUpdateContent = $("#input_content_update"); //내용
console.log(skdUpdateContent.val());
var skdUpdateStartDate = $("#start_date_update"); //시작날짜
var test = document.querySelector("label.form-check");
console.log(test);
var skdUpdateStartTime = $("#start_time_update");//시작시간
console.log(skdUpdateStartDate.val() + " " + skdUpdateStartTime.val());
var skdUpdateEndDate = $("#end_date_update");//종료날짜
var skdUpdateEndTime = $("#end_time_update");//종료시간
console.log(skdUpdateEndDate.val() + " " + skdUpdateEndTime.val());
var skdUpdateShare = $('input[name="radio-2"]:checked').val();
console.log(skdUpdateShare);





function modifySkdSubmitHandler(e) {
console.log(skdUpdateShare);


    //변수를  가져와야 하나? 
    
    if (skdUpdateShare == "p"){
		$.ajax({
      
			url: backSkdModify,
			method: "post",
			data: {
        title: skdUpdateTitle.val(),
        content: skdUpdateContent.val(),
        start: skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
        end: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
        allDay: false,
        backgroundColor: '#ffc107',
        extendedProps: {
          calendarType: skdUpdateType.val(),
          teamOrPersonal: skdUpdateShare
        }

			},
			success: function() {
					alert("일정이 변경되었습니다");

			},
			error: function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});
		e.preventDefault();

 }else{
  $.ajax({
    url: backSkdModify,
    method: "post",
    data: {
      title: skdUpdateTitle.val(),
      content: skdUpdateContent.val(),
      start: skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
      end: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
      allDay: false,
      backgroundColor: '#28a745',
      extendedProps: {
        calendarType: skdUpdateType.val(),
        teamOrPersonal: skdUpdateShare
      }

    }  ,
    success: function() {
        alert("일정이 변경되었습니다");
    },
    error: function(request, status, error) {
      alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
    }
  });
  e.preventDefault();
}
}

modifySkdSubmitBtn.click(modifySkdSubmitHandler);

});
