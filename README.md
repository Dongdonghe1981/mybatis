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
### mybatis参数处理	
1.	单个参数：<br/>mybatis不会做特殊处理，#{参数名}:就可以取出参数值
2.	多个参数：<br/>mybatis会做特殊处理，多个参数会被封装成一个map, 
        + Key：param1…paramN,（或者arg1..argN也可以）
        + Value:传入参数的值
		\#{}就是从map里获取指定的key值。
3.	使用命名参数：
<br/>明确指定封装参数值时map的key
		+ key：使用@Param注解指定的值
		+ value：参数值
>selectUserByIdAndUserName(@Param("id") Integer id, 
@Param("userName") String userName);
4.	POJO<br/>如果多个参数正好是POJO，可以直接传入。
/#{属性名}：取出传入的POJO的属性值
5.	Map<br/>如果没有对应的POJO，可以传入Map
6.	TO<br/>如果方法频繁掉用，可以定义TO（Transfer Object）数据传输对象

##### 例：
>Public Employee getEmp(@Param("id")Integer id,String lastName)
* 参数处理：id => #{id/param1} lastName=>#{param2}
>Public Employee getEmp(Integer id,@Param(“e”)Employee emp)
* 参数处理：id=>#{param1} lastName=>#{param2.lastName/e.lastName}
如果是Collection(List,Set)类型或者是数组，把传入的list或者数组封装在map中。
<br/>Key:Collection(collection)，key(list),数组(array)
>Public Employee getEmp(List\<Integer\> ids)
* 参数处理：取出第一个id的值：#{list[0]}

##### 码分析：mybatis如何处理参数
* ParamNameResolver解析参数封装map
1. 获取每个标了param注解的参数的@Param的值，赋值给name’
2. 每次解析一个参数给map中保存信息（key：参数索引，value：name的值）
    <br/>Name的值:标注了param注解的值，
	没有标注的话
    + 全局配置：useActualParamName(jdk1.8),name=参数名
    + Name=map.size()相当于当前元素的索引{0=id，1=lastName}
##### 参数的获取
\#{}可以获取map中的值或者pojo对象属性的值，以预编译的形式，将参数设置到sql中，PrepareStatement
>select * from users where id = #{id} and userName = #{userName}
><br/>=> select * from users where id = ? and userName = ?

${}参数的值直接拼装在sql语句中，会有安全问题
>select * from users where id = ${id} and userName = #{userName}
><br/>=>select * from users where id = 2 and userName = ?

大多数情况下，使用\#{}
动态生成表明的时候，使用${}
>select *from ${year}_salary <br/>=> Select *from 2016_salary

###### \#{}：更丰富的用法，规定参数的一些规则
在我们的数据为null的时候，有些数据库不能识别mybatis对null的默认处理，比如Oracle
<br/>JdbcType OTHER:无效的类型，因为mybatis对所有的null都映射的是原生Jdbc的OTHER类型
<br/>由于全局配置中：jdbcTypeForNull=OTHER，oracle不支持
<br/> ※解决方法
+ 1.\#{email,jdbcType=NULL}
+ 2.jdbcTypeForNull=NULL (mysql和oracle都支持)
    mybatis-config.xml <setting name=”jdbcTypeForNull” value=”NULL”/>

    
