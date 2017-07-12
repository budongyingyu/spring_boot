package com.li.test.spring_boot.readinglist;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

//在Spring里可以很方便地编写你自己的条件,你所要做的就是实现 Condition 接口,覆盖它的 matches() 方法
public class JdbcTemplateCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context,
                           AnnotatedTypeMetadata metadata) {
        try {
            context.getClassLoader().loadClass("org.springframework.jdbc.core.JdbcTemplate");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
/*
当你用Java来声明Bean的时候,可以使用这个自定义条件类:
@Conditional(JdbcTemplateCondition.class)
public MyService myService() {
    ...
}
在这个例子里,只有当 JdbcTemplateCondition 类的条件成立时才会创建 MyService 这个
Bean。也就是说 MyService Bean创建的条件是Classpath里有 JdbcTemplate 。否则,这个Bean使用自动配置
的声明就会被忽略掉
*/