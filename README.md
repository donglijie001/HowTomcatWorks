# HowTomcatWorks

## 介绍
HowTomcatWorks配套代码，[参考链接](https://blog.csdn.net/qq_38182963/article/details/78660767)
## 第一章
运行第一章代码的时候，使用火狐浏览器测试，是可以的，使用谷歌是不行的。
直接输入 http://localhost:8080/index.html

## 第二章

第二章部分，由于需要用到servlet，所以需要导入servlet的依赖，而且我使用的idea版本是社区版，并不能配置tomcat，但是后来我发现实际上我并不需要配置tomcat，只需要导入servlet的依赖就可以了。[参考链接](https://blog.csdn.net/Shaun_Guo/article/details/80250774)。直接导入项目lib文件夹下的所有jar包就可以。

> 由于项目的gitignore文件设置了忽略了class文件，因此，提交到代码仓库里的文件是没有class文件的，但是由于从第二章开始，使用了类加载器来加载字节码文件，所以下载下来需要用javac命令变异webroot文件夹下的类文件，直接在webroot文件夹下输入：javac -classpath ./../lib/servlet.jar PrimitiveServlet.java，只需要改变最后面的java文件名就可以了。

## 第三章

第三章代码的复杂度比第二章直接上升了一大截。这里用到的`org.apache.catalina`包下的类，我直接放到了orgModule里了，在接下来的模块里，如果需要使用这里的类，直接导入模块依赖就可以了。而且由于每一章都在不同的模块，所以每一个模块在创建的时候都需要导入lib文件夹下的依赖。

## 第七章

只是简单的介绍了tomcat的日志接口，并使用了其中的一个日志实现类filelogger，代码主要还是第六章的代码，就是在第六章的基础上加上一点日志记录的相关代码