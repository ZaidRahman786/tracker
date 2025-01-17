package com.bottrack.repository;

import com.bottrack.model.Login;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@EnableAutoConfiguration
public interface LoginRepository extends JpaRepository<Login, Long> {

//    @Query(value = "update Login set userName = :userName where mobile = :mobile")
//    String updateLoginByUserIdRepository(@Param("userName") String userName, @Param("mobile") String mobile);

    @Query(value = "select l from Login l where l.mobile = :mobile")
    Login getLoginByMobile(@Param("mobile") String mobile);

    @Query(value = "select l from Login l where l.userId = :userId")
    Login getLoginByUserId(@Param("userId") Long mobile);

    @Query(value = "select l.* from login l order by l.UserId desc limit 1", nativeQuery = true)
    Login getLoginLastRecord();
}
