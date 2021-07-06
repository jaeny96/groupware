//종류
//1. dateClick 날짜셀을클릭할때 발생하는 이벤트 2. eventClick일정을 클릭할때 발생하는 이벤트
//방법
//캘린더 초기화 시 클릭이벤트 추가
var calendar = new Calender(calendarEl, {
  dateClick: function () {
    alert("날짜를 클릭하였습니다.");
  },
});

//캘린더 초기화 후 클릭이벤트 추가
calendar.on("dateClick", function (info) {
  console.log("clicked on" + info.dateStr);
});

//Setter
// calendar.addEvent({ title: "evt4", start: "2019-09-04", end: "2019-09-06" });
var calendar = new calendar(calendarEl, {
  events: [
    {
      title: "Event1",
      start: "2021-07-07",
    },
    {
      title: "Event2",
      start: "2021-07-05",
    },
    // etc...
  ],
  color: "yellow", // an option!
  textColor: "black", // an option!
});
//Getter getEvents()메소드를 통해 calendar 객체가 갖고 있는 이벤트를 모두 배열타입으로 가져올수있다.
var arrCal = calendar.getEvnets();
alert(arrCal[0].title);
