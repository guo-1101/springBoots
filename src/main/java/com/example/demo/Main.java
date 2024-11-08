package com.example.demo;

import org.springframework.validation.annotation.Validated;

public class Main {
    public static void main(String[] args) {


//        SpringBoot

//        SpringBoot只是用于快速创建SSM项目的脚手架，就像是个外壳一样，离开了SSM核心内容就是个空壳。
//        SpringBoot为大量的第三方库添加了支持，用最少的配置快速构建你想要的项目。它可以自动进行配置，
//        我们只需要导入对应的启动器（starter）依赖即可。它提供了一站式的开发体验，能够大幅度提高我们的开发效率。

//        SpringBoot功能有哪些：
//        能够创建独立的Spring应用程序
//        内嵌Tomcat、Jetty或Undertow服务器（无需单独部署WAR包，打包成Jar本身就是一个可以运行的应用程序）
//        提供一站式的“starter”依赖项，以简化Maven配置（需要整合什么框架，直接导对应框架的starter依赖）
//        自动配置Spring和第三方库
//        提供生产环境下相关功能，如指标、运行状况检查和外部化配置
//        没有任何代码生成，也不需要任何XML配置

//        SpringBoot内置了一个Tomcat服务器，要求版本最低为10，并且Java版本也要求为17和Spring6。
//        SpringBoot支持自动包扫描。只要把一个类注册为Bean就可以直接通过@Resource获取，不用在去配置类配置了。


//        一、创建项目

//        一、极速创建项目
//        在过去，创建一个SSM项目，需要先导入各种依赖，进行大量的配置。
//        而现在，使用SpringBoot，只需要进行少量配置就能快速生成一个SpringBoot项目模版：https://start.spring.io/

//        步1.首先在新建项目阶段，选择 Spring Initializr 类型
//        步2.接着选择项目需要使用的模块，这里简单选择两个依赖：GraaIVM Native Support 和 Lombok。
//        如果一开始不清楚自己需要哪些模块，也可以后续自己手动添加对应模块的starter依赖，使用非常简单。
//        步3.删除.mvn、HELP.md、mvnw、mvnw.cmd文件
//        步4.在默认情况下，需要在resources目录下创建两个目录，名字必须是这个：
//        templates - 所有模版文件都存放在这里
//        static - 所有静态资源都存放在这里
//        只需要按照上面的样子放入前端模版，就可以正常使用模版引擎了，不需要任何的配置。
//        步5.项目生成之后，可以看到Spring相关的依赖已经全部自动导入，并且也自动创建了一个主类用于运行SpringBoot项目，
//        可以一键启动我们的SpringBoot项目，只不过由于没有添加任何有用的模块，也没有编写什么操作，因此启动之后项目就直接停止了。

//        二、常用模块快速整合 --对应 TestController User WebConfiguration SecurityConfiguration--
//        SpringBoot的核心思想就是约定大于配置，能在一开始默认的就直接默认，不用自己来进行配置，只需要配置某些特殊的部分即可。

//        所有的SpringBoot依赖都是以starter的形式命名的，之后需要导入其他模块也是导入spring-boot-starter-xxxx这种名称格式的依赖。

//        步1.首先还是从SpringMvc相关依赖开始。SpringBoot为我们提供了包含内置Tomcat服务器的Web模块，只需要导入依赖就能直接运行服务器
//        步2.不需要进行任何配置，直接点击启动。到目前为止仅仅是导入了一个依赖，现在就可以直接启动Web服务器并访问这个网站。
//        步3.SpringBoot支持自动包扫描，不需要编写任何配置，直接在任意路径（但是不能跑到主类所在包外面去了）下创建的组件都可以生效。
//        重启之后，可以直接就能访问到，而这期间只是创建了对应的Controller却没有进行任何配置。
//        步4.包括一个对象现在也可以直接以JSON形式返回给客户端，无需任何配置。
//        最后浏览器能够直接得到application/json的响应数据，这都是SpringBoot对应的start帮助我们自动将处理JSON数据的Converter
//        进行了配置。不过SpringBoot官方默认使用的是Jackson和Gson的HttpMessageConverter来进行配置，不是使用的FastJSON框架。
//        步5.如果需要像之前一样添加WebMvc的配置类，方法是一样的，直接创建WebConfiguration即可
//        步6.SpringSecurity框架如何进行整合，只需要直接导入依赖即可。
//        访问网站就可以看到登录界面了。在启动时配置了一个随机密码的用户，密码直接展示在启动日志中，而默认用户名称为user可以直接登录。
//        如果要进行额外配置，只需要直接添加配置类SecurityConfiguration即可
//        步7.也可以快速整合之前使用的模版引擎，比如Thymeleaf框架，直接上对应的Starter依赖即可
//        不需要在controller中写任何内容，它默认会将index.html作为首页文件，直接访问服务器 根目录/ 地址就能展示首页了
//        步8.最后再来看看Mybatis如何进行整合，同样只需要一个starter即可，顺便把MySQL的驱动加上。

//        Tomcat在8080端口完成了初始化: Tomcat initialized with port 80 (http)
//        开启服务                  : Starting service [Tomcat]
//        启动Servlet引擎           : Starting Servlet engine: [Apache Tomcat/10.1.16]
//        启动内嵌的Tomcat服务器      : Initializing Spring embedded WebApplicationContext
//        空                       : Root WebApplicationContext: initialization completed in 1447 ms
//        空                       : Tomcat started on port 80 (http) with context path ''
//        启动成功                  : Started DemoApplication in 5.583 seconds (process running for 6.363)

//        三、自定义运行器 --对应 TestRunner--
//        在项目中，我们需要在项目启动完成之后，紧接着执行一段代码。
//        可以编写自定义的ApplicationRunner来解决，它会在项目启动完成后执行。

//        也可以使用CommandLineRunner，它也支持使用@Order或是实现Ordered接口来支持优先级执行。

//        四、配置文件介绍 --对应 TestController User UserMapper--
//        在application.properties中进行编写配置，它是整个SpringBoot的配置文件。
//        配置文件还有yml、yaml格式。每一级目录都是通过缩进（不能使用Tab，只能使用空格）区分，并且键和值之间需要添加冒号+空格来表示。
//        一级目录:
//         二级目录:
//          三级目录: 值
//          三级目录List:
//          - 元素1
//          - 元素2

//        步9.可以添加自定义的配置，可以直接在程序中通过@Value来访问到（跟Spring是一样的）
//        步10.在SpringBoot中使用Mybatis。在配置类上添加@MapperScan("com.example.mapper")注解即可，跟之前的使用方法是一样的。
//        也可以直接为需要注册为Mapper的接口添加@Mapper注解，来表示这个接口作为Mapper使用。

//        五、轻松打包运行
//        步1.打包SpringBoot项目，SpringBoot提供了一个非常便捷的打包插件，能够直接将我们的项目打包成一个jar包，
//        然后使用java命令直接运行，直接点击Maven中的package。双击package
//        步2.点击之后项目会自动打包构建。会在target目录下出现一个打包好的jar文件：
//        springBoot-0.0.1-SNAPSHOT.jar
//        我们可以直接在命令行中运行这个程序，在CMD中进入到target目录，然后输入：
//        -java -jar springBoot-0.0.1-SNAPSHOT.jar
//        这样就可以直接运行了

//        把War包丢在Tomcat里面的webapps里面，访问路径要带上http://localhost/springBoot-0.0.1-SNAPSHOT/。
//        怎么才能像之前一样在我们的Tomcat服务器中运行呢？我们也可以将其打包为War包的形式部署到我们自己环境中的Tomcat
//        服务器或是其他任何支持Servlet的服务器中。
//        步3.首先需要排除掉spring-boot-starter-web中自带的Tomcat服务器依赖。
//        然后自行添加Servlet依赖。最后将打包方式修改为war包。接着需要修改主类，将其继承SpringBoot需要的Initializer。
//        最后运行Maven的package指令就可以打包为war包了。直接将其部署到Tomcat服务器中，接着启动服务器就能正常访问了。
//        步4.如果需要在IDEA中进行调试运行，我们需要像之前一样配置一个Tomcat运行环境，这样就可以跟之前一样使用外部Tomcat服务器了。
//        编辑配置... --> + --> Tomcat 服务器 --> 本地 --> 部署 + 工件... 选择springBoot:war exploded 编辑应用程序上下文

//        SpringBoot项目除了打包为传统的Jar包基于JVM运行之外，也可以将其直接编译为操作系统原生的程序来进行使用

//        JAVA项目打包（jar war）基于JVM运行
//        C C# C++编译成操作系统原生的直接运行的，运行效率快
//        Java基于Java虚拟机去跑的，不如C C# C++原生编译的性能快


//        二、日志系统介绍

//        一、日志门面和日志实现
//        日志实现，之前JUL实际上就是一种日志实现，我们可以直接使用JUL为我们提供的日志框架来规范化打印日志。
//        日志门面，如Slf4j，是把不同的日志系统的实现进行了具体的抽象化，只提供了统一的日志使用接口，使用时只需要按照其提供
//        的接口方法进行调用即可，由于它只是一个接口，并不是一个具体的可以直接单独使用的日志框架，所以最终日志的格式、记录级别、
//        输出方式等都要通过接口绑定的具体的日志系统来实现，这些具体的日志系统就有log4j、logback、java.util.logging等，
//        它们才实现了具体的日志系统的功能。

//        不同的框架使用了不同的日志框架，现在希望所有的框架一律使用日志门面（Slf4j）进行日志打印，这时该怎么去解决？
//        只保留不同日志框架的接口和类定义等关键信息，而将实现全部定向为Slf4j调用。

//        SpringBoot为了统一日志框架的使用，做了这些事情：
//        直接将其他依赖以前的日志框架剔除
//        导入对应日志框架的Slf4j中间包
//        导入自己官方指定的日志实现，并作为Slf4j的日志实现层

//        二、打印项目日志信息 --对应 TestController--
//        SpringBoot使用的是Slf4j作为日志门面，Logback（Logback 是log4j 框架的作者开发的新一代日志框架，
//        它效率更高、能够适应诸多的运行环境，同时天然支持SLF4J）作为日志实现。此依赖已经被包含了。

//        三、配置Logback日志 --对应 logback-spring.xml TestController--
//        步1.Logback实现定制化，SpringBoot推荐将配置文件名称命名为logback-spring.xml表示这是SpringBoot下Logback专用的配置文件。
//        步2.直接导入并使用SpringBoot为我们预设好的日志格式。在外部库
//        Maven：org.springframework.boot：spring-boot：3.2.0 org.springframework.boot/logging/logback/defaults.xml
//        中已经帮我们把日志的输出格式定义好了，我们只需要设置对应的appender即可。
//        步3.导入后，利用预设的日志格式创建一个控制台日志打印。配置完成后，控制台已经可以正常打印日志信息了。
//        步4.接着开启文件打印，只需要配置一个对应的Appender即可。配置完成后，我们可以看到日志文件也能自动生成了。

//        MDC机制，Logback内置的日志字段还是比较少，如果我们需要打印有关业务的更多的内容，包括自定义的一些数据，需要借助logback MDC机制，
//        MDC（映射诊断上下文），即将一些运行时的上下文数据通过logback打印出来。此时我们需要借助org.sl4j.MDC类。

//        四、自定义Banner展示 --对应 Banner.txt--
//        Banner部分和日志部分是独立的。
//        在配置文件所在目录下创建一个名为banner.txt的文本文档，

//        还可以使用颜色代码来为文本切换颜色，也可以获取一些常用的变量信息。


//        三、多环境配置 --对应 logback-spring.xml--

//        SpringBoot自带的Logback日志系统也是支持多环境配置的，比如在开发环境下输出日志到控制台到文件，而生产环境下只需要输出到文件即可。

//        如果希望生产环境中不要打包开发环境下的配置文件呢？
//        步1.Maven设置多环境，打包的问题就只能找Maven解决了。
//        步2.接着，需要根据环境的不同，排除其他环境的配置文件。
//        步3.接着，直接将Maven中的environment属性，传递给SpringBoot的配置文件application.yml，在构建时替换为对应的值。
//        这样，根据我们Maven环境的切换，SpringBoot的配置文件也会进行对应的切换。
//        步4.最后我们打开右边的Maven栏目，点击 配置文件 。勾选就可以自由切换了。


//        四、常用框架介绍

//        一、邮件发送模块 --对应 DemoApplicationTests--
//        邮件发送协议比较常用的有两种：
//        SMTP协议（主要用于发送邮件 Simple Mail Transfer Protocol）
//        POP3协议（主要用于接收邮件 Post Office Protocol 3）

//        整个发送/接收流程大致如下：
//        每个邮箱服务器都有一个smtp发送服务器和pop3接收服务器。比如要从QQ邮箱发送邮件到163邮箱：
//        QQ邮箱客户端 == 发送邮件 ==> QQ邮箱的smtp服务器 ==发送到==> 163邮箱的pop3服务器 ==收到一封新邮件==> 163邮箱客户端。

//        而我们如果想要实现给别人发送邮件，那么就需要连接到对应电子邮箱的smtp服务器上，并告知其我们要发送邮件。

//        步1.SpringBoot已经提供了封装好的邮件模块使用，添加依赖
//        步2.需要在配置文件application.yml中告诉SpringBootMail我们的smtp服务器的地址以及你的邮箱账号和密码。
//        以163邮箱 https://mail.163.com 为例，设置 --> POP3/SMTP/IMAP --> 新增授权密码
//        步3.配置完成后，接着来进行一下测试
//        步4.如果需要添加附件等更多功能，可以使用MimeMessageHelper来帮助我们完成

//        一、实现邮件注册功能实战 --对应 TestController User UserMapper index.html--
//        流程：请求验证码 -> 生成验证码（临时有效，注意设定过期时间） -> 用户输入验证码并填写注册信息 -> 验证通过注册成功！

//        二、接口规则校验 --对应 TestController ValidationController User1--
//        SpringBoot为我们提供了很方便的接口校验框架

//        步1.添加spring-boot-starter-validation依赖
//        步2.直接在Controller上使用注解完成全部接口的校验，注解@Validated
//        步3.不过这样会抛出一个异常对用户不友好，这里直接使用之前在SSM阶段中的异常处理Controller来自行处理这类异常
//        步4.在接口是以对象形式接收前端发送的表单数据的时候
//        步5.这里我们修改一下ValidationController的错误处理，对于实体类接收参数的验证，会抛出MethodArgumentNotValidException异常

//        三、Swagger：接口文档生成 --对应 SwaggerConfiguration User1--
//        Swagger的主要功能：
//        支持API自动生成同步的在线文档：使用Swagger后可以直接通过代码生成文档，不再需要自己手动编写接口文档了。
//        提供Web页面在线测试API：Swagger生成的文档还支持在线测试。参数和格式都定好了，直接在界面上输入参数对应的值即可在线测试接口。

//        结合Spring框架（Spring-doc，官网：https://springdoc.org/），Swagger可以很轻松地利用注解以及扫描机制，
//        来快速生成在线文档，以实现当我们项目启动之后，前端开发人员就可以打开Swagger提供的前端页面，查看和测试接口。
//
//        步1.添加Swagger依赖
//        步2.项目启动之后，我们可以直接访问：http://localhost:8080/swagger-ui/index.html，就能看到我们的开发文档了
//        步3.Swagger的UI界面配置接口的一些描述信息。SwaggerConfiguration配置要展示文档信息，只需要一个Bean就能搞定
//        步4.接着为Controller编写API描述信息，在类名称上面添加@Tag注解
//        步5.在类名称上面添加@Tag注解，并填写相关信息，来为当前的Controller设置描述信息。接着我们可以为所有的请求映射配置描述信息：
//        步6.对于实体类，我们也可以编写对应的API接口文档。这样我们就可以在文档中查看实体类简介以及各个属性的介绍了。
//        步7.不过，这种文档只适合在开发环境下生成，如果是生产环境，我们需要关闭文档，application-prod.yml

//        四、项目运行监控
//        项目开发完成之后，在项目上线运行的运行过程中，需要对其进行监控，从而实时观察其运行状态，并在发生问题时做出对应调整，
//        因此，集成项目运行监控就很有必要了。我们只需要通过这些接口就能快速获取到当前应用程序的运行信息了。

//        步1.SpringBoot框架提供了spring-boot-starter-actuator模块来实现监控效果。
//        添加好之后，Actuator会自动注册一些接口用于查询当前SpringBoot应用程序的状态，
//        官方文档如下：https://docs.spring.io/spring-boot/docs/3.1.1/actuator-api/htmlsingle/#overview
//        步2.默认情况下，所有Actuator自动注册的接口路径都是/actuator/{id}格式的（可在配置文件中修改），
//        比如查询当前服务器的健康状态，就可以访问这个接口：http://localhost:8080/actuator/health，结果会以JSON格式返回给我们。
//        步3.直接访问：http://localhost:8080/actuator根路径，可以查看当前已经开启的所有接口，默认情况下只开启以下接口。
//        步4.我们可以来修改一下配置文件application-dev.yml，让其暴露全部接口
//        步5.我们可以通过 http://localhost:8080/actuator/info 接口查看当前系统运行环境信息，这里得到的数据是一个空的
//        步6.我们还需要单独开启对应模块才可以。再次请求，就能获得运行环境相关信息了，比如这里的Java版本、JVM信息、操作系统信息等
//        步7.我们也可以让http://localhost:8080/actuator/health显示更加详细的系统状态信息，这里我们开启一下配置
//        就能查看当前系统占用相关信息了，比如下面的磁盘占用、数据库等信息。
//        http://localhost:8080/actuator/env包括完整的系统环境信息，比如我们配置的服务器8080端口。
//        步8.还有线程转储和堆内存转储文件直接生成，便于我们对Java程序的运行情况进行分析，
//        这里我们获取一下堆内存转储文件：http://localhost:8080/actuator/heapdump，文件下载之后直接使用IDEA就能打开
//        左上角 三个点 --> 分析器 --> 文件拉进来
//        步9.以及对应的线程转储信息，也可以通过http://localhost:8080/actuator/threaddump直接获取
























    }
}
