package com.li.test.spring_boot.readinglist;


import org.springframework.data.jpa.repository.JpaRepository;


public interface ReaderRepository extends JpaRepository<Reader, String> {
}
