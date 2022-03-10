package cg.controller;

import cg.model.Customer;
import cg.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller

public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/create-customer")
    public ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customers", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customers") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customers", new Customer());
        modelAndView.addObject("message", "Create Success!!");
        return modelAndView;
    }

    @GetMapping("/customers")
    public ModelAndView listCustomers(){
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("customers", customerService.findAll());
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView editForm(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("customers", customer.get());
            return modelAndView;
        }else {
            return new ModelAndView("/error.404");
        }
    }
    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customers") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Update success");
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView deleteCustomer(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("customers", customer.get());
            return modelAndView;
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/delete-customer")
    public String deletes(@ModelAttribute("customers") Customer customer){
        customerService.deleteById(customer.getId());
        return "redirect:customers";
    }
}
