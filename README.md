#WebStaticAnalyzer Server
这是一个C++云端静态分析工具的服务器源代码。同时也是NJU 2017CS的软工综合实验的项目。该文档主要对代码文件结构和相应功能进行一个简单说明。  

**以下是代码文件结构说明**
+ src/main/java/com/example/
	+ controller
		+ Config.java 对拦截器配置
		+ Controller.java 对所有请求进行处理
		+ CORSFilter.java 跨域过滤器
		+ LoginHandlerInterceptor.java 拦截器，对未登录的请求进行拦截
		+ Manager.java 调用静态分析程序
		+ ThreadPoolExecutorConfig.java 配置线程池
	+ entity 数据库实体类
		+ LoginInfo.java
		+ Record.java
		+ User.java
	+ inter 数据库接口类
		+ LoginInfoRepository.java
		+ RecordRepository.java
		+ UserRepository.java
	+ json 处理json字符串的临时类
		+ Defect.java
		+ DefectIntheSameFile.java
		+ Report.java
	+ main
		+ AnalyzerApplication.java main函数入口
	+ message 与前端进行通信的返回格式类
		+ Error.java
		+ FileText.java
		+ History.java
		+ HitoryResponse.java
		+ Line.java
		+ Message.java
		+ Person.java
		+ RegisterLoginInfo.java
		+ Response.java
		+ Result.java
	+ misc 解析请求的临时类
		+ AnalyzeID.java
		+ CheckFile.java
		+ CodeLine.java
		+ Login.java
+ pom.xml 项目配置文件
+ src/main/resources/application.properties 设置文件

