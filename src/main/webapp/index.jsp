<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<style>
	body{background-color: #dedede;margin:0;width:100%}
	.data-container{position:fixed;left:10px;right:10px;background-color:#FFFFFF;border-radius:10px}
	#login-container{width:250px;margin: -40px auto 0 auto;top:50%;height:40px}
	#message-list-container{top:10px;bottom:60px;overflow-y: scroll;}
	#message-sender-container{bottom:10px;height:40px;}
	#message-sender-container #input-message-box{position:fixed;left:20px;right:120px;bottom:9px}
	#message-sender-container #input-message-box input[name=message]{width:100%}
	#message-sender-container input[name=send-message]{position:absolute;right:10px;width:60px}
	#message-list .message {border: 1px solid #dedede;margin: 10px;border-radius: 10px;padding: 15px;text-align: right;}
	input{border-radius:10px;margin:10px}
	
	</style>
	<title>Twitter-Like SocialBase Test</title>
</head>
<body>

	<c:if test="${param.code eq null}"> 
		<form id="login-container" class="data-container" action="https://www.facebook.com/dialog/oauth">
			<input type="hidden" name="client_id" value="${loginAccessData.clientId}"/>
			<input type="hidden" name="grant_type" value="client_credentials"/>
			<input type="hidden" name="redirect_uri" value="${loginAccessData.redirectUri}"/>
			<input type="hidden" name="response_type" value="code"/>
			
			<input type="submit" class="fb-login-button" value="Logar com Facebook"/>
		</form>
	</c:if>
	<c:if test="${param.code ne null}">
		<div id="message-list-container" class="data-container">
			<div id="message-list">
			</div>
		</div>
		<form id="message-sender-container" class="data-container">
		
			<div id="input-message-box">
				<input name="message" maxlength="140"/>
			</div>
			<input type="submit" value="Enviar" name="send-message"/>
			
		</form>
	</c:if>
</body>
<script src="scripts/jquery-3.2.1.min.js"></script>
<script>
	var accessToken = null;
	$(function(){
		$("#message-sender-container").submit(function(){
			$.ajax({
				dataType: "json",
				method: "POST",
				url: "rest/message",
				headers: {
					"Accept": "application/json",
					"Content-Type": "application/json",
					"Authorization": "Beerus "+accessToken
				},
				data: JSON.stringify({
					message: $("input[name=message]").val()
				}),
				success: function(){
					$("input[name=message]").val("");
					loadMessages();
				},
				error: function(){
					window.location.href="${loginAccessData.redirectUri}";
				}
			});
			return false;
		})
	});

	function login(){

		$.ajax({

			dataType: "json",
			method: "POST",
			url: "rest/login",
			headers: {
				"Accept": "application/json",
				"Content-Type": "application/json"
			},
			data: JSON.stringify({
				code: '${param.code}'
			}),
			success: function(data, textStatus, jqXHR){
				accessToken = data.accessToken;
				loadMessages();
			},
			error: function(){
				window.location.href="${loginAccessData.redirectUri}";
			}
			
		});
	}

	function loadMessages(){

		$.ajax({

			dataType: "json",
			method: "GET",
			url: "rest/message",
			headers: {
				"Accept": "application/json",
				"Content-Type": "application/json",
				"Authorization": "Beerus "+accessToken
			},
			success: function(data, textStatus, jqXHR){
				var html = "";
				for(var cont = 0; cont < data.length; cont++){
					html += "<div class='message'>"+data[cont].message+"</div>";
				}
				$("#message-list").html(html);
			},
			error: function(){
				window.location.href="${loginAccessData.redirectUri}";
			}
			
		});
	}
</script>
<c:if test="${param.code ne null}">
	<script>
		login();
	</script>
</c:if>
</html>
