### Dubbo环境搭建

1. 准备jdk（1.8）

   1. [jdk官网](https://www.oracle.com/java/technologies/downloads/) 下载jdk1.8
   2. jdk安装完成
   3. 配置环境变量
      1. 在新建页面输入"变量名"位 JAVA_HOME,输入“变量值”为你安装的JDK地址
      2. 在系统变量区域，选择"新建按钮"输入"变量名"为CLASSPATH,输入"变量值"为.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar
   4. 在命令提示符中输入java -version 查询当前安装的jdk版本

2. 准备maven（3.2+）

   1. [maven官网](https://maven.apache.org/download.cgi) 下载maven

   2. 解压下载的文件

   3. 配置环境变量

      1. M2_HOME  D:\apache\maven\apache-maven-3.3.9
      2. MAVEN_HOME  D:\apache\maven\apache-maven-3.3.9
      3. path %M2_HOME%\bin
      4. path  %MAVEN_HOME%\bin

   4. 然后**win+R** 运行cmd 输入 **mvn -version**

   5. 修改**setting.xml**文件

      1. 配置**阿里云镜像源**

         ```xml
         <mirrors>
             <mirror>
               <id>alimaven</id>
               <name>aliyun maven</name>
               <url>https://maven.aliyun.com/repository/public</url>
               <mirrorOf>central</mirrorOf>        
             </mirror>
           </mirrors>
         ```

      2. 配置EDAS（使用HSF时配置）（可选）

         ```xml
         <profile>
                 <id>nexus</id>
                 <repositories>
                     <repository>
                         <id>central</id>
                         <url>https://repo1.maven.org/maven2</url>
                         <releases>
                             <enabled>true</enabled>
                         </releases>
                         <snapshots>
                             <enabled>true</enabled>
                         </snapshots>
                     </repository>
                 </repositories>
                 <pluginRepositories>
                     <pluginRepository>
                         <id>central</id>
                         <url>https://repo1.maven.org/maven2</url>
                         <releases>
                             <enabled>true</enabled>
                         </releases>
                         <snapshots>
                             <enabled>true</enabled>
                         </snapshots>
                     </pluginRepository>
                 </pluginRepositories>
             </profile>
             <profile>
                 <id>edas.oss.repo</id>
                 <repositories>
                     <repository>
                         <id>edas-oss-central</id>
                         <name>taobao mirror central</name>
                         <url>http://edas-public.oss-cn-hangzhou.aliyuncs.com/repository</url>
                         <snapshots>
                             <enabled>true</enabled>
                         </snapshots>
                         <releases>
                             <enabled>true</enabled>
                         </releases>
                     </repository>
                     </repositories>
                 <pluginRepositories>
                     <pluginRepository>
                         <id>edas-oss-plugin-central</id>
                         <url>http://edas-public.oss-cn-hangzhou.aliyuncs.com/repository</url>
                         <snapshots>
                             <enabled>true</enabled>
                         </snapshots>
                         <releases>
                             <enabled>true</enabled>
                         </releases>
                     </pluginRepository>
                 </pluginRepositories>
             </profile>
           </profiles>
         	<activeProfiles>
         		<activeProfile>nexus</activeProfile>
         		<activeProfile>edas.oss.repo</activeProfile>
         	</activeProfiles>
         ```

      3. 修改**本地仓库**的位置（可选）

         ```xml
         <localRepository>文件夹路径</localRepository>
         ```

3. zookeeper

   1. [zookeeper官网](https://zookeeper.apache.org/releases.html#download) 下载zookeeper

   2. 解压 zookeeper-3.4.10.tar.gz，进入解压后conf目录下，复制zoo_sample.cfg文件，重命名为zoo.cfg即可

   3. 在解压目录下创建data和log文件夹，在zoo.cfg中修改添加

      ```
      dataDir=D:\apache\zookeeper\zookeeper-3.4.10\data
      dataLogDir=D:\apache\zookeeper\zookeeper-3.4.10\log
      ```

   4. 进入解压后bin目录，点击zkServer.cmd即可，端口号则是之前默认设置的2181。**注意 ：这个窗口不要关闭！让注册中心服务一直运行。**

4. tomcat8

   1. [tomcat官网](https://tomcat.apache.org/download-80.cgi) 下载tomcat8.5

   2. 解压缩**apache-tomcat-8.5.51-windows-x64.zip**文件

   3. 配置环境变量

      | 变量          | 值                      |
      | ------------- | ----------------------- |
      | CATALINA_HOME | C:\apache-tomcat-8.5.51 |
      | CATALINA_BASE | C:\apache-tomcat-8.5.51 |
      | path          | %CATALINA_HOME%\lib     |
      | path          | %CATALINA_HOME%\bin     |

   4. **win+R键**打开CMD命令行界面，输入**service.bat install**，回车键执行

   5. 命令行界面输入**startup.bat**启动tomcat

   6. 端口号修改在server.xml中修改，默认为8080

5. dubbo

   1. [下载源码地址](https://github.com/apache/incubator-dubbo/tree/dubbo-2.5.8)

   2. 解压后，打开cmd进入dubbo-admin目录下，如我解压的目录为：D:\Test\dubbo-master\dubbo-admin，在cmd下输入命令：mvn clean install

   3. 在target目录下生成dubbo-admin-2.5.8.war，然后将该war包放入tomcat的webapps目录下，启动tomcat， 访问localhost:[端口]/dubbo-admin-2.5.8，输入默认的用户名和密码：root/root

      例如我访问的地址为：http://localhost:8088/dubbo-admin-2.5.8/

   4. 要是你的zookeeper注册中心也是本地起的服务，那么就可以不用修改任何东西，要是不是本地起的zookeeper注册中心，那么你需要修改war包里面的dubbo配置文件(可以用好压打开war包，修改WEB-INF目录下的dubbo.properties文件)

      ```
      dubbo.registry.address=zookeeper://127.0.0.1:2181
      ```

      

### Dubbo项目实战

简单的理解

提供者就是service层，消费者就是controller层，有几个service层的类就有几个服务、和几个提供者；而消费者的数目不是看有几个controller层的类就有几个消费者数目的，消费者是看controller层使用了几个service层的类来看的。比如说，service层有两个类，controller层有一个类，那么在controller层的这个类里使用到了service层的两个类，那么，这里就有两个消费者，要是一个也没使用过，那就不存在消费者。

1. 创建一个maven父项目shop，并创建三个子项目shop-interface, shop-provider, shop-consumer

![](https://i.loli.net/2021/11/05/YXt42MI3Dpfn9WE.png)

2. 创建maven父子项目（以eclipse为例子）

   1. eclipse中项目列表**右键->New->Project->Maven Project**, 可自定义创建位置或默认创建位置

      ![](https://i.loli.net/2021/11/05/XajoMptzVZuDhCg.png)

   2. 点击**Next**，选择**quickstart**版本

      ![image-20211105133004438](https://i.loli.net/2021/11/05/mST4FjsaGQdkLci.png)

   3. 点击**Next**, 设置Group Id和Artifact Id（由于之前已经创建好了shop，所以这里有错误提示）

      ![image-20211105133608127](https://i.loli.net/2021/11/05/rtsdXInRbq2wZG5.png)

   4. 点击**finish**创建完成

   5. 创建shop的子项目，在创建好的shop项目上**右键->New->Porject->Maven Module**

      ![image-20211105133826472](https://i.loli.net/2021/11/05/pAKuSWBy9qDkc6V.png)

   6. 点击**Next**, 输入**module name**

      ![image-20211105133930377](https://i.loli.net/2021/11/05/reDEM6q38BaVSTL.png)

   7. 点击**Next**，选择**quickstart**版本

      ![image-20211105133004438](https://i.loli.net/2021/11/05/mST4FjsaGQdkLci.png)

   8. 点击**Next**, 输入**package**与父项目保持一致

      ![image-20211105140555903](https://i.loli.net/2021/11/05/ysO5HX6V2mLQgrl.png)

   9. 重复上述步骤建立好所有子项目

3. 公共API。 shop-interface建立

   创建好接口和实例类

   ![image-20211105142011295](https://i.loli.net/2021/11/05/Gy9VULzj21MnOQ4.png)

4. 服务者shop-provider建立

   ![image-20211105141835615](https://i.loli.net/2021/11/05/lp5tWrZ2q79HEg1.png)

   pom.xml

   ```xml
   		<!-- 引入公共API,以实现其接口 -->
           <dependency>
               <groupId>com.zang</groupId>
               <artifactId>gmall-interface</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
           <!-- 引入spring-boot-starter以及dubbo和curator的依赖 -->
           <dependency>
               <groupId>com.alibaba.boot</groupId>
               <artifactId>dubbo-spring-boot-starter</artifactId>
               <version>0.2.0</version>
           </dependency>
           <!-- Spring Boot相关依赖 -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter</artifactId>
           </dependency>
   
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
   ```

   需要注意的是，根据jdk和Spring Boot版本的不同，dubbo-spring-boot-starter的版本需要有根据的选择

   ![img](https://img2018.cnblogs.com/blog/1001990/201809/1001990-20180924172720839-1422760561.png)

   dubbo提供了@Service注解，可将类声明为提供方，省去了大量配置的麻烦

   ```java
   import com.alibaba.dubbo.config.annotation.Service;
   import com.zang.gmall.bean.UserAddress;
   import com.zang.gmall.service.UserService;
   import org.springframework.stereotype.Component;
   
   import java.util.Arrays;
   import java.util.List;
   
   @Service   //属于Dubbo的@Service注解，非Spring  作用：暴露服务
   @Component
   public class UserServiceImpl implements UserService {
   
       @Override
       public List<UserAddress> getUserAddressList(String userId) {
       //省略
   }}
   ```

   通过属性配置的方式设置application.properties 

   ```
   #当前服务/应用的名字
   dubbo.application.name=user-service-provider
   #注册中心的协议和地址
   dubbo.registry.protocol=zookeeper
   dubbo.registry.address=127.0.0.1:2181
   #通信规则（通信协议和接口）
   dubbo.protocol.name=dubbo
   dubbo.protocol.port=20880
   #连接监控中心
   dubbo.monitor.protocol=registry
   #开启包扫描，可替代 @EnableDubbo 注解
   ##dubbo.scan.base-packages=com.zang.gmall
   ```

   springboot容器根据配置启动服务提供方，这里需要添加 @EnableDubbo 注解

   ```java
   import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   
   // 开启基于注解的dubbo功能（主要是包扫描@DubboComponentScan）
   // 也可以在配置文件中使用dubbo.scan.base-package来替代   @EnableDubbo
   @EnableDubbo
   
   @SpringBootApplication
   public class UserServiceProviderApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(UserServiceProviderApplication.class, args);
       }
   }
   ```

   **执行Application类启动服务**

5. 消费者shop-consumer建立

   ![image-20211105142115679](https://i.loli.net/2021/11/05/Mkn8HQy2DpmUlVc.png)

   引入和服务提供方同样的依赖，除此之外，添加springboot web模块的依赖。

   ```XML
    		<!-- springboot web模块 -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
   ```

   dubbo提供了@Reference注解，可替换@Autowired注解，用于引入远程服务

   ```JAVA
   import com.alibaba.dubbo.config.annotation.Reference;
   import com.zang.gmall.bean.UserAddress;
   import com.zang.gmall.service.OrderService;
   import com.zang.gmall.service.UserService;
   import org.springframework.stereotype.Service;
   
   import java.util.List;
   
   @Service
   public class OrderServiceImpl implements OrderService {
   
       @Reference
       UserService userService;
   
       @Override
       public List<UserAddress> initOrder(String userId) {
   　　//代码省略
      }}
   ```

   配置文件application.properties

   ```
   #避免和监控中心端口冲突，设为8081端口访问
   server.port=8081  
   
   dubbo.application.name=order-service-consumer
   dubbo.registry.address=zookeeper://127.0.0.1:2181
   dubbo.monitor.protocol=registry
   ```

   启动类同样加上@EnableDubbo注解

   ```JAVA
   import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   
   @EnableDubbo
   @SpringBootApplication
   public class OrderServiceConsumerApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(OrderServiceConsumerApplication.class, args);
       }
   }
   ```

   为查看调用是否成功，新增控制层用于访问

   ```JAVA
   import com.zang.gmall.bean.UserAddress;
   import com.zang.gmall.service.OrderService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.RestController;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RequestParam;
   import org.springframework.web.bind.annotation.ResponseBody;
   
   import java.util.List;
   
   @RestController
   public class OrderController {
   
       @Autowired
       OrderService orderService;
   
       @ResponseBody   //以json格式返回
       @RequestMapping("/initOrder")
       public List<UserAddress> initOrder(@RequestParam("uid") String userId){
   
          return orderService.initOrder(userId);
       }
   
   }
   ```

3. 测试

   启动消费方，在浏览器访问

   http://localhost:8081/initOrder?uid=1

   ![image-20211105144203175](https://i.loli.net/2021/11/05/gNRDeFv36cT4m5P.png)

![image-20211105144153167](https://i.loli.net/2021/11/05/di9IqAKPzM8oXTL.png)

参考：https://www.cnblogs.com/zjfjava/p/9696086.html



#### 切换zookeeper为EDAS

pom.xml中加入EDAS依赖

```xml
<dependency>
         <groupId>com.alibaba.edas</groupId>
         <artifactId>edas-dubbo-extension</artifactId>
         <version>1.0.1</version>
     </dependency>
```

application.properties

修改注册中心地址

```
dubbo.registry.address=edas://127.0.0.1:8080
dubbo.scan.basePackages=me.gary.edas.boot
```



























​		

