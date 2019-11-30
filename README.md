# mybatis

* [mybatis源码](https://github.com/mybatis/mybatis-3/)
* [mybatis帮助文档](https://mybatis.org/mybatis-3/zh/index.html)

### HelloWorld
1.	接口式编程
* 原生：		Dao ===> DaoImpl
* Mybatis：	Mapper===>xxMapper.xml	

2.	SqlSession代表跟数据库的一次会话，用完必须关闭
3.	SqlSession和connection一样都是线程安全的，每次使用都应该获取新的对象
4.	Mapper接口没有实现类，但是myBatis会为这个接口生成一个代理对象将接口跟xml进行绑定
5.	两个重要的配置文件
    * mybatis的全局配置文件，包含数据库连接池信息，事务管理器信息等
    * sql映射文件，保存每一个sql语句的映射信息

