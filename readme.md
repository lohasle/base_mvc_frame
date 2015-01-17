##基础开发框架
>mybatis3 spring4 springMVC为基础框架。
>
		>1. 简单纯粹的MVC框架集
		>2. 使用mybatis Maven 插件生成sql和实体
		>3. 生成sql集成分页操作
		>4. 前端使用bootsharp

----
>简单、实用、高效

>使用方法：
>
>1.创建数据库s4m3
>
>2.首页doc文件夹中的jar安装到maven仓库中
>mvn install:install-file -D file=D:\workspace\base_mvc_frame\base_mvc_frame\doc\lib\mybatis-page-plugin-1.0-SNAPSHOT.jar -D groupId=org.duoku.groom -D artifactId=mybatis-page-plugin -D version=1.0 -D packaging=jar
>
>3.安装完成之后，配置dao模块中generator自动化SQL配置文件。
>
>4.打开项目，调试运行，maven 项目你懂的。