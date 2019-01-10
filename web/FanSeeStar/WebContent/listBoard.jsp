<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalble=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Fan See star</title>

   
    <link href="resources/list/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    
    <link href="resources/list/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">

    
    <link href="resources/list/vendor/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">

    
    <link href="resources/list/css/freelancer.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/list/assets/css/main.css"/>
    <link rel="stylesheet" href="resources/list/assets/css/bootstrap/bootstrap.css">

  </head>

  <body id="page-wrapper">

    
    <nav class="navbar navbar-expand-lg bg-secondary fixed-top text-uppercase" id="mainNav">
      <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="index.jsp">팬시스타 게시판</a>
        <button class="navbar-toggler navbar-toggler-right text-uppercase bg-primary text-white rounded" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          Menu
          <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
         
        </div>
      </div>
    </nav>

   
    <header class="masthead bg-primary text-white text-center">
      <div class="container">
        <img class="img-fluid mb-5 d-block mx-auto" src="resources/list/img/banner.png" alt="" width="600" height="600">
        <h1 class="text-uppercase mb-0">WELCOME!!</h1>
        <hr class="star-light">
       
      </div>
    </header>

   
    <section class="portfolio" id="portfolio">
      <div class="container">
        <h2 class="text-center text-uppercase text-secondary mb-0">자유게시판</h2>
        

    
    <script src="resources/list/vendor/jquery/jquery.min.js"></script>
    <script src="resources/list/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

   
    <script src="resources/list/vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="resources/list/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

 
    <script src="resources/list/js/jqBootstrapValidation.js"></script>
    <script src="resources/list/js/contact_me.js"></script>

    
    <script src="resources/js/freelancer.min.js"></script>

      <table class="table table-striped">
      
    <thead>
 	<tr>
 	<td></td>
 	<td></td>
 	<td></td>
 	<td></td>
 	<td></td>
 	<td></td>
 	</tr>
    <tr>
        <td>NO</td>
        <td>아티스트</td>
        <td>작성자</td>
        <td>제목</td>
        <td></td>
        <td></td>
    
    </tr>
    </thead>
    <tbody>
            <c:forEach items="${boards}" var="board" varStatus="sts">
                    <tr>
                        <td>${sts.count }</td>
                        <td>${board.artistName }</td>
                        <td>${board.writer }</td>
                        <td><a class="pull-left" href="detailBoard?boardId=${board.boardId }">${board.boardTitle }</a></td>
                        <td><a class="pull-left" href="updateBoardList?boardId=${board.boardId }&&writer=${board.writer }&&artistName=${board.artistName}">수정</a></td>
                        <td><a class="pull-left" href="deleteBoard?boardId=${board.boardId }&&writer=${board.writer }&&artistName=${board.artistName}">삭제</a></td>
                    </tr>
                </c:forEach>
    </tbody>
</table>

<hr/>


<div style="border:1pBD; width:340px; height: 285px; margin: auto;">
    <ul class="pagination">
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
          <a class="btn btn-default pull-right" href="createBoard.jsp?artistName=${artist }">글쓰기</a>

    </ul>
</div>
</div>
  </body>

</html>
