package com.placement.placementPortal.Controller;

import com.placement.placementPortal.Entity.Experience;
import com.placement.placementPortal.Repository.ExperienceRepository;
import com.placement.placementPortal.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ExperienceRepository experienceRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/preparation")
    public String showPreparation() {
        return "preparation";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @GetMapping("/post")
    public String showPostForm(Model model) {
        model.addAttribute("experience", new Experience());
        return "post";
    }


    @PostMapping("/post")
    public String submitExperience(@ModelAttribute Experience exp,
                                   @RequestParam("pdfFile") MultipartFile file) {
        try {
            String filePath = fileStorageService.savePdf(file);
            exp.setResumePdfPath(filePath);
            experienceRepo.save(exp);
            return "redirect:/experiences?success=true";
        } catch (Exception e) {
            return "redirect:/post?error=true";
        }
    }

    @GetMapping("/experiences")
    public String viewExperiences(@RequestParam(required = false) String company,
                                  @RequestParam(required = false) String type,
                                  Model model) {
        List<Experience> expList;

        if (company != null && !company.isEmpty() && type != null && !type.isEmpty()) {
            expList = experienceRepo.findByCompanyNameContainingIgnoreCaseAndPlacementTypeIgnoreCase(company, type);
        } else if (company != null && !company.isEmpty()) {
            expList = experienceRepo.findByCompanyNameContainingIgnoreCase(company);
        } else if (type != null && !type.isEmpty()) {
            expList = experienceRepo.findByPlacementTypeIgnoreCase(type);
        } else {
            expList = experienceRepo.findAll();
        }

        model.addAttribute("experiences", expList);
        return "experiences";
    }

    // Endpoint to serve the PDF when "Read Article" is clicked
    @GetMapping("/resume/{id}")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {
        Experience exp = experienceRepo.findById(id).orElseThrow();
        File file = new File(exp.getResumePdfPath());
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}