<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>마이뮤직</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/style.css" rel="stylesheet">
<style type="text/css">
body {
	padding: 50px;
}
h1 {
	font-weight:bold;
	color : #A0B0DB;
}
.ranking {
	text-align: center;
	font-size: 28pt;
}

.spanTitle {
	font-size: 18pt;
	font-weight: bold;
	margin-right: 10px;
}

.pAlbum {
	color: gray;
	margin-left: 5px;
}

.imgAlbum {
	width: 80px;
	height: 80px;
	margin-right: 10px;
}

.btnPlay {
	margin-top:40%
}
</style>
</head>
<body>
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<h1>${loginedUser.userId}님의 Artist List</h1>
				<div style="text-align: right;">
					<a class="btn btn-xs btn-default" href="list">Home</a>
				</div><br>
				<table class="table table-hover table-condensed">
					<colgroup>
						<col width="80" align="center">
						<col width="*">
						<col width="70">
					</colgroup>
					<thead>
						<tr>
							<th>No</th>
							<th>Artist</th>
							<th>delete</th>
						</tr>
					</thead>
					<tbody>
                        <c:forEach items="${artists}" var="artist" varStatus="sts">
                        <tr>
                            <td class="ranking">${sts.count}</td>
                            <td>
                            <table>
                            <tr><td rowspan="2"><img class="imgAlbum" src="resources/image/${artist.image}" ></td>
                            <td><span class="spanTitle">${artist.artistName}</span></td>
                            </tr>
                            <tr><td><p class="pAlbum"><a class="btn btn-xs btn-default" href="boardList?artistName=${artist.artistName }"><b>게시판 가기</b></a>
                            <a class="btn btn-xs btn-default" href="schedule?artistName=${artist.artistName }&&company=${artist.agency }"><b>일정보기</b></a></p></td></tr>
                            </table>
                            <td><a class="btn btn-xs btn-danger btnPlay" href="deleteMyArtist?artistName=${artist.artistName }"><b> X </b></a></td>
                        </tr>
                        </c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>