package com.example.admin.controller;

import com.example.admin.bean.City;
import com.example.admin.bean.Product;
import com.example.admin.bean.User;
import com.example.admin.service.CityService;
import com.example.admin.service.ProductService;
import com.example.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductService productService;

    @Autowired
    CityService cityService;

    @Autowired
    UserService userService;

    //@Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @GetMapping("/user")
    public List<User> getUsers(){
        return userService.list();
    }

    @ResponseBody
    @GetMapping("/product")
    public Product getProductById(@RequestParam("id") Long id){
        return productService.getProductById(id);
    }

    @ResponseBody
    @GetMapping("/city")
    public City getCityById(@RequestParam("id") int id){
        return cityService.getCityById(id);
    }

    @ResponseBody
    @PostMapping("/city")
    public City insert(City city){
        cityService.insert(city);
        return city;
    }


    @ResponseBody
    @GetMapping("/sql")
    public String queryFrmDb(){
        Long aLong = jdbcTemplate.queryForObject("select count(*) from t_product", Long.class);
        return aLong.toString();
    }

    @GetMapping(value = {"/","/login"})
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String main(User user, HttpSession session, Model model){
        if (StringUtils.hasLength(user.getUserName()) && user.getPassword().equals("123456")){
            //将登陆的对象保存起来
            session.setAttribute("loginUser",user);
            //登陆成功后重定向至main.html页面，重定向防止表单重复提交
            return "redirect:main.html";
        }else {
            model.addAttribute("msg","账号密码错误");
            return "login";
        }
    }

    @GetMapping("/main.html")
    public String mainPage(HttpSession session,Model model){
        log.info("当前方法是：{}","mainPage");
       /* Object user = session.getAttribute("loginUser");
        if (user != null){
            return "main";
        }else {
            model.addAttribute("msg","请重新登陆");
            return "login";
        }*/

        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String main = value.get("/main.html");
        String sql = value.get("/sql");
        model.addAttribute("mainCount",main);
        model.addAttribute("sqlCount",sql);
        return "main";
    }
}
