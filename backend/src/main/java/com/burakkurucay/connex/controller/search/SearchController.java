package com.burakkurucay.connex.controller.search;

import com.burakkurucay.connex.dto.profile.company.CompanyProfileResponse;
import com.burakkurucay.connex.service.search.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/companies")
    public List<CompanyProfileResponse> searchCompanies(@RequestParam String query) {
        return searchService.searchCompanies(query).stream()
                .map(CompanyProfileResponse::from)
                .toList();
    }

    @GetMapping("/companies/random")
    public List<CompanyProfileResponse> getRandomCompanies() {
        return searchService.getRandomCompanies().stream()
                .map(CompanyProfileResponse::from)
                .toList();
    }
}
