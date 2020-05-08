
### 1、日常操作命令

```
**查看当前所在的工作目录
pwd

**查看当前系统的时间
date

**查看有谁在线（哪些人登陆到了服务器）
who  查看当前在线
last 查看最近的登陆历史记录
```

### 2、文件系统操作
```
**
ls /    查看根目录下的子节点（文件夹和文件）信息
ls -al  -a是显示隐藏文件   -l是以更详细的列表形式显示

**切换目录
cd  /home

**创建文件夹
mkdir aaa     这是相对路径的写法
mkdir -p aaa/bbb/ccc
mkdir  /data    这是绝对路径的写法

**删除文件夹
rmdir   可以删除空目录
rm -r aaa   可以把aaa整个文件夹及其中的所有子节点全部删除
rm -rf aaa   强制删除aaa

**修改文件夹名称
mv aaa angelababy

**创建文件
touch  somefile.1   创建一个空文件
echo "i miss you,my baby" > somefile.2  利用重定向“>”的功能，将一条指令的输出结果写入到一个文件中，会覆盖原文件内容
echo "huangxiaoming ,gun dan" >> somefile.2     将一条指令的输出结果追加到一个文件中，不会覆盖原文件内容

用vi文本编辑器来编辑生成文件
******最基本用法
vi  somefile.4
1、首先会进入“一般模式”，此模式只接受各种快捷键，不能编辑文件内容
2、按i键，就会从一般模式进入编辑模式，此模式下，敲入的都是文件内容
3、编辑完成之后，按Esc键退出编辑模式，回到一般模式；
4、再按：，进入“底行命令模式”，输入wq命令，回车即可

******一些常用快捷键
一些有用的快捷键（在一般模式下使用）：
a  在光标后一位开始插入
A   在该行的最后插入
I   在该行的最前面插入
gg   直接跳到文件的首行
G    直接跳到文件的末行
dd   删除行，如果  5dd   ，则一次性删除光标后的5行
yy  复制当前行,  复制多行，则  3yy，则复制当前行附近的3行
p   粘贴
v  进入字符选择模式，选择完成后，按y复制，按p粘贴
ctrl+v  进入块选择模式，选择完成后，按y复制，按p粘贴
shift+v  进入行选择模式，选择完成后，按y复制，按p粘贴

查找并替换（在底行命令模式中输入）
%s/sad/88888888888888     效果：查找文件中所有sad，替换为88888888888888
/you       效果：查找文件中出现的you，并定位到第一个找到的地方，按n可以定位到下一个匹配位置（按N定位到上一个）

```

### 3、文件权限的操作

```
****linux文件权限的描述格式解读
drwxr-xr-x      （也可以用二进制表示  111 101 101  -->  755）

d：标识节点类型（d：文件夹   -：文件  l:链接）
r：可读   w：可写    x：可执行
第一组rwx：  表示这个文件的拥有者对它的权限：可读可写可执行
第二组r-x：  表示这个文件的所属组对它的权限：可读，不可写，可执行
第三组r-x：  表示这个文件的其他用户（相对于上面两类用户）对它的权限：可读，不可写，可执行


****修改文件权限
chmod g-rw haha.dat    表示将haha.dat对所属组的rw权限取消
chmod o-rw haha.dat 	表示将haha.dat对其他人的rw权限取消
chmod u+x haha.dat      表示将haha.dat对所属用户的权限增加x

也可以用数字的方式来修改权限
chmod 664 haha.dat
就会修改成   rw-rw-r--

如果要将一个文件夹的所有内容权限统一修改，则可以-R参数
chmod -R 770 aaa/
chown angela:angela aaa/    <只有root能执行>

目录没有执行权限的时候普通用户不能进入
文件只有读写权限的时候普通用户是可以删除的(删除文件不是修改它,是操作父及目录),只要父级目录有执行和修改的权限

```



### 4、基本的用户管理

```
*****添加用户
useradd  angela
要修改密码才能登陆
passwd angela  按提示输入密码即可


**为用户配置sudo权限
用root编辑 vi /etc/sudoers
在文件的如下位置，为hadoop添加一行即可
root    ALL=(ALL)       ALL
hadoop  ALL=(ALL)       ALL

然后，hadoop用户就可以用sudo来执行系统级别的指令
[hadoop@shizhan ~]$ sudo useradd huangxiaoming

```



### 5、系统管理操作

```
*****查看主机名
hostname
****修改主机名(重启后无效)
hostname hadoop

*****修改主机名(重启后永久生效)
vi /ect/sysconfig/network
****修改IP(重启后无效)
ifconfig eth0 192.168.12.22

****修改IP(重启后永久生效)
vi /etc/sysconfig/network-scripts/ifcfg-eth0


mount ****  挂载外部存储设备到文件系统中
mkdir   /mnt/cdrom      创建一个目录，用来挂载
mount -t iso9660 -o ro /dev/cdrom /mnt/cdrom/     将设备/dev/cdrom挂载到 挂载点 ：  /mnt/cdrom中

*****umount
umount /mnt/cdrom


*****统计文件或文件夹的大小
du -sh  /mnt/cdrom/Packages
df -h    查看磁盘的空间
****关机
halt
****重启
reboot


******配置主机之间的免密ssh登陆
假如 A  要登陆  B
在A上操作：
%%首先生成密钥对
ssh-keygen   (提示时，直接回车即可)
%%再将A自己的公钥拷贝并追加到B的授权列表文件authorized_keys中
ssh-copy-id   B



******后台服务管理
service network status   查看指定服务的状态
service network stop     停止指定服务
service network start    启动指定服务
service network restart  重启指定服务
service --status-all  查看系统中所有的后台服务

设置后台服务的自启配置
chkconfig   查看所有服务器自启配置
chkconfig iptables off   关掉指定服务的自动启动
chkconfig iptables on   开启指定服务的自动启动


*****系统启动级别管理
vi  /etc/inittab

# Default runlevel. The runlevels used are:
#   0 - halt (Do NOT set initdefault to this)
#   1 - Single user mode
#   2 - Multiuser, without NFS (The same as 3, if you do not have networking)
#   3 - Full multiuser mode
#   4 - unused
#   5 - X11
#   6 - reboot (Do NOT set initdefault to this)
#
id:3:initdefault:

```

