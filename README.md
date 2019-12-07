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

### 缓存
#### 一级缓存（本地缓存）和二级缓存（全局缓存）
1.默认情况下，只有一级缓存（SqlSession级别的缓存，也称为本地缓存）开启
  与数据库`同一次会话`期间查询到的数据，会放到本地缓存中，以后的数据如果需要获取相同的数据，
  直接可以从缓存中获取。一级缓存是存储在sqlSession的一个Map中  
  一级缓存失效情况  
  +  SqlSession不同
  +  SqlSession相同，查询条件不同
  +  SqlSession相同，先查getAll，再查getOne
  +  SqlSession相同，两次之间执行了增删改操作（即使不是对缓存中相同的记录）
  （因为这次操作，可能修改缓存数据）  
  +  缓存数据清空openSession.clearCache();
   
2.二级缓存需要手动开启和配置，它是基于NameSpace级别的缓存  
  工作机制
  +  一个会话：查询一条数据，这个数据就会被放到当前会话的一级缓存中，如果会话关闭，一级缓存
  中的数据会被保存到二级缓存中，新的会话查询时，就可以参照二级缓存  
  
  步骤
  1. 开启全局二级缓存配置<setting name="cacheEnabled" value="true"/>
  2. 在各个mapper.xml文件中配置  
  ```
  <cache eviction="" flushInterval="" readOnly="" size="" type=""></cache>
  ```  
  +  eviction：缓存的回收策略（默认是LRU）  
        +  LRU：最近最少使用的，移除最长时间不被使用的对象
        +  FIFO:先进先出，按照对象进入缓存的顺序来移除
        +  SOFT：软引用，移除基于垃圾回收器状态和软引用规则的对象
        +  WEAK：弱引用，更积极的移除基于垃圾回收器状态和软引用规则的对象
    
  +  flushInterval：缓存刷新间隔，缓存多长时间清空一次，默认不清空，设置一个毫秒值
  +  readOnly：缓存是否只读，默认false
        +  true：只读，mybatis认为所有从缓存中获取数据的操作都是只读操作，不会修改数据，为了加快获取数据，不安全，速度快
        +  false：非只读，mybatis认为获取的数据可能会被修改。mybatis会利用反序列化克隆一份新的数据给用户。安全，速度慢
  +  size：缓存中存放多少元素
  +  type：指定自定义缓存的全类名，实现Cache接口的类  
  
  3. POJO实现反序列化接口  
`查询的数据都会默认放到一级缓存中，只有会话关闭，一级缓存中的数据，才会转移到二级缓存中`
  
3.为了提高扩展性，MyBatis定义了缓存接口Cache。可以通过实现Cache接口来自定义二级缓存

#### 和缓存相关的配置
1. 全局`cacheEnabled=false`:关闭二级缓存，不影响一级缓存
2. 每个select都有一个`useCache`,默认是true  
   false：关闭二级缓存，不影响一级缓存
3. 每个增删改标签的flushCache="true",操作完成后清除一级和二级缓存，包括`<select>`
4. `sqlSession.clearCache()`只清除一级缓存，不清除二级缓存。
5.  全局localCacheScope,一级（本地）缓存作用域，当前会话的所有数据保存在会话缓存中。  
   STATEMENT：可以禁用一级缓存。
#### 缓存的使用顺序
先匹配二级缓存，在匹配一级缓存，如果都没匹配上，再查询DB
