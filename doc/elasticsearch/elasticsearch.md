前置条件
    文件创建数

修改Linux系统的限制配置，将文件创建数修改为65536个 ：
1. 修改系统中允许应用最多创建多少文件等的限制权限。Linux默认来说，一般限制应用最多创建的文件是65535个。但是ES至少需要65536的文件创建数的权限。
2. 修改系统中允许用户启动的进程开启多少个线程。默认的Linux限制root用户开启的进程可以开启任意数量的线程，其他用户开启的进程可以开启1024个线程。必须修改限制数为4096+。因为ES至少需要4096的线程池预备。

vi /etc/security/limits.conf
#新增如下内容在limits.conf文件中
es soft nofile 65536
es hard nofile 65536
es soft nproc 4096
es hard nproc 4096

系统控制权限
修改系统控制权限，ElasticSearch需要开辟一个65536字节以上空间的虚拟内存。Linux默认不允许任何用户和应用程序直接开辟这么大的虚拟内存。

添加参数:新增如下内容在sysctl.conf文件中，当前用户拥有的内存权限大小
vi /etc/sysctl.conf
vm.max_map_count=262144

重启生效:让系统控制权限配置生效
sysctl -p