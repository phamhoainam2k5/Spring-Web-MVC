package com.vang.springmvc.CustomerManagement.Controller;

import com.vang.springmvc.CustomerManagement.Model.Customer;
import com.vang.springmvc.CustomerManagement.Reponsitory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerCONTROLLER {
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping()
    public ModelAndView getAllCustomers() throws SQLException, ClassNotFoundException {
        ModelAndView modelAndView = new ModelAndView("customer-management");
        List<Customer> customers = customerRepository.showAccUser();
//        for (int i = 0; i < customers.size(); i++) {
//            System.out.printf(customers.get(i).getName());
//        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public String informationCustomer(@PathVariable int id, Model model) throws SQLException, ClassNotFoundException {
        Customer customer = customerRepository.getUserByID(id);
        model.addAttribute("customer", customer);
        return "customer-information";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, Model model) throws SQLException, ClassNotFoundException {
        customerRepository.updateCustomer(customer);
        model.addAttribute("message", "Update success");
        return "success";
    }
}
