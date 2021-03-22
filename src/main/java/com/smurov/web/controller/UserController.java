package com.smurov.web.controller;

import com.smurov.web.model.User;
import com.smurov.web.repository.UserRepository;
import com.smurov.web.service.RoleService;
import com.smurov.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public UserController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}


	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/admin")
	public String getUsers(Model model){
		model.addAttribute("users", userService.listUsers());
		model.addAttribute("roles", roleService.getRoles());
		return "admin";
	}

	@GetMapping("/user")
	public String getUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user",  userService.getUserByFirstName(authentication.getName()));
		return "user";
	}

	@PostMapping(value = "/admin/add")
	public String addUser(@ModelAttribute User user, @RequestParam(value = "role") Long[] rolesId){
		userService.add(user, rolesId);
		return "redirect:/admin";
	}

	@PostMapping(value = "/admin/delete")
	public String deleteUser(@ModelAttribute("id") Long id){
		userService.remove(id);
		return "redirect:/admin";
	}

	@PostMapping(value = "/admin/update")
	public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "role", required = false) Long[] rolesId){
		userService.update(user, rolesId);
		return "redirect:/admin";
	}

	@GetMapping(value = "/admin/update")
	public String updateUser(@ModelAttribute("id") Long id, Model model){
		if (userService.checkUserById(id)) {
			return "redirect:/admin";
		}
		User user = userService.getUserById(id);
		model.addAttribute("roles", roleService.getRoles());
		model.addAttribute("user",user);
		return "update";
	}













//
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	@ResponseBody
//	public User save(@RequestBody User user) {
//		User userResponse = (User) userService.saveUser(user);
//		return userResponse;
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public User getUser(@PathVariable Long id) {
//		User userResponse = (User) userService.getUser(id);
//		return userResponse;
//	}
//
//	@RequestMapping(value = "/admin/delete", method = RequestMethod.DELETE)
//	@ResponseBody
//	public String deleteUser(@PathVariable Long id) {
//		userService.deleteUser(id);
//		return "redirect:/admin";
//	}
}

