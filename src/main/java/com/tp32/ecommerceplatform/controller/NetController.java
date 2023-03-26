package com.tp32.ecommerceplatform.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tp32.ecommerceplatform.dto.NetDto;
import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.service.NetItemService;
import com.tp32.ecommerceplatform.service.NetService;

@Controller
public class NetController {

    private NetService netService;
    private NetItemService netItemService;

    public NetController(NetService netService, NetItemService netItemService) {
        this.netService = netService;
        this.netItemService = netItemService;
    }

    @GetMapping("/net")
    public String getNet(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Net net = netService.findByUser(user);
        if (net != null) 
            model.addAttribute("netitems", net.getNetItems());
        return "net.html";
    }
    
    @PostMapping("/net/add")
    public ModelAndView createNetItem(@ModelAttribute NetDto netDto, @RequestParam(value = "productId", required = true) Long productId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        netItemService.create(user.getID(), productId, netDto.getQuantity());

        ModelAndView model = new ModelAndView("redirect:/net");
        return model;
    }

    @GetMapping("/net/delete")
    public ModelAndView deleteNetItem(@RequestParam(value = "productId", required = true) Long productId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        netItemService.removeNetItem(user, productId);

        ModelAndView model = new ModelAndView("redirect:/net");
        return model;
    }

    @GetMapping("/net/submit")
    public ModelAndView submitNet() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Net net = netService.findByUser(user);

        if (net == null) {
            return new ModelAndView("redirect:/categories");
        }
        netService.close(net.getID()); 

        ModelAndView model = new ModelAndView("redirect:/orders");
        return model;
    }

}
