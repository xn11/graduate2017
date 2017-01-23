/*constants*/
/*task status related*/
var TASK_STATUS_NOT_STARTED = 1;
var TASK_STATUS_STARTED = 2;
var TASK_STATUS_COMPLETED = 3;
var TASK_STATUS_INVALID = 4;

/*validate date format*/
function validateDateFormat(dateStr) {
	var datePattern = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/;
	return datePattern.test(dateStr);
}

/*validate time format*/
function validateTimeFormat(timeStr) {
	var timePattern = /^(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
	return timePattern.test(timeStr);
}

/*check is number*/
function isNumber(numberStr) {
	var numberPattern = /^\d+$/;
	if(numberPattern.test(numberStr)) {
		return true;
	}
	return false;
}

function checkLength(str, minLength, maxLength) {
	if(!str) {
		return false;
	}
	str = str.trim();
	if(str.length < minLength || str.length > maxLength) {
		return false;
	}

	return true;
}

function compareDate(date1, time1, date2, time2) {
	if(!date1 || !time1 || !date2 || !time2) {
		return false;
	}
	
	var dateTime1 = date1 + " " + time1;
	var dateTime2 = date2 + " " + time2;

	dateTime1 = new Date(Date.parse(dateTime1.replace("-", "/")));
	dateTime2 = new Date(Date.parse(dateTime2.replace("-", "/")));

	if(dateTime1 >= dateTime2) {
		return false;
	}else{
		return true;
	}

}