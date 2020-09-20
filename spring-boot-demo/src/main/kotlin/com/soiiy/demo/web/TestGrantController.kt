package com.soiiy.demo.web

import com.soiiy.platformsecurity.annotation.SecurityAny
import com.soiiy.platformsecurity.annotation.SecurityOnly
import com.soiiy.platformsecurity.annotation.SecurityUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sc")
class TestGrantController {

    @GetMapping
    fun index(user: DefaultUser): Any = user

    @GetMapping("/any")
    @SecurityAny(roles = ["1", "2", "user"], permissions = ["1", "2", "user_edit"])
    fun any(@SecurityUser user: DefaultUser): Any = user

    @GetMapping("/any-r1")
    @SecurityAny(roles = ["1", "2", "3"])
    fun anyRole1(user: DefaultUser): Any = user

    @GetMapping("/any-r2")
    @SecurityAny(roles = ["user", "admin"])
    fun anyRole2(user: DefaultUser): Any = user

    @GetMapping("/any-r3")
    @SecurityAny
    fun anyRole3(user: DefaultUser): Any = user

    @GetMapping("/any-g1")
    @SecurityAny(permissions = ["1", "2", "", "user_edit"])
    fun anyGrant1(user: DefaultUser): Any = user

    @GetMapping("/any-g2")
    @SecurityAny(roles = ["user_edit", "user_create"])
    fun anyGrant2(user: DefaultUser): Any = user

    @GetMapping("/any-g3")
    @SecurityAny
    fun anyGrant3(user: DefaultUser): Any = user

    // ==========================

    @GetMapping("/only")
    @SecurityOnly(roles = ["admin", "user"], permissions = ["user_create", "user_edit"])
    fun only(user: DefaultUser): Any = user

    @GetMapping("/only-r1")
    @SecurityOnly(roles = ["1", "2", "3"])
    fun onlyRole1(user: DefaultUser): Any = user

    @GetMapping("/only-r2")
    @SecurityOnly(roles = ["user", "admin"])
    fun onlyRole2(user: DefaultUser): Any = user

    @GetMapping("/only-r3")
    @SecurityOnly
    fun onlyRole3(user: DefaultUser): Any = user

    @GetMapping("/only-g1")
    @SecurityOnly(permissions = ["1", "2", "", ""])
    fun onlyGrant1(user: DefaultUser): Any = user

    @GetMapping("/only-g2")
    @SecurityOnly(roles = ["user_edit", "user_create"])
    fun onlyGrant2(user: DefaultUser): Any = user

    @GetMapping("/only-g3")
    @SecurityOnly
    fun onlyGrant3(user: DefaultUser): Any = user


}