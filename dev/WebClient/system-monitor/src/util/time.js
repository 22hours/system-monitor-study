export const getFilteredDate = (nowTime) => {
    let year = nowTime.getFullYear(); // 년도

    let month = new String(nowTime.getMonth() + 1);  // 월
    month = month >= 10? month : '0'+month;

    let day = new String(nowTime.getDate());  // 일
    day = day >= 10? day :'0'+day;

    let hour = new String(nowTime.getHours());  // 시간
    hour = hour >=10? hour : '0' + hour;

    let minute = new String(nowTime.getMinutes());
    minute = minute >=10? minute : '0' + minute;

    var sendTime = 
    year + "-" +
    month  + "-" +
    day  + "-" +
    hour  + "-" +
    minute;

    return sendTime;
};

export const plus30minute = () => {
    let today = new Date();   
    today.setMinutes(today.getMinutes()+30);
    return today;
};

