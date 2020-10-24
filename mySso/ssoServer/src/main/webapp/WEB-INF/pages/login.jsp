<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        div.crust{
           	position:absolute;
            padding: 5px;
            text-align:center;
        }
		body{
			background-image:url(images/background1.jpg);
			background-size:100%;
		}
		input{
			outline-style: none ;
    		border: 1px solid #ccc; 
    		border-radius: 3px;
    		font-size: 20px;
    		font-family: "Microsoft soft";
		}
		input:focus{
    		border-color: #66afe9;
    		outline: 0;
    		-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
    		box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
		}  /*选中时，边框发蓝色的光*/ 
        .crust{
			border: 5px solid rgba(255,255,255,1);
		    border-radius: 20px;
		    width: 370px;
		    height: 400px;
		    top: 50%;
		    left: 50%;
		    transform: translate(-50%,-50%);
			background-color: rgba(230,230,255,0.8);
			box-shadow:11px 11px 11px rgba(50,50,50,1);	
        }
		.text{
			height:72%;
			width: 180px;
		}
		a {text-decoration: none}
    </style>
<title>SSO登录中心</title>
</head>
<body>
	<div class="crust">
		<form action="login" method="post"> 
			<p style="text-align:center;font-size:30px;">SSO登录中心</p>
			
			<div>
			  <img src="images/username.png" height="25px" />&nbsp;&nbsp;
			  <input type="text" name="id" placeholder="登录名" />
			</div><br />
			<div>
			  <img src="images/password.png" height="25px" />&nbsp;&nbsp;
			  <input type="password" name="pwd" placeholder="密码"/>
			</div><br />
			
			<div><img id="codeImg" src="authCode" onclick="refreshCode()" /></div><br />
			<div>
			  <input type="text" name="vericode" placeholder="验证码(点击图片刷新)"/>
			</div><br />
			
			<div>
			  <input type="submit" value="登录" />
			</div>
			
			<input type="text" name="backUrl" value="${backUrl}" style ="display:none">
			<input type="text" name="sessionID" value="${sessionID}" style ="display:none">
			
		</form>
		<div>
		<%if(session.getAttribute("remind")=="error") {%>
        	<p>账号密码或验证码错误</p>
		<% }%>
		</div>
	</div>
</body>
<script type="text/javascript">
  function refreshCode() {
    var codeImg = document.getElementById("codeImg");
    var d = new Date();
    codeImg.src = "authCode?s="+d;
  }
</script>
</html>