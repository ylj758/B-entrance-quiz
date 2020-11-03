### 完成度：
__Details:__
- \- StudentController.java:56 缺少分组的接口
- \- StudentController.java:57 缺少查看分组的接口

### 知识点：
* 需要了解下Lombok注解的用法以及意思
* 需要了解下三层架构
* 需要了解下restful实践
* 需要了解下序列化相关的知识

__Details:__
- \- Student.java:10 注解使用有误
- \- StudentController.java:22 数据存储的相关职责，不是controller的职责
- \- StudentController.java:44 直接返回studentList即可，Spring框架会自动进行序列化
- \- StudentController.java:49 不符合restful实践，一般路径中是资源名称，而非动词

### 工程实践：
__Details:__
- \- StudentController.java:54 这种自增id的方式，线程不安全，试试AtomicInteger

### 综合：
* 代码量过少，无法给出更多的feedback



