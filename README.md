# mySso
sso java maven (no framework)
# 描述
一共三个端，分别时web1、web2、ssoServer，分别表示两个独自的网站和一个单点登录认证网站。
http://localhost:8080//web1

http://localhost:8080//web1

http://localhost:8080//web1

# 版本说明
1.0实现cas流程的单点登录系统，在web1、web2、ssoServer任意处登录后，可以无需登录访问这几个网站。

2.0修复直接在ssoServer端登录后的退出bug

3.0修复验证码大小写不同而无法认证通过的bug
