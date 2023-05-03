package com.example.admin;


import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @BootstrapWith(SpringBootTestContextBootstrapper.class)
 * @ExtendWith(SpringExtension.class)
 */
//引入该注解才可以使用Spring的功能
@SpringBootTest
@DisplayName("Junit5功能测试类")
public class Junit5Test {



    /**
     * 测试前置条件
     */
    @DisplayName("测试前置条件")
    @Test
    void testassumptions(){
        Assumptions.assumeTrue(false,"结果不是true");
        System.out.println("111111");

    }

    @Test
    @DisplayName("简单断言")
    void testSimpleAssertion(){
        //一个断言失败，剩余的断言不会运行
        int cal = cal(5, 3);
        assertEquals(5,cal,"业务逻辑计算错误");
        Object obj1 = new Object();
        Object obj2 = new Object();
        assertSame(obj1,obj2,"对象不一致");
    }

    int cal(int i,int j){
        return i+j;
    }

    @Test
    @DisplayName("数组断言")
    void testArray(){
       assertArrayEquals(new int[]{2,1},new int[]{1,2},"两个数组不一样");
    }

    @Test
    @DisplayName("组合断言")
    void testAll(){
        //必须两个断言同时成立，第一个断言失败，其余的不运行
        assertAll("math",
                () -> assertEquals(3,1+1),
                () -> assertTrue(1>0));
    }

    @Test
    @DisplayName("异常断言")
    void testThrow(){
        //断定业务逻辑一定出现异常
        assertThrows(ArithmeticException.class,
                () -> {int i = 10/2;},
            "程序居然运行正常？");
    }

    @Test
    @DisplayName("超时断言")
    void testTimeAssertion(){
        //程序运行超过500ms，会出现异常
        assertTimeout(Duration.ofMillis(500),() -> Thread.sleep(600),
                "程序运行超时");
    }

    @Test
    @DisplayName("fail")
    void testFail(){
        fail("直接运行失败");
    }


    @Test
    @DisplayName("第一次测试")
    void testDisplayName(){
        System.out.println("1");
    }

    @Test
    @Disabled
    @DisplayName("第二个测试")
    void test2(){
        System.out.println("2");
    }

    @Test
    @BeforeEach
    void testBeforeEach(){
        System.out.println("测试要开始了。。。。");
    }

    @Test
    @AfterEach
    void testAfterEach(){
        System.out.println("测试要结束了。。。");
    }

    @Test
    @BeforeAll
    static void testBeforeAll(){
        System.out.println("所有测试开始");
    }

    @Test
    @AfterAll
    static void testAfterAll(){
        System.out.println("所有测试结束");
    }

    @Test
    @Tag("importtant")
    void testTag(){
        System.out.println("important");
    }

    @Test
    @Disabled
    @Timeout(value = 500,unit = TimeUnit.MILLISECONDS)
    void testTimeout() throws InterruptedException {
        Thread.sleep(600);
    }

    @RepeatedTest(value = 5)
    void testRepeat(){
        System.out.println("5");
    }

}
