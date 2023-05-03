package com.example.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.bean.User;
import com.example.admin.exception.UserTooManyException;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TableController {

    @Autowired
    private UserService userService;

    /**
     * @param a 不带请求参数或者参数类型不对  400；Bad Request  一般都是浏览器的参数没有传递正确
     * @return
     */
    @GetMapping("/basic_table")
    public String basicTable(@RequestParam int a){
        int i=10/0;
        return "table/basic_table";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id,
                             @RequestParam(value = "pn",defaultValue = "1") Integer pn,
                             RedirectAttributes ra){
        userService.removeById(id);
        ra.addAttribute("pn",pn);
        return "redirect:/dynamic_table";
    }

    @GetMapping("/dynamic_table")
    public String dynamicTable(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                                Model model){
        //List<User> users = Arrays.asList(new User("zhangsan", "1234"),
        //        new User("lisi", "12345"),
        //        new User("wangwu", "123"),
        //        new User("赵六", "12355"));
        //model.addAttribute("users",users);
        //if (users.size()>3){
        //    throw new UserTooManyException();
        //}
        //构造分页参数
        Page<User> page = new Page<>(pn,2);
        //调用page进行分页
        Page<User> userPage = userService.page(page, null);
        //List<User> users = userService.list();

        //userPage.getRecords();
        //userPage.getCurrent();
        //userPage.getPages();
        //userPage.getTotal();

        model.addAttribute("users",userPage);
        return "table/dynamic_table";
    }

    @GetMapping("/editable_table")
    public String editableTable(){
        return "table/editable_table";
    }

    @GetMapping("/responsive_table")
    public String responsiveTable(){
        return "table/responsive_table";
    }
}
