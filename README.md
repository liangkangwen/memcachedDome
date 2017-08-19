# memcachedDome
这个案例是通过 spring 的 AOP 控制缓存，key 的命名股则是： 包名+ 类名 + 方法名 + 参数(多个)
当 修改数据时，把 Key 为  “包名+ 类名” 开始全部清除
 
#linux 安装 memcached
[root@lkw2 ~]# yum install memcached -y       # 通过 yum 命令安装
Loaded plugins: fastestmirror, security
Determining fastest mirrors
 * base: mirrors.aliyun.com
 * extras: mirrors.aliyun.com
 * updates: mirrors.aliyun.com
...
Installed:
  memcached.x86_64 0:1.4.4-5.el6                                                                                                                                   

Complete!
[root@lkw2 ~]# rpm -qa | grep memcached      # 查看是否安装
memcached-1.4.4-5.el6.x86_64
[root@lkw2 ~]# rpm -ql memcached             # 查看软件安装的目录  
/etc/rc.d/init.d/memcached
/etc/sysconfig/memcached
/usr/bin/memcached
/usr/bin/memcached-tool
/usr/share/doc/memcached-1.4.4
/usr/share/doc/memcached-1.4.4/AUTHORS
/usr/share/doc/memcached-1.4.4/CONTRIBUTORS
/usr/share/doc/memcached-1.4.4/COPYING
/usr/share/doc/memcached-1.4.4/ChangeLog
/usr/share/doc/memcached-1.4.4/NEWS
/usr/share/doc/memcached-1.4.4/README
/usr/share/doc/memcached-1.4.4/protocol.txt
/usr/share/doc/memcached-1.4.4/readme.txt
/usr/share/doc/memcached-1.4.4/threads.txt
/usr/share/man/man1/memcached.1.gz
/var/run/memcached
[root@lkw2 ~]# more /etc/sysconfig/memcached
PORT="11211"
USER="memcached"
MAXCONN="1024"
CACHESIZE="64"
OPTIONS=""                                 # 可以控制连接的IP，如 OPTIONS="127.0.0.1,192.168.206.129"
[root@lkw2 ~]# service memcached start     # 启动 memcached
Starting memcached:                                        [  OK  ] 
