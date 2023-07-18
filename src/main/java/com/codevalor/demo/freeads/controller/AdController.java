package com.codevalor.demo.freeads.controller;


import com.codevalor.demo.freeads.dto.request.AdRequest;
import com.codevalor.demo.freeads.model.Ad;
import com.codevalor.demo.freeads.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @GetMapping
    public List<Ad> findAllAds() {
        return adService.findAllAds();
    }

    @PostMapping
    public Ad createAd(@RequestBody AdRequest adRequest) {
        return adService.createAd(adRequest);
    }

    @PutMapping("/{id}")
    public Ad verifyAd(@PathVariable String id) {
        return adService.verifyAd(id);
    }

}
