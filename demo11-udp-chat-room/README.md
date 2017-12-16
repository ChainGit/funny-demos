### 聊天室

基于UDP的可用于公网的多线程设计的多人聊天室

博客地址：[地址](https://www.leechain.top/blog/2017/12/05-udp-chat-room.html)

阿里云公网服务器工作截图：

![image](https://github.com/ChainGit/mine-demos/blob/master/demo03/chat-room/4.png)

客户端截图（每个客户端都在不同的主机和不同的网路环境下）：

具体的测试环境：移动4G网，电信4G网，校园宽带网

![image](https://github.com/ChainGit/mine-demos/blob/master/demo03/chat-room/2.jpg)

![image](https://github.com/ChainGit/mine-demos/blob/master/demo03/chat-room/3.jpg)

![image](https://github.com/ChainGit/mine-demos/blob/master/demo03/chat-room/1.png)

客户端可以在同一台电脑上运行多个实例，另外即使电脑的网络环境发生变化（比如切换到不同的网络）也能做到不掉线。

这个聊天DEMO没有确保UDP消息一定能做到可靠，没有遗漏，只是为了学习多线程和计算机网络与通信。

