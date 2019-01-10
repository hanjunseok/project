<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900"
	rel="stylesheet" />
<link href="resources/default.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="fonts.css" rel="stylesheet" type="text/css" media="all" />
<!-- <link rel="stylesheet" href="resources/assets2/css/main.css"/> -->
<link rel="stylesheet"
	href="resources/assets2/css/bootstrap/bootstrap.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">


<link rel="stylesheet"
	href="resources/bootstrap/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">


<script src="resources/bootstrap/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>




</head>
<body>
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="logo">
				<h1>
					<a href="index.jsp">Fan See Star</a>
				</h1>
			</div>
		</div>
		<div id="menu-wrapper">
			<div id="menu" class="container">
				<ul>
					<li class="current_page_item"><a href="#" accesskey="1"
						title=""><%=request.getParameter("artistName")%></a></li>

				</ul>
			</div>
		</div>
	</div>
	<!-- <div id="banner"></div> -->
	<div id="wrapper2">
		<div id="portfolio" class="container">
			<!-- <div class="title">
				<h2>Fan see star</h2>
				<span class="byline">ddd</span>
			</div> -->

			</thead>
			<tbody>
				<form action="boardCreate" method="post">  
					<tr>
						<p> 
							<th>제목</th>
						</p>
						<p>
							<td><input type="text"
								placeholder="제목을 입력하세요." name="boardTitle"
								class="form-control" /></td>
					</tr>
					<tr>
						</p>
						<p>
							<th>내용</th>
						</p>
						<td><textarea cols="10" style="overflow-y:scroll"
								placeholder="내용을 입력하세요" name="boardContent"
								class="form-control"></textarea></td>
					</tr>
					<td colspan="2">
						<p>
						<input type="hidden" value="${loginedUser.userId}" name="writer"></input>
						<input type="hidden" value=<%=request.getParameter("artistName")%> name="artistName"></input>
							<input type="submit" value="등록하기" class="button"></input>
								<input type="reset" value="취소" class="button"> </input> 
					</td>
					</tr>     
				</form>
			</tbody>
			</table>  



		</div>
	</div>


</body>
</html>
