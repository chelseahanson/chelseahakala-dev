package io.hakala.chelseahakaladev.Controller;

import io.hakala.chelseahakaladev.Entity.Project;
import io.hakala.chelseahakaladev.Entity.FormCommand;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class ProjectController{
    
	//Spring Boot will automagically wire this object using application.properties:
	@Autowired
	private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("project", new FormCommand());
        return "index";
    }

    @PostMapping("/result")
    public String result(@ModelAttribute FormCommand command, Model model) {
        String projectInput = command.getProject();

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS projects(name VARCHAR(100))");
        jdbcTemplate.execute("INSERT INTO projects VALUES ('" + projectInput + "')");

        List<Project> projects = jdbcTemplate.query("SELECT * FROM projects", (resultSet, rowNum) -> new Project(resultSet.getString("name")));
        StringBuilder message = new StringBuilder();
        projects.forEach(message::append);

        model.addAttribute("projects", projects);

        return "result";
    }
}