package com.li.test.spring_boot.readinglist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


//通过扩展 JpaRepository , ReadingListRepository 直接继承了18个执行常用持久化操作的方法
//JpaRepository泛型的两个参数:仓库操作的领域对象类型,及其ID属性的类型
public interface ReadingListRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
}
