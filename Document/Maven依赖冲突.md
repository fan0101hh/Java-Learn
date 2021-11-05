#### Maven 依赖冲突

[参考](https://www.cnblogs.com/pjhaymy/p/13961725.html)

##### 依赖传递

在maven中依赖是可以传递的，比如我们有A,B,C三个项目，其中A依赖B,B依赖C,由递推可知A依赖C

![img](https://i.loli.net/2021/11/05/ZfnCQ3LwtWeP8jk.png)

##### 举例

比如我们在web项目中导入spring-webmvc的jar包，即我们的项目依赖了spring-webmvc，其实spring-webmvc还依赖其他jar包，比如sping-aop、spring-beans，所以我们的web项目也间接依赖了sping-aop、spring-beans

![img](https://i.loli.net/2021/11/05/HvD3nSh1PqQx7MT.png)

##### 什么是依赖冲突

这是由于依赖传递现象的存在

比如spring-webmvc 依赖 spirng-beans-4.2.4，而spring-aop 依赖  spring-beans-5.0.2，但是发现 spirng-beans-4.2.4 加入到了工程中，而我们希望  spring-beans-5.0.2 加入工程。这就造成了依赖冲突。

![在这里插入图片描述](https://i.loli.net/2021/11/05/dhBkT5pVWuLZNvn.png)

在我们的开发中我们当然啦不允许这种情况的出现，我们要使用什么版本，他就必须使用什么版本

##### 解决方案

1. 使用maven提供的依赖调解原则
    第一声明者优先原则
    路径近者优先原则
2. 排除依赖
3. 锁定版本

###### 使用maven提供的依赖调解原则

​	**第一声明者优先原则**

​	在 pom 文件中定义依赖，以先声明的依赖为准。其实就是根据坐标导入的顺序来确定最终使用哪个传递过来的依赖。

​	![在这里插入图片描述](https://i.loli.net/2021/11/05/qfEg8FjkrNHZJcL.png)

​	通过上图可以看到，spring-aop和spring-webmvc都传递过来了spring-beans，但是因为spring-aop在前面，所以最终使用的spring-beans是由spring-aop	传递过来的，而spring-webmvc传递过来的spring-beans则被忽略了

​	**路径近者优先原则**

​	在 pom 文件定义依赖，以路径近者为准。
 	还是上述情况，spring-aop 和 spring-webmvc 都会传递过来 spirng-beans，那如果直接把  spring-beans 的依赖直接写到 pom 文件中，那么项目就不会再使用其他依赖传递来的 spring-beans，因为自己直接在 pom 中定义 spring-beans要比其他依赖传递过来的路径要近

###### 排除依赖

​	可以使用exclusions标签将传递过来的依赖排除出去

​	**没有使用的时候**

​	![在这里插入图片描述](https://i.loli.net/2021/11/05/aZ4loP5FhriBOmG.png)

​	**使用之后**

​	![在这里插入图片描述](https://i.loli.net/2021/11/05/VxyXqPpkA8orHOj.png)

###### 版本锁定

​	**使用dependencyManagement标签锁定依赖的版本**

​	注意dependencyManagement中指定的仅仅是对应jar包的版本，并不会把指定的jar导入到项目中，要导入对应的jar包还是要使用dependencies标签

​	![在这里插入图片描述](https://i.loli.net/2021/11/05/CdhxUr7IOSAfTKp.png)	

​	**在使用dependence标签的时候不需要再指定版本号了**

​	![在这里插入图片描述](https://i.loli.net/2021/11/05/tBG2Ip3UzgZsJWc.png)

​		![在这里插入图片描述](https://i.loli.net/2021/11/05/KlT253Srm1HuRIe.png)



#### 使用eclipse maven build validate 查看项目的maven冲突

​	配置pom.xml文件

```
<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-enforcer-plugin</artifactId>
				<version>1.3.1</version>
				<executions>
					<execution>
						<id>enforce</id>
						<configuration>
							<rules>
								<DependencyConvergence />
							</rules>
						</configuration>
						<goals>
							<goal>enforce</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
```

![image-20211105150352849](https://i.loli.net/2021/11/05/wVGjoBCKFYl2cp3.png)