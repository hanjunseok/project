<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
Design by TEMPLATED
http://templated.co
Released for free under the Creative Commons Attribution License

Name       : Undeviating 
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20140322

-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900|Quicksand:400,700|Questrial"
	rel="stylesheet" />
<link href="resources/css/default.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="resources/css/fonts.css" rel="stylesheet" type="text/css"
	media="all" />

<link rel='stylesheet' type='text/css'
	href='http://www.blueb.co.kr/data/201010/IJ12872423858253/fullcalendar.css' />
<script type='text/javascript'
	src='http://www.blueb.co.kr/data/201010/IJ12872423858253/jquery.js'></script>
<script type='text/javascript'
	src='http://www.blueb.co.kr/data/201010/IJ12872423858253/jquery-ui-custom.js'></script>
<script type='text/javascript'
	src='http://www.blueb.co.kr/data/201010/IJ12872423858253/fullcalendar.min.js'></script>
<script type='text/javascript'>

	$(document).ready(function() {
		var date1 = new Date();
		var d = date1.getDate();
		var m = date1.getMonth();
		var y = date1.getFullYear();
		
		var listContent = [];
		var listYear = [];
		var listMonth = [];
		var listDay = [];
		var count;
		<c:forEach items="${schedules}" var="schedule" varStatus="sts">
		var jbString = "${schedule.date}";
		var year = jbString.split('-')[0];
		var month = jbString.split('-')[1];
		var day = jbString.split('-')[2];

		listContent["${sts.count}"] = "${schedule.content}";
		listYear["${sts.count}"] = year;
		listMonth["${sts.count}"] = month - 1;
		listDay["${sts.count}"] = day;
		count = "${sts.count}";
		

		</c:forEach>
		for (var i = 0, result = []; i < count + 1; i++) {
			test = listDay[i];
			result.push({
				"title" : listContent[i],
				"start" : new Date(listYear[i], listMonth[i], listDay[i]),

			});
		}

		$('#calendar').fullCalendar({
			
			
			events : result,
		});

	});
</script>
<style type='text/css'>
body {
	margin-top: 40px;
	text-align: center;
	font-size: 12px;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
}

#calendar {
	width: 550px;	
}
 
#top{ 
vertical-align: top;
}

</style>
</head>
<body>
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<a href="index.jsp"><span class="icon icon-cog"></span></a>
				<h1>
					<a href="index.jsp">FanSeeStar</a>
				</h1>
			</div>
			<div id="menu">
				<ul>
				<c:choose>
					<c:when test="${company eq 'SM' }">
					<li class="active"><a href="sm.jsp" accesskey="1" title="">SM</a></li>
					<li><a href="jyp.jsp" accesskey="2" title="">JYP</a></li>
					<li><a href="yg.jsp" accesskey="3" title="">YG</a></li>
					<li><a href="#" accesskey="4" title="">Etc</a></li>
					<li><a href="myArtistList" accesskey="5" title="">my artist</a></li>
					</c:when>
					<c:when test="${company eq 'JYP' }">
					<li ><a href="sm.jsp" accesskey="1" title="">SM</a></li>
					<li class="active"><a href="jyp.jsp" accesskey="2" title="">JYP</a></li>
					<li><a href="yg.jsp" accesskey="3" title="">YG</a></li>
					<li><a href="#" accesskey="4" title="">Etc</a></li>
					<li><a href="myArtistList" accesskey="5" title="">my artist</a></li>
					</c:when>
					<c:when test="${company eq 'YG' }">
					<li><a href="sm.jsp" accesskey="1"
						title="">SM</a></li>
					<li><a href="jyp.jsp" accesskey="2" title="">JYP</a></li>
					<li class="active"><a href="yg.jsp" accesskey="3" title="">YG</a></li>
					<li><a href="#" accesskey="4" title="">Etc</a></li>
					<li><a href="myArtistList" accesskey="5" title="">my artist</a></li>
					</c:when>
					<c:otherwise>
						
					<li class="active"><a href="sm.jsp" accesskey="1" title="">SM</a></li>
					<li ><a href="jyp.jsp" accesskey="2" title="">JYP</a></li>
					<li><a href="yg.jsp" accesskey="3" title="">YG</a></li>
					<li><a href="#" accesskey="4" title="">Etc</a></li>
					<li><a href="myArtistList" accesskey="5" title="">my artist</a></li>
					</c:otherwise>
				</c:choose>					
				</ul>
			</div>
		</div>
	</div>
	
	
	<div class="wrapper">
		<div id="banner" class="container">
			<!-- <img src="resources/images/smLogo.jpg" width="1300" height="200"
				alt="" /> -->
		</div>
		<div id="welcome" class="container">
		
		<table width=1100>
		<tr>
		<td>
			<div id='calendar'></div>
			<div class="title">
				<h2></h2>
			</div>
		</td>
		<td id=top width=450>
		<hr align="left" width=100% color="silver"></hr>
		<c:forEach items="${schedules}" var="schedule" varStatus="sts"> 
		<ul align="left"> 
		<c:if test="${schedule == null}">
		<li>일정이 없습니다.</li>
		</c:if>
		<li>${schedule.date}</li>
		<li>${schedule.content}</li>
		<hr align="left" width=100% color="silver"></hr>
		</ul>
		</c:forEach>
		</td>
		</tr>
		</table>
		
		
		</div>
		<div id="footer">
			<div class="container">
				<div class="fbox1">
					<span class="icon icon-map-marker"></span> <span> <br /></span>
				</div>
				<div class="fbox1">
					<span class="icon icon-phone"></span> <span> Telephone: +80
						123 4657 </span>
				</div>
				<div class="fbox1">
					<span class="icon icon-envelope"></span> <span>bujws3715@naver.com</span>
				</div>
			</div>
		</div>
</body>
</html>

